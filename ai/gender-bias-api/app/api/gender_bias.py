"""
This file contains functionality to calculating gender bias words and score for documents
"""

import numpy as np
import pandas as pd
from sklearn.metrics import accuracy_score
from tqdm import tqdm
from .preprocess import document_to_tokens, document_length
from gensim.models import KeyedVectors
from gensim.test.utils import datapath


def euclidean_similarities(v1: np.ndarray, v_all: np.ndarray) -> np.ndarray:
    """Calculate negative euclidean distance between v1 and each vector in v_all
    Drop in replacement for `gensim.models.KeyedVectors.cosine_similarities`

    Args:
        v1 (np.ndarray): shape (1,m) word embedding vector of dimension m
        v_all (np.ndarray): shape (n,m) array of n word vectors of dimension m

    Returns:
        np.ndarray: shape (1,n) array of negative euclidean distances
    """
    return -np.array([np.linalg.norm(v1-v) for v in v_all])

def vmax(a: np.ndarray) -> float:
    """Return symmetrical max of 1D array (i.e. "V" max)

    Args:
        a (np.ndarray): 1D array of values

    Returns:
        float: "V" max of array
    """
    return a[np.abs(a).argmax()]

def percentage_bias(document: str, scores: list) -> float:
    """Calculate percentage of biased words out of document

    Args:
        document (str): original document for calculating total document length
        scores (list): scores calculated for tokens

    Returns:
        float: percentage of bias words
    """
    return np.sum(np.abs(scores)) / document_length(document) * 100

def biased_words(tokens: list, scores: list) -> dict:
    """Return detected biased words

    Args:
        tokens (list): tokens from original text
        scores (list): bias scores per token

    Returns:
        dict: biased words towards male, female and unbiased
    """
    s = np.array(scores)
    t = np.array(tokens, dtype="object")
    return {"biased_m": t[s==1].tolist(), 
            "biased_f": t[s==-1].tolist(),
            "unbiased": t[s==0].tolist()}

def score_document_sentiment_subjectivity(document: str, return_scores=False) -> dict:
    """Sentiment and subjectivity analysis on document using TextBlob pretrained.
    Explanation [here](https://towardsdatascience.com/the-most-favorable-pre-trained-sentiment-classifiers-in-python-9107c06442c6)
    Return by default just labels describing sentiment polarity and subjectivity.

    Args:
        document (str): Document to be analysed (one line)
        return_scores (bool, optional): Whether to return numerical scores as well. Defaults to False.

    Returns:
        dict: "sentiment": sentiment descriptor if return_scores==False else descriptor and numerical score.
            Ditto with "subjectivity".
    """
    _, blob = document_to_tokens(document, return_textblob=True)
    sentiment_output = blob.sentiment

    if sentiment_output.polarity >= 0.666:
        sentiment = "Very positive"
    elif sentiment_output.polarity >= 0.333:
        sentiment = "Positive"
    elif sentiment_output.polarity >= -0.333:
        sentiment = "Neutral"
    elif sentiment_output.polarity >= -0.666:
        sentiment = "Negative"
    elif sentiment_output.polarity >= -1:
        sentiment = "Very negative"
    else:
        sentiment = "Error occured"
    
    if sentiment_output.subjectivity >= 0.75:
        subjectivity = "Subjective"
    elif sentiment_output.subjectivity >= 0.25:
        subjectivity = "Neutral"
    elif sentiment_output.subjectivity >= 0:
        subjectivity = "Objective"
    else:
        subjectivity = "Error occured"

    if return_scores:
        sentiment = [sentiment, sentiment_output.polarity]
        subjectivity = [subjectivity, sentiment_output.subjectivity] 
    
    return {"sentiment": sentiment, "subjectivity": subjectivity}

