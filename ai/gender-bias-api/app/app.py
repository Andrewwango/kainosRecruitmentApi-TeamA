import json
from gensim.models import Word2Vec
from gensim.models import KeyedVectors
import gensim.downloader as gensim_downloader
from api import EnsembleGenderBiasScorer, percentage_bias, biased_words, score_document_sentiment_subjectivity

ensemble_scorer = None
model_base = "/opt/ml" #"models"

print("Loading models...")
wv_pretrained = KeyedVectors.load_word2vec_format(f"{model_base}/word2vec-google-news-300.gz", binary=True)
wv_wikibios = Word2Vec.load(f"{model_base}/dataset_wikibios_merged.pt").wv
wv_bug = Word2Vec.load(f"{model_base}/dataset_bug.pt").wv

print("Models loaded. Creating scorer...")
ensemble_scorer = EnsembleGenderBiasScorer(wvs=[wv_wikibios, wv_bug, wv_pretrained],
                                            threshs=[0.4, 0.8, 0.13]
                                            )
print("Scorer created.")

def post_gender_bias(document: str):
    tokens, scores = ensemble_scorer.score_document(document)
    words = biased_words(tokens, scores)
    bias = percentage_bias(document, scores)

    return {
        "percentage_bias": bias,
        "biased_words": {
            "biased_words_male": list(set(words["biased_m"])),
            "biased_words_female": list(set(words["biased_f"]))
        }
    }

def post_sentiment(document: str):
    result = score_document_sentiment_subjectivity(document)
    sentiment, subjectivity = result["sentiment"], result["subjectivity"]

    return {
        "sentiment": sentiment,
        "subjectivity": subjectivity
    }

def lambda_handler(event, context):
    document = str(event['body'])
    print("document:", document, type(document))
    result = post_gender_bias(document)
    print("result:", result)
    return {
        'statusCode': 200,
        'body': json.dumps(result)
    }
