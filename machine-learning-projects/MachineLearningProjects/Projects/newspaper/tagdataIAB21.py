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







for row1 in reader5:
  #  print(row)
    sp = []
    row1a = ",".join(row1)
    sp = row1a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp[0], x, 'res')>5.0:
           print("Word Similarity res:" + sp[0] + "," + x)
           print(wns.word_similarity(sp[0], x, 'res'))


for row2 in reader4:
    sp = []
    row2a = ",".join(row2)
    sp1 = row2a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp1[0], x, 'res')>5.0:
           print("Word Similarity res:" + sp1[0] + "," + x)
           print(wns.word_similarity(sp1[0], x, 'res'))

for row3 in reader3:
    sp = []
    row3a = ",".join(row3)
    sp2 = row3a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp2[0], x, 'res') >5.0:
           print("Word Similarity:" + sp2[0] + "," + x)
           print(wns.word_similarity(sp2[0], x, 'res'))

for row4 in reader2:
    sp = []
    row4a = ",".join(row4)
    sp3 = row4a.split(',')
    for x in category:
       if wns.word_similarity(sp3[0], x, 'res') >5.0:
          print("Word Similarity:" + sp3[0] + "," + x)
       #print(entity_sim.similarity(x,y))
          print(wns.word_similarity(sp3[0], x, 'res'))

for row5 in reader1:
    sp = []
    row5a = ",".join(row5)
    sp4 = row5a.split(',')
    for x in category:
        if wns.word_similarity(sp4[0], x, 'res') > 5.0:
            print("Word Similarity:" + sp4[0] + "," + x)
            # print(entity_sim.similarity(x,y))
            print(wns.word_similarity(sp4[0], x, 'res'))


