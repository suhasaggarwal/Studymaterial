import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article
import urllib.request
import requests
import json
import sys
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


text = "bmw%206%20series%20gran%20turismo%20is%20now%20in%20india%20";

r = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text="+text)

jData = json.loads(r.content)

print(jData)
print (jData[0]["Title"])

#article = Article("http://www.shopclues.com/ankur-plastic-vegetable-cutter-regular-1-piece-green.html")
#article.download()
#article.parse()
#article.nlp()
#print(article.keywords)
#print(article.meta_keywords)
#print(article.title)
#text = ' '.join(article.keywords)
#print(text)
category = []
# Print annotations with a score higher than 0.1
#for ann in lunch_annotations.get_annotations(0.1):
category = category + wikipedia.page(jData[0]["Title"]).categories
print(category)

categorycheck = ' '.join(category)
print(categorycheck)

for row in reader5:
  #  print(row)
    sp = []
    row1 = ",".join(row)
    sp = row1.split(',')
    for st in sp:
        st.replace('"', '')
        if st in categorycheck:
            print(sp[0])
            #sys.exit()
            break
        else:
            print("")



for row in reader4:
    sp = []
    row1 = ",".join(row)
    sp = row1.split(',')
    for st in sp:
        st.replace('"', '')
        if st in categorycheck:
            print(sp[0])
            #sys.exit()
            break
        else:
            print("")

for row in reader3:
    sp = []
    row1 = ",".join(row)
    sp = row1.split(',')
    for st in sp:
        st.replace('"', '')
        if st in categorycheck:
            print(sp[0])
            #sys.exit()
            break
        else:
            print("")

for row in reader2:
    sp = []
    row1 = ",".join(row)
    sp = row1.split(',')
    for st in sp:
        st.replace('"', '')
        if st in categorycheck:
            print(sp[0])
            #sys.exit()
            break
        else:
            print("")

for row in reader1:
    sp = []
    row1 = ",".join(row)
    sp = row1.split(',')
    for st in sp:
        st.replace('"', '')
        if st in categorycheck:
            print(sp[0])
            #sys.exit()
            break
        else:
            print("")
#nearest_match = wikipedia.search('board games')[0]
#nearestCategory = wikipedia.page(nearest_match).categories
#print(nearestCategory)
#print(ann.score)
#print(mediawikiapi.WikipediaPage(ann.entity_title).categories)

#print(anydup(category))


