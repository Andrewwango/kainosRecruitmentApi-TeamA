"""
This file contains functionality to preprocess raw corpus text for word2vec model training.
"""
import re
from tqdm import tqdm
import numpy as np
from textblob import TextBlob, Word
from gensim.test.utils import datapath
from gensim.parsing.preprocessing import remove_stopword_tokens
from gensim.utils import simple_preprocess

#################
### CONSTANTS ###
#################

# STOPWORDS: set of stopwords removed from document when tokenising (mostly taken from gensim.parsing.preprocessing.STOPWORDS 
# with minor modifications)
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
STOPWORDS = set(w for w in STOPWORDS.split() if w)

# PAT_ALPHABETIC: regex taken from gensim.utils.PAT_ALPHABETIC for gensim's tokenizer. We use this on top of TextBlob tokenization
# to ensure clean tokens.
PAT_ALPHABETIC = re.compile('(((?![\d])\w)+)', re.UNICODE)


def token_commonsense_filter(token: str):
    """Check whether to filter additional stopwords in inference mode
    such as words that common sense deem it shouldn't be included.

    Args:
        token (str): input token

    Returns:
        bool: whether token passes common sense check
    """
    commonsense_stopwords = "biasinput abcdefg".split()
    return token not in commonsense_stopwords


def token_filter(token: str, tag: str, stopwords=STOPWORDS, min_len=2, max_len=15, commonsense_filter=False) -> bool:
    """Check if token is: not None, not a number, not a stopword, and reasonable length.

    Args:
        token (str): token word from tokenization
        tag (str): corresponding textblob POS tag of token (see https://www.geeksforgeeks.org/python-part-of-speech-tagging-using-textblob/)
        stopwords (set, optional): set of stopwords to remove. Defaults to constant STOPWORDS.
        min_len (int, optional): min acceptable length of tokens. Defaults to 2.
        max_len (int, optional): max acceptable length of tokens. Defaults to 15.
        commonsense_filter (bool, optional): whether to filter additional stopwords in inference mode
            such as words that common sense deem it shouldn't be included. Defaults to False.

    Returns:
        bool: whether token passes filter checks mentioned above
    """

    filters = token is not None and tag not in ("CD",) and token not in stopwords and min_len <= len(token) <= max_len
    if commonsense_filter:
        filters = filters and token_commonsense_filter(token)
    return filters 

def token_regex(token: str, regex=PAT_ALPHABETIC) -> str:
    """Apply regex to token

    Args:
        token (str): input token as string
        regex (re.Pattern, optional): compiled regex pattern. Defaults to constant PAT_ALPHABETIC.

    Returns:
        str: token with regex applied, None if nothing matched
    """
    reg = regex.findall(token)
    token = reg[0][0] if reg != [] else None 
    return token

def token_preprocess(token: Word, tag: str) -> str:
    """Preprocess token by lemmatization, lower, convert to str and apply regex

    Args:
        token (Word): textblob token as result of textblob tokenization
        tag (str): corresponding textblob POS tag of token (see https://www.geeksforgeeks.org/python-part-of-speech-tagging-using-textblob/)

    Returns:
        str: preprocessed token
    """
    token = token.lemmatize(wordnet_tag(tag)).lower()
    token = token_regex(token)
    return token

def wordnet_tag(tag: str) -> str:
    """Helper function for context-aware lemmatization using inferred POS tags

    Args:
        tag (str): POS tag from textblob

    Returns:
        str: 'n' or 'v' representing POS category
    """
    tag_abbreviation = tag[0].lower()
    return tag_abbreviation if tag_abbreviation in ['n', 'v'] else 'n'

def document_length(document: str) -> int:
    """Calculate simple word count of raw document using gensim simple preprocessing.

    Args:
        document (str): input raw document string

    Returns:
        int: word count of input document
    """
    tokens = simple_preprocess(document)
    tokens = [token for token in tokens if token_commonsense_filter(token)]
    return len(tokens)

def document_to_tokens(document: str, use_textblob=True, return_textblob=False, commonsense_filter=False) -> list:
    """Tokenise and preprocess raw document string. Preprocess includes lemmatization (only if use_textblob==True, 
        stopword removal, lower case, length filter, regex match to remove punctuation and remove numbers.)

    Args:
        document (str): input document string on one line.
        use_textblob (bool, optional): method to tokenise and preprocess document. If True, use textblob tokenization: this
            also includes textblob lemmatization (but is slower). If False, use gensim simple preprocess (no lemmatization). All 
            other functionality is common to both methods. Defaults to True.
        return_textblob (bool, optional): whether to return the blob created during tokenization when use_textblob==True.
            Defaults to False.
        commonsense_filter (bool, optional): whether to filter additional stopwords in inference mode
            such as words that common sense deem it shouldn't be included. Defaults to False.

    Returns:
        if return_textblob==True and use_textblob==True:
            list: tokens from document tokenization
            textblob.TextBlob: blob of the document from document tokenization
        else:
            list: tokens from document tokenization

    """
    if use_textblob:
        blob = TextBlob(document)
        tokens_tags = [(token_preprocess(token, tag), tag) for token, tag in blob.tags]
        tokens_filtered = [token for token, tag in tokens_tags if token_filter(token, tag, commonsense_filter=commonsense_filter)]
    else:
        tokens = simple_preprocess(document)
        tokens_filtered = remove_stopword_tokens(tokens, stopwords=STOPWORDS)
    
    return (tokens_filtered, blob) if use_textblob and return_textblob else tokens_filtered

class TrainingCorpus:
    """Class representing input training corpus for training word2vec models. Wraps an iterable to read corpus (1 line = 1 document), and tokenize each line.
        Includes functionality to perform tokenization offline before training to save time.
    """
    def __init__(self, corpus: str, tokenize_before_training=False, use_textblob=True, base_fn="data/corpus"):
        """Initialise training corpus.

        Args:
            corpus (str): name of corpus (must be present in data/corpus folder)
            tokenize_before_training (bool, optional): perform tokenization offline before training. Make sure to set this as True if tokenization is expensive.
            If True, tokens are saved in npy format after tokenization so tokenization doesn't need to be performed again. Defaults to False.
            use_textblob (bool, optional): whether tokenization should use textblob (includes lemmatization but is expensive). Defaults to True.
            base_fn (str, optional): data/corpus folder. Defaults to "data/corpus".
        
        Raises:
            FileNotFoundError: corpus file not found in data/corpus folder.
        """
        self.fn = f'{base_fn}/{corpus}.txt'
        self.tokenize_before_training = tokenize_before_training
        self.use_textblob = use_textblob
        
        if self.tokenize_before_training:
            try:
                self.corpus_tokens = np.load(f'{base_fn}/{corpus}_tokens.npy', allow_pickle=True)
            except FileNotFoundError:
                self.corpus_tokens = []

                with open(self.fn, "r") as f:
                    documents = f.readlines()
                    for document in tqdm(documents):
                        self.corpus_tokens.append(document_to_tokens(document, use_textblob=self.use_textblob))
                        
                np.save(f'{base_fn}/{corpus}_tokens.npy', np.array(self.corpus_tokens, dtype="object"))
                    
    def __iter__(self):
        if self.tokenize_before_training:
            for tokens in self.corpus_tokens:
                yield tokens
        else:
            for document in open(self.fn):
                tokens = document_to_tokens(document, use_textblob=self.use_textblob)
                yield tokens
