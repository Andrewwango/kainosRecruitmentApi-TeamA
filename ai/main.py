import json
from gensim.models import Word2Vec
import gensim.downloader as gensim_downloader
from api import EnsembleGenderBiasScorer, percentage_bias, biased_words, score_document_sentiment_subjectivity

ensemble_scorer = None

def init():
    global ensemble_scorer

    wv_pretrained = gensim_downloader.load('word2vec-google-news-300')
    wv_wikibios = Word2Vec.load(f"models/dataset_wikibios_merged.pt").wv
    wv_bug = Word2Vec.load(f"models/dataset_bug.pt").wv

    ensemble_scorer = EnsembleGenderBiasScorer(wvs=[wv_wikibios, wv_bug, wv_pretrained],
                                               threshs=[0.4, 0.8, 0.13]
                                               )


def post_gender_bias(document: str):
    tokens, scores = ensemble_scorer.score_document(document)
    words = biased_words(tokens, scores)
    bias = percentage_bias(document, scores)

    return json.dumps({
        "percentage_bias": bias,
        "biased_words": {
            "biased_words_male": list(set(words["biased_m"])),
            "biased_words_female": list(set(words["biased_f"]))
        }
    })


def post_sentiment(document: str):
    result = score_document_sentiment_subjectivity(document)
    sentiment, subjectivity = result["sentiment"], result["subjectivity"]

    return json.dumps({
        "sentiment": sentiment,
        "subjectivity": subjectivity
    })