class GenderBiasScorer:
    """Class for calculating gender bias score for words and documents using a word vocabulary trained using word2vec
    """
    def __init__(self, wv: KeyedVectors, group_m=None, group_f=None, metric="cosine", group_reduce="max", verbose=False):
        """Initialise gender bias scoring for given model

        Args:
            wv (gensim.models.KeyedVectors): a word embedding vocabulary from trained word2vec model
            group_m (list, optional): words describing male bias. Defaults to None. If None, use ["male", "man", "he", "his", "masculine"] 
            group_f (list, optional): words describing female bias. Defaults to None. If None, use ["female", "woman", "she", "her", "feminine"]
            metric (str, optional): metric for measuring word similarity, choose from ["cosine", "euclidean"]. Defaults to "cosine".
            group_reduce (str, optional): how to calculate difference between similarities to either gender bias group, choose from ["max", "mean"].
                If "max", choose best absolute difference between similarities of input word and either bias group words. If "mean", choose
                mean difference, which boils down to calculating similarities of input word and group means (mathematically bit iffy).  Defaults to "max".
            verbose (bool, optional): print when words missing from vocabulary. Defaults to False.

        Raises:
            ValueError: metric not in ["cosine", "euclidean"]
            ValueError: group_reduce not in ["max", "mean"]
        """
        self.wv = wv
        self.group_m = ["male", "man", "he", "his", "masculine", "men"]       if group_m is None else group_m
        self.group_f = ["female", "woman", "she", "her", "feminine", "women"] if group_f is None else group_f
        self.verbose = verbose
        
        assert(len(self.group_m) == len(self.group_f))

        self.group_m_vecs = []
        self.group_f_vecs = []

        for m,f in zip(self.group_m, self.group_f):
            try:
                _ = self.wv[m]
                _ = self.wv[f]
            except KeyError:
                if self.verbose: print(f"Bias group words {m}, {f} not found in model vocabulary")
                continue

            self.group_m_vecs.append(self.wv[m])
            self.group_f_vecs.append(self.wv[f])
        
        if metric=="cosine":
            self.metric = self.wv.cosine_similarities
        elif metric=="euclidean":
            self.metric = euclidean_similarities
        else:
            raise ValueError
        
        if group_reduce == "mean":
            self.group_reduce = np.mean
        elif group_reduce == "max":
            self.group_reduce = vmax
        else:
            raise ValueError
        
    def score_tokens(self, tokens: list, progress=False) -> list:
        """Calculate gender bias for list of tokens

        Args:
            tokens (list): word tokens
            progress (bool): display progress

        Returns:
            list: gender bias scores of tokens
        """
        tokens = tqdm(tokens) if progress else tokens
        return [self.score_token(token) for token in tokens]

    def score_token(self, token: str) -> float:
        """Calculate gender bias score for token by comparing token's vector representation with
            each of the group words from either bias group. Then, calculate difference of scores
            between male and female. The final score is calculated as either the best absolute 
            difference or mean difference according to self.group_reduce.

        Args:
            token (str): input token to be scored

        Returns:
            float: calculated gender bias score between -1 and 1.
            0=neutral, negative=female bias, positive=male bias
        """
        try:
            similarities_m = self.metric(self.wv[token], self.group_m_vecs)
            similarities_f = self.metric(self.wv[token], self.group_f_vecs)
            similarities_diff = similarities_m - similarities_f
            
            #check if any similarities are super high, in which case just take that as definitive
            definitive_bias = int(len(np.where(similarities_m > 0.995)[0]) > 0) - int(len(np.where(similarities_f > 0.995)[0]) > 0)
        except KeyError:
            if self.verbose: print(f"Tokens {token} not found in model vocabulary")
            similarities_diff = np.zeros(len(self.group_m))
            definitive_bias = 0

        return self.group_reduce(similarities_diff) if definitive_bias == 0 else definitive_bias

    def score_token_binary(self, token: str, thresh=0.1) -> int:
        """Calculate thresholded gender bias score giving either female bias, unbiased or male bias.
            This is in fact "ternary": male/female/unbiased.

        Args:
            token (str): input token
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.

        Returns:
            int: pseudo binary score (i.e. either -1, 0 or 1 representing female bias, unbiased and male bias)
        """
        score = self.score_token(token)
        return (np.sign(score) * (np.abs(score) > thresh) + 1 - 1).astype(np.int8) #+1-1 hack to remove -0.0 examples
    
    def score_tokens_binary(self, tokens: list, thresh=0.1, progress=False) -> list:
        """Calculate thresholded gender bias scores for list of tokens

        Args:
            tokens (list): input tokens
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.
            progress (bool): display progress

        Returns:
            list: scores in (-1, 0 or 1) per token representing female bias, unbiased and male bias
        """
        tokens = tqdm(tokens) if progress else tokens
        return [self.score_token_binary(token, thresh=thresh) for token in tokens]
    
    def score_document(self, document: str, thresh=0.1, progress=False, commonsense_filter=False) -> tuple[list, list]:
        """Calculate thresholded gender bias scores for raw document string.

        Args:
            document (str): Input document string (all on one line)
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.
            progress (bool): display progress
            commonsense_filter (bool, optional): whether to filter additional stopwords in inference mode
                such as words that common sense deem it shouldn't be included. Defaults to False.

        Returns:
            list: tokens extracted from document (using same function as in corpus training)
            list: scores per token
        """
        tokens = document_to_tokens(document, commonsense_filter=commonsense_filter)
        scores = self.score_tokens_binary(tokens, thresh=thresh, progress=progress)
        return tokens, scores
    
    def lexicon_accuracy(self, lexicon: str, thresh=0.1, verbose=False, base_fn="data/lexicons") -> float:
        """Evaluate gender bias scorer on labelled lexicon. Return accuracy of lexicon's gender bias labels
            and predicted gender bias scores.

        Args:
            lexicon (str): name of lexicon, choose from ["bias", "test"]. Test lexicon is reality-check: words like
                           "he", "she", "dog" should all be appropriately classified. Bias lexicon is a fun comparison
                           with psychoanalytical lexicon from the literature, seeing if words that are clasically 
                           psychologically and sociologically "biased" in job specs are also represented in a biased way 
                           in unstructured text from the wild. The lexicon file should be csv with columns
                           ["word", "label"].
            thresh (float, optional): scoring threshold for tuning. See docs for score_token_binary(). Defaults to 0.1.
            verbose (bool, optional): print words, labels and predictions. Defaults to False.
            base_fn (str, optional): data folder for lexicon. Defaults to "data/lexicons".

        Returns:
            float: accuracy score of predictions compared to ground labels
        """
        lexicon = pd.read_csv(f"{base_fn}/{lexicon}_lexicon.csv")
        lexicon["score"] = [self.score_token_binary(lexicon.iloc[i,0], thresh=thresh) for i in range(len(lexicon))]

        #accuracy = np.mean(lexicon["label"]==lexicon["score"])
        accuracy = accuracy_score(lexicon["label"], lexicon["score"])

        if verbose:
            print(lexicon[["word","label","score"]].to_string())

        return accuracy
    
    def lexicon_n_absent_words(self, lexicon: str, base_fn="data/lexicons") -> int:
        lexicon = pd.read_csv(f"{base_fn}/{lexicon}_lexicon.csv")
        ret = 0
        for i in range(len(lexicon)):
            try:
                _ = self.wv[lexicon.iloc[i,0]]
            except KeyError:
                ret += 1
            except AttributeError: #no wv object because method called in EnsembleGenderBiasScorer
                return 0
        return ret
    
    def eval_gensim_human_dataset(self) -> float:
        return self.wv.evaluate_word_pairs(datapath('wordsim353.tsv'))[0].statistic


