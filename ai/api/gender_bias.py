"""
This file contains functionality to calculating gender bias words and score for documents
"""

import numpy as np
import pandas as pd
from .preprocess import document_to_tokens, document_length
from gensim.models import KeyedVectors

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
    return np.sum(np.abs(scores)) / document_length(document)

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

class GenderBiasScorer:
    """Class for calculating gender bias score for words and documents using a word vocabulary trained using word2vec
    """
    def __init__(self, wv: KeyedVectors, group_m=None, group_f=None, metric="cosine", group_reduce="max"):
        """Initialise gender bias scoring for given model

        Args:
            wv (gensim.models.KeyedVectors): a word embedding vocabulary from trained word2vec model
            group_m (list, optional): words describing male bias. Defaults to None. If None, use ["male", "man", "he", "his", "masculine"] 
            group_f (list, optional): words describing female bias. Defaults to None. If None, use ["female", "woman", "she", "her", "feminine"]
            metric (str, optional): metric for measuring word similarity, choose from ["cosine", "euclidean"]. Defaults to "cosine".
            group_reduce (str, optional): how to calculate difference between similarities to either gender bias group, choose from ["max", "mean"].
                If "max", choose best absolute difference between similarities of input word and either bias group words. If "mean", choose
                mean difference, which boils down to calculating similarities of input word and group means (mathematically bit iffy).  Defaults to "max".

        Raises:
            ValueError: metric not in ["cosine", "euclidean"]
            ValueError: group_reduce not in ["max", "mean"]
        """
        self.wv = wv
        self.group_m = ["male", "man", "he", "his", "masculine", "men"]       if group_m is None else group_m
        self.group_f = ["female", "woman", "she", "her", "feminine", "women"] if group_f is None else group_f
        
        assert(len(self.group_m) == len(self.group_f))

        self.group_m_vecs = []
        self.group_f_vecs = []

        for m,f in zip(self.group_m, self.group_f):
            try:
                _ = self.wv[m]
                _ = self.wv[f]
            except KeyError:
                print(f"Bias group words {m}, {f} not found in model vocabulary")
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
        
    def score_tokens(self, tokens: list) -> list:
        """Calculate gender bias for list of tokens

        Args:
            tokens (list): word tokens

        Returns:
            list: gender bias scores of tokens
        """
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
        except KeyError:
            print(f"Tokens {token} not found in model vocabulary")
            similarities_diff = np.zeros(len(self.group_m))

        return self.group_reduce(similarities_diff)

    def score_token_binary(self, token: str, thresh=0.1) -> int:
        """Calculate thresholded gender bias score giving either female bias, unbiased or male bias.

        Args:
            token (str): input token
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.

        Returns:
            int: pseudo binary score (i.e. either -1, 0 or 1 representing female bias, unbiased and male bias)
        """
        score = self.score_token(token)
        return (np.sign(score) * (np.abs(score) > thresh) + 1 - 1).astype(np.int8) #+1-1 hack to remove -0.0 examples
    
    def score_tokens_binary(self, tokens: list, thresh=0.1) -> list:
        """Calculate thresholded gender bias scores for list of tokens

        Args:
            tokens (list): input tokens
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.

        Returns:
            list: scores in (-1, 0 or 1) per token representing female bias, unbiased and male bias
        """
        return [self.score_token_binary(token, thresh=thresh) for token in tokens]
    
    def score_document(self, document: str, thresh=0.1) -> tuple[list, list]:
        """Calculate thresholded gender bias scores for raw document string.

        Args:
            document (str): Input document string (all on one line)
            thresh (float, optional): absolute score threshold. Defaults to 0.1. Tuned as hyperparameter in testing.

        Returns:
            list: tokens extracted from document (using same function as in corpus training)
            list: scores per token
        """
        tokens = document_to_tokens(document)
        scores = self.score_tokens_binary(tokens, thresh=thresh)
        return tokens, scores