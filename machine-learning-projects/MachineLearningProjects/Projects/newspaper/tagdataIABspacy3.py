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




import csv

from fuzzywuzzy import fuzz
from fuzzywuzzy import process



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
text1=""



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


import en_core_web_sm
nlp = en_core_web_sm.load()

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
                       if "History" not in x:
                           words1.append(x)
    print(words1)
    words2 = words2 + words1
    category = words2
    print(category)



highestScore=0
similarSentence=""
for x in category:

    highestScore1 = 0
    similarSentence1 = ""
    i = 0

    fname = "segments1.txt"
    with open(fname) as f:
        content = f.readlines()

    content = [k.strip() for k in content]

    for y in content:
        sentence_2 = y
        i = i + 1


        sen1_sen2_similarity = fuzz.ratio(x,y)

        if sen1_sen2_similarity > highestScore:
            highestScore = sen1_sen2_similarity
            similarSentence = y
        print(highestScore)
        print(similarSentence)
        print(i)
    if highestScore1 < highestScore:
        highestScore1=highestScore
        similarSentence1=similarSentence

fuzzyScore = highestScore1
fuzzyCategory = similarSentence1


highestScore=0
similarSentence=""
for x in category:

    highestScore1 = 0
    similarSentence1 = ""
    i = 0

    fname = "segments1.txt"
    with open(fname) as f:
        content = f.readlines()

    content = [k.strip() for k in content]

    for y in content:
        sentence_2 = y
        i = i + 1

        doc1 = nlp(x)
        doc2 = nlp(y)
        sen1_sen2_similarity = doc1.similarity(doc2)

        if sen1_sen2_similarity > highestScore:
            highestScore = sen1_sen2_similarity
            similarSentence = y
        print(highestScore)
        print(similarSentence)
        print(i)
    if highestScore1 < highestScore:
        highestScore1=highestScore
        similarSentence1=similarSentence

print(similarSentence1)
print(highestScore1)

print("fuzzyscore")
print(fuzzyScore)
print("fuzzy Category:"+fuzzyCategory)