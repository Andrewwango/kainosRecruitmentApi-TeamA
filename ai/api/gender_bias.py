import pandas as pd
import numpy as np

from .preprocess import document_to_tokens

def euclidean_similarities(v1, v_all):
    return -np.array([np.linalg.norm(v1-v) for v in v_all])

def vmax(a):
    return a[np.abs(a).argmax()]

def percentage_bias(scores):
    # percentage of biased tokens out of all tokens
    return np.sum(np.abs(scores)) / len(scores)

def biased_words(tokens, scores):
    s = np.array(scores)
    t = np.array(tokens, dtype="object")
    return {"biased_m": t[s==1].tolist(), "biased_f": t[s==-1].tolist(), "unbiased": t[s==0].tolist()}

class GenderBiasScorer:
    def __init__(self, wv, group_m=None, group_f=None, metric="cosine", group_reduce="max"):
        self.wv = wv
        self.group_m = ["male", "man", "he", "his", "masculine"] if group_m is None else group_m
        self.group_f = ["female", "woman", "she", "her", "feminine"] if group_f is None else group_f
        
        assert(len(self.group_m) == len(self.group_f))
        self.group_m_vecs = []
        self.group_f_vecs = []
        for m,f in zip(self.group_m, self.group_f):
            try:
                _ = self.wv[m]
                _ = self.wv[f]
            except KeyError:
                print(m, f, "not found")
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
        
    def score_tokens(self, tokens):
        return [self.score_token(token) for token in tokens]

    def score_token(self, token):
        # optionally send through adjective/verb filter
        try:
            similarities_m = self.metric(self.wv[token], self.group_m_vecs)
            similarities_f = self.metric(self.wv[token], self.group_f_vecs)
            similarities_diff = similarities_m - similarities_f #probs divide if using euclidean metric

        except KeyError:
            print("Does not exist in vocab: ", token)
            similarities_diff = np.zeros(len(self.group_m))

        return self.group_reduce(similarities_diff)

    def score_token_binary(self, token, thresh=0.1):
        score = self.score_token(token)
        return (np.sign(score) * (np.abs(score) > thresh) + 1 - 1).astype(np.int8)
    
    def score_tokens_binary(self, tokens, thresh=0.1):
        return [self.score_token_binary(token, thresh=thresh) for token in tokens]
    
    def score_document(self, document, thresh=0.1):
        tokens = document_to_tokens(document)
        scores = self.score_tokens_binary(tokens, thresh=thresh)
        return tokens, scores