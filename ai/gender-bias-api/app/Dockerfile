FROM public.ecr.aws/lambda/python:3.9

COPY app.py ./ 
COPY requirements.txt ./
COPY api ./api/
COPY models/* /opt/ml/
# RUN curl -L https://github.com/RaRe-Technologies/gensim-data/releases/download/word2vec-google-news-300/word2vec-google-news-300.gz -o /opt/ml/word2vec-google-news-300.gz

RUN python3.9 -m pip install --trusted-host pypi.org --trusted-host files.pythonhosted.org -r requirements.txt -t .
RUN mkdir -p /var/lang/nltk_data
RUN python3.9 -c "import nltk; a = [nltk.download(_, download_dir='/var/lang/nltk_data') for _ in ['omw-1.4', 'brown', 'punkt', 'wordnet', 'averaged_perceptron_tagger']]"

CMD ["app.lambda_handler"]
