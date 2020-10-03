from gensim import corpora, models, similarities
from collections import defaultdict

documents = [
    'Car Insurance',  # doc_id 0
    'Car Insurance Coverage',  # doc_id 1
    'Auto Insurance',  # doc_id 2
    'Best Insurance',  # doc_id 3
    'How much is car insurance',  # doc_id 4
    'Best auto coverage',  # doc_id 5
    'Auto policy',  # doc_id 6
    'Car Policy Insurance',  # doc_id 7
]

stoplist = set(['is', 'how'])

texts = [[word.lower() for word in document.split()
          if word.lower() not in stoplist]
         for document in documents]

print texts
frequency = defaultdict(int)
for text in texts:
    for token in text:
        frequency[token] += 1
texts = [[token for token in text if frequency[token] > 1]
         for text in texts]
dictionary = corpora.Dictionary(texts)

# doc2bow counts the number of occurences of each distinct word,
# converts the word to its integer word id and returns the result
# as a sparse vector

corpus = [dictionary.doc2bow(text) for text in texts]
lsi = models.LsiModel(corpus, id2word=dictionary, num_topics=2)
doc = "giraffe poop car murderer"
vec_bow = dictionary.doc2bow(doc.lower().split())

# convert the query to LSI space
vec_lsi = lsi[vec_bow]
index = similarities.MatrixSimilarity(lsi[corpus])

# perform a similarity query against the corpus
sims = index[vec_lsi]
sims = sorted(enumerate(sims), key=lambda item: -item[1])

print sims