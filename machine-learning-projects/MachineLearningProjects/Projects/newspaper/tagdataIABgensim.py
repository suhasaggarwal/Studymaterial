import tagme
import wikipedia
import wikidata
import collections
from newspaper import Article
from sematch.semantic.similarity import EntitySimilarity
#entity_sim = EntitySimilarity()
#print(entity_sim.relatedness('http://dbpedia.org/resource/Apple_Inc.','http://dbpedia.org/resource/Steve_Jobs'))
import requests
import json
import sys
import csv



from sematch.semantic.similarity import WordNetSimilarity
wns = WordNetSimilarity()

tagme.GCUBE_TOKEN = "1a65e79d-4bd6-453a-946c-a4a1f8a13acc-843339462"

import csv

reader1 = csv.reader(open('level1categorymap.csv', 'r'))


reader2 = csv.reader(open('level2categorymap.csv', 'r'))


reader3 = csv.reader(open('level3categorymap.csv', 'r'))


reader4 = csv.reader(open('level4categorymap.csv', 'r'))


reader5 = csv.reader(open('level5categorymap.csv', 'r'))



def anydup(thelist):
  seen = []
  for x in thelist:
    if x in seen: print(x)
    seen.append(x)



#article = Article("https://www.socialpost.news/telugu/national/trs-mp-kavitha-get-cabinet-berth-in-modi-govt/")
#article.download()
#article.parse()
#article.nlp()
#print(article.keywords)
#print(article.meta_keywords)
#text1 = ' '.join(article.keywords)
text1="Sims is a good video game"


#text1=article.meta_keywords[0]
print(text1)
#text1 = "IVES Black Rayon Printed Casual Full Sleeve Straight Kurtas For Women"

#lunch_annotations = tagme.annotate(text1)
#lunch_annotations = tagme.annotate("flower decor")
#lunch_annotations = tagme.annotate("raees shahrukh khan")
# lunch_annotations = tagme.annotate("Kwality Walls is best ice cream brand")

r = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text="+text1)

jData = json.loads(r.content)
print(jData)
category = []
cleanedcategories = []
# Print annotations with a score higher than 0.1
entityTitle = []
words1 = []
from sematch.semantic.graph import SimGraph
from sematch.semantic.similarity import WordNetSimilarity
from sematch.nlp import Extraction, word_process
from sematch.semantic.sparql import EntityFeatures
from collections import Counter
from sematch.nlp import RAKE


for x in jData:
    if x['rho']<0.1:
        jData.remove(x)

words1 = []
words2 = []
for x in jData:


    words = wikipedia.page(x['Title']).categories
    for x in words:
        if "Article" not in x:
           if "articles" not in x:
               if "history" not in x:
                  if "Pages" not in x:
                      print(x)
                      r = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text=" + x)
                      jData1 = json.loads(r.content)

                      for x in jData1:
                          if x['rho'] < 0.1:
                              jData1.remove(x)

                      for x in jData1:
                          y=x['Title']
                          words1.append(y)
    print(words1)
    words2 = words2 + words1
    category = words2
    print(category)



documents = []

for row in reader1:
    documents.append(' '.join(row))


stoplist = set(['is', 'how'])

texts = [[word.lower() for word in document.split()
          if word.lower() not in stoplist]
         for document in documents]


from gensim import corpora, models, similarities
from collections import defaultdict
import numpy as np
print(texts)
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
doc = category[6]
vec_bow = dictionary.doc2bow(doc.lower().split())

# convert the query to LSI space
vec_lsi = lsi[vec_bow]
index = similarities.MatrixSimilarity(lsi[corpus])


from nltk.corpus import brown
sentences = brown.docs()
model = models.Doc2Vec(sentences, min_count=1)
model.save("brown_model")
print ("Brown corpus model saved")
model = models.Doc2Vec.load("brown_model")

def get_vector(word):
    return model.docvecs[model.vocab[word].index]

def calculate_similarity(sentence, word):
   vec_a = get_vector(sentence)
   vec_b = get_vector(word)
   sim = np.dot(vec_a, vec_b)
   return sim

print(calculate_similarity(category[6]," ".join(texts[0])))


# perform a similarity query against the corpus
sims = index[vec_lsi]
sims = sorted(enumerate(sims), key=lambda item: -item[1])
print(sims)