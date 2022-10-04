import re
from textblob import TextBlob
from tqdm import tqdm
import numpy as np
from gensim.test.utils import datapath

STOPWORDS = """
a about above across after afterwards again against all almost alone along already also although always am among amongst an and another any anyhow anyone anything anyway anywhere are around as at back be
became because become becomes becoming been before beforehand being beside besides between beyond both bottom but by call can
cannot cant co con could couldnt cry de
did didn do does doesn doing don done down due during
each eight eg either eleven else elsewhere enough etc even ever every everyone everything everywhere except few fifteen
fifty fill find for former formerly forty found four from front full further get give go
had has hasnt have hence here hereafter hereby herein hereupon how however hundred i ie
if in inc indeed into is it its itself  last latter latterly least less ltd
just
kg km keep
made make many may me meanwhile might mill mine more moreover most mostly move much must my myself name namely
neither never nevertheless next nine no nobody none noone nor not nothing now nowhere of off
often on once one only onto or other others otherwise our ours ourselves out over own part per
perhaps please put rather re
quite
rather really regarding
same say see seem seemed seeming seems several should show side since six sixty so some somehow someone something sometime sometimes somewhere still such take ten
than that the then thence there thereafter thereby therefore therein thereupon these third this those though three through throughout thru thus to too top toward towards twelve twenty two un under
until up unless upon us used using
various very via
was we well were what whatever when whence whenever where whereafter whereas whereby wherein whereupon wherever whether which while whither who whoever whole whom whose why will with within without would yet you
your yours yourself yourselves

lrb rrb lcb rcb lsb rsb
-lrb- -rrb- -lcb- -rcb- -lsb- -rsb-
"""
STOPWORDS = frozenset(w for w in STOPWORDS.split() if w)

PAT_ALPHABETIC = re.compile('(((?![\d])\w)+)', re.UNICODE)

def token_filter(token, tag, stopwords=STOPWORDS, min_len=2, max_len=15):
    return token is not None and tag not in ("CD",) and token not in stopwords and min_len <= len(token) <= max_len

def token_preprocess(token, tag):
    token = token.lemmatize(wordnet_tag(tag)).lower()
    reg = PAT_ALPHABETIC.findall(token)
    token = reg[0][0] if reg != [] else None 
    return token

def wordnet_tag(tag):
    tag_abbreviation = tag[0].lower()
    return tag_abbreviation if tag_abbreviation in ['n', 'v'] else 'n'


def document_to_tokens(document, use_textblob=True):
    if use_textblob:
        blob = TextBlob(document)
        tokens_tags = [(token_preprocess(token, tag), tag) for token, tag in blob.tags]
        tokens_filtered = [token for token, tag in tokens_tags if token_filter(token, tag)]
    else:
        tokens = simple_preprocess(document)
        tokens_filtered = remove_stopword_tokens(tokens, stopwords=STOPWORDS)
    
    return tokens_filtered

class TrainingCorpus:
    def __init__(self, corpus, tokenize_before_training=False, use_textblob=True, base_fn="/Users/andrew.wang/Documents/academy/project/kainosRecruitmentApi-TeamA/ai/data/corpus"):
        fn = f'{base_fn}/{corpus}.txt'
        self.corpus_path = datapath(fn)
        self.tokenize_before_training = tokenize_before_training
        self.use_textblob = use_textblob
        
        if self.tokenize_before_training:
            try:
                self.corpus_tokens = np.load(f'{base_fn}/{corpus}_tokens.npy', allow_pickle=True)
            except FileNotFoundError:
                self.corpus_tokens = []
                with open(fn, "r") as f:
                    documents = f.readlines()
                    for document in tqdm(documents):
                        self.corpus_tokens.append(document_to_tokens(document, use_textblob=self.use_textblob))
                np.save(f'{base_fn}/{corpus}_tokens.npy', np.array(self.corpus_tokens, dtype="object"))
                    
    def __iter__(self):
        if self.tokenize_before_training:
            for tokens in self.corpus_tokens:
                yield tokens
        else:
            for document in open(self.corpus_path):
                tokens = document_to_tokens(document, use_textblob=self.use_textblob)
                yield tokens