class EnsembleGenderBiasScorer(GenderBiasScorer):
    """Class for gender bias scorer using ensemble of single gender bias scorers. Inherits from GenderBiasScorer.
        Even though the test accuracy of the ensemble method is lower (see 03_test_model.ipynb), this is
        expected to be more robust because biased words have to be "voted" in by all models.
    """
    def __init__(self, wvs: list, threshs: list, **kwargs):
        """Initialise ensemble gender bias model using several word embedding models.

        Args:
            wvs (list): list of word vocabulary models of type gensim.models.KeyedVectors either from trained
                        word2vec models or pretrained from gensim.downloader
            threshs (list): list containing threshold for each model for binary scoring of gender bias
            kwargs: kwargs to be passed to initialise all child GenderBiasScorers
        """
        self.wvs = wvs
        self.threshs = threshs
        self.scorers = [GenderBiasScorer(wv, **kwargs) for wv in wvs]
    
    def score_tokens(self, tokens: list) -> list:
        """Ensemble method uses voting of binary scores, so raw scores are not possible.
        """
        raise NotImplementedError

    def score_token(self, token: str) -> float:
        """Ensemble method uses voting of binary scores, so raw scores are not possible.
        """
        raise NotImplementedError

    def _mode(self, x: list) -> int:
        # Return most common value of list
        return max(set(x), key=x.count)
    
    def _mode_conflict(self, x: list) -> bool:
        # Return whether there are multiple modes (i.e. undecided gender bias?)
        c = [x.count(i) for i in set(x)]
        return c.count(max(c)) > 1

    def score_token_binary(self, token: str, thresh=0) -> int:
        """Calculate thresholded gender bias score giving either female bias, unbiased or male bias,
            using ensemble of models. This is in fact "ternary": male/female/unbiased.

        Args:
            token (str): input tokens to be scored
            thresh (int, optional): Ignored parameter for easy integration with normal GenderBiasScorer:
                                    thresholds for individual models in the ensemble are passed in constructor.

        Returns:
            int: gender bias score (-1=female biased, 0=unbiased, 1=male bias)
        """
        del thresh 

        scores = [scorer.score_token_binary(token, thresh=thresh) for scorer,thresh in zip(self.scorers, self.threshs)]
        
        score = 0 if self._mode_conflict(scores) else self._mode(scores)
        #print(token, scores, score)
        return score

    def eval_gensim_human_dataset(self) -> float:
        corrs = [scorer.eval_gensim_human_dataset() for scorer in tqdm(self.scorers)]
        return(sum(corrs)/len(corrs))
