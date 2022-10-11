"""
This module serves to act as alias for gender-bias-api/app/api modules.
This is so that notebooks here can seamlessly access api simply using from api import ...
Import functions and classes for gender bias scoring from gender-bias-api/app/api/__init__.py.
"""

import importlib  
api = importlib.import_module("gender-bias-api.app.api")

GenderBiasScorer = api.GenderBiasScorer
EnsembleGenderBiasScorer = api.EnsembleGenderBiasScorer
percentage_bias = api.percentage_bias
biased_words = api.biased_words
score_document_sentiment_subjectivity = api.score_document_sentiment_subjectivity
document_to_tokens = api.document_to_tokens
TrainingCorpus = api.TrainingCorpus
