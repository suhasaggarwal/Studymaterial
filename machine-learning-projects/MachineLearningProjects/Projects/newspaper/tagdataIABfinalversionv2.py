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
#text1="worlds largest radio telescope begins searching aliens"



text1="home appliances"

#text1="Fashion Men's Clothing T-Shirts Polos T-Shirts"
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


wikicategories = []

for x in jData:
    if x['rho']<0.1:
        jData.remove(x)


for x in jData:
    wikicategories = wikicategories+x['category']
    textv1 = 'http://dbpedia.org/resource/' +  x['Title'].replace(" ", "_")
    print(textv1)

    tv1 = EntityFeatures().features(textv1)
    # words = Extraction().extract_nouns(tv1['abstract'])
    #words = Extraction().extract_words(tv1['abstract'])
    cats = Extraction().category_features(tv1['category'])
    words = Extraction().category2words(cats)
    words =  ' '.join(words)
    words = Extraction().extract_words(words)
    words = word_process(words)
    print(words)
    wns = WordNetSimilarity()
    word_graph = SimGraph(words, wns.word_similarity)
    word_scores = word_graph.page_rank()
    words, scores = zip(*Counter(word_scores).most_common(5))
    words1 = words1 + list(words)



print(words1)
words1 = list(set(words1))
words = word_process(words1)
print(words)
wns = WordNetSimilarity()
word_graph = SimGraph(words, wns.word_similarity)
word_scores = word_graph.page_rank()
words, scores = zip(*Counter(word_scores).most_common(10))
print(words)
cleanedcategories=list(words)

text1 = ' '.join(cleanedcategories)


#entityTitle.append(ann.entity_title)
                    #print(entityTitle)
                    #i=i+1


#for x in entityTitle:
    #for y in entityTitle:
        #print("Word Similarity:"+x+","+y)
        #print(entity_sim.similarity(x,y))
        #print(wns.word_similarity(x,y,'li'))



             #   print(mediawikiapi.WikipediaPage(ann.entity_title).categories)
r = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text="+text1)
jData = json.loads(r.content)

cleanedcategories1= []

for x in jData:
    if x['rho']<0.1:
        jData.remove(x)


for x in jData:
    cleanedcategories1.append(x['Title'])

print(cleanedcategories1)
category=cleanedcategories1
print(category)
print(cleanedcategories)
from urlparse import urlparse
import classifier_LICA
import classifier_DFR
print(cleanedcategories)
text3 = ' '.join(cleanedcategories1)
print(wikicategories)
text5 = ' '.join(wikicategories)
print(text3)
print(text5)
classifier = classifier_LICA.LICA()
classifier1 = classifier_DFR.DFR()
print(classifier.classify("",title=text3.lower()))
print(classifier1.classify("",title=text3.lower()))
print(classifier.classify("",title=text5.lower()))
print(classifier1.classify("",title=text5.lower()))