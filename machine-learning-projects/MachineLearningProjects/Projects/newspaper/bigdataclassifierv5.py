import requests

'''

import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article
import urllib.request

import json
import sys
import csv


text1 = "http://in.ign.com/star-wars-battlefstar-wars-battlefront-2-watch-57-minutes-of-brand-new-gamepl"

import urllib
from bs4 import BeautifulSoup

soup = BeautifulSoup(urllib.urlopen(text1))
print(soup.title.string)

text = soup.title.string

r1 = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text="+text)





jData = json.loads(r1.content)
print(jData)

for x in jData:
    if x['rho']<0.2:
       jData.remove(x)
y=""
z=[]
k=""

with open('All_hidden_categories.txt', encoding="utf8") as f:
    hiddencategories = f.read().splitlines()
#text1=article.meta_keywords[0]


rankedcategories = {}
with open('ranked-categories.csv', encoding="utf8") as csv_file:
    for row in csv.reader(csv_file, delimiter='\t'):
        if len(row)>1:
            rankedcategories[row[0]] = row[1]
words1=[]
for x in jData:
     y=y+" "+x['Title']
     #z.append(x['category'])
     words = x['category']
     for p in words:
         if "Article" not in p:
             if "articles" not in p:
                 if "history" not in p:
                     if "Pages" not in p:
                         if p not in hiddencategories:
                             if p in rankedcategories:
                                 words1.append(p)
k=" ".join(str(v) for v in words1)
print(y)
print(k)
if y is "":
     y=text

'''

#y="emmanuel macron macron president france president of france elections marine le pen trump obama youngest ever leader"

y="salman khan kabir khan tubelight twitter emoji"
r1 = requests.get("http://101.53.130.215:82/IAB1/"+y.lower().replace("-",""))

print(r1.content)


