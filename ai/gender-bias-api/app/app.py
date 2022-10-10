"""
Handle Lambda function invocation and get gender bias prediction of text
Deployment notes:
- CloudFormation Stack creates S3 bucket, Lambda function, API Gateway
- Lambda function is built from Docker image stored in ECR
- API Gateway timeout is 29s. We don't want to exceed this, however loading Google news model
takes > 1 minute, so cold started Lambda called via API will fail (504 Gateway timeout).
Solution: use Cloudwatch scheduler rule to keep function warm. Before starting, kickstart
function directly using test event call from Lambda. 
- We deploy to eu-west-2, and all resources have prefix academy22-gender-bias-prediction
"""
import json
from gensim.models import Word2Vec, KeyedVectors
from api import EnsembleGenderBiasScorer, percentage_bias, biased_words, score_document_sentiment_subjectivity


print("Loading models...")
# Load models from folder created in Dockerfile
model_base = "/opt/ml"
wv_pretrained = KeyedVectors.load_word2vec_format(f"{model_base}/word2vec-google-news-300.gz", binary=True)
wv_wikibios = Word2Vec.load(f"{model_base}/dataset_wikibios_merged.pt").wv
wv_bug = Word2Vec.load(f"{model_base}/dataset_bug.pt").wv

print("Models loaded. Creating scorer...")
ensemble_scorer = EnsembleGenderBiasScorer(wvs=[wv_wikibios, wv_bug, wv_pretrained],
                                            threshs=[0.4, 0.8, 0.13]
                                            )
print("Scorer created.")


def post_gender_bias(document: str) -> dict:
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

def post_sentiment(document: str) -> dict:
    result = score_document_sentiment_subjectivity(document)
    sentiment, subjectivity = result["sentiment"], result["subjectivity"]

    return {
        "sentiment": sentiment,
        "subjectivity": subjectivity
    }

def lambda_handler(event, context):
    document = str(event['body'])
    print("Document:", document, type(document))
    result = post_gender_bias(document)
    print("Result:", result)

    return {
        'statusCode': 200,
        'body': json.dumps(result)
    }
