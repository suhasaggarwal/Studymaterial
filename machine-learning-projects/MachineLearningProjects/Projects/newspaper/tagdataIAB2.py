import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article
from sematch.semantic.similarity import EntitySimilarity
#entity_sim = EntitySimilarity()
#print(entity_sim.relatedness('http://dbpedia.org/resource/Apple_Inc.','http://dbpedia.org/resource/Steve_Jobs'))

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



article = Article("http://www.siliconindia.com/news/startups/14-Tech-Startups-Graduate-From-Microsoft-Accelerator-nid-203206-cid-100.html")
article.download()
article.parse()
article.nlp()
print(article.keywords)
print(article.meta_keywords)
text1 = ' '.join(article.keywords)
text1=article.title

#text1=article.meta_keywords[0]
print(text1)
#text1 = "IVES Black Rayon Printed Casual Full Sleeve Straight Kurtas For Women"

lunch_annotations = tagme.annotate(text1)
#lunch_annotations = tagme.annotate("flower decor")
#lunch_annotations = tagme.annotate("raees shahrukh khan")
# lunch_annotations = tagme.annotate("Kwality Walls is best ice cream brand")
category = []
cleanedcategories = []
# Print annotations with a score higher than 0.1
entityTitle = []
i=0
for ann in lunch_annotations.get_annotations(0.1):
    print(ann.entity_title)
    print(ann.score)
    textv1 = 'http://dbpedia.org/resource/' + ann.entity_title.replace(" ", "_")
    if ann.score > 0.2:
       cleanedcategories.append(ann.entity_title)


                    #entityTitle.append(ann.entity_title)
                    #print(entityTitle)
                    #i=i+1


#for x in entityTitle:
    #for y in entityTitle:
        #print("Word Similarity:"+x+","+y)
        #print(entity_sim.similarity(x,y))
        #print(wns.word_similarity(x,y,'li'))



             #   print(mediawikiapi.WikipediaPage(ann.entity_title).categories)



categorycheck = ' '.join(category)
categorycheck = categorycheck.lower()
print(categorycheck)
category=cleanedcategories
print(cleanedcategories)
for row1 in reader5:
  #  print(row)
    sp = []
    row1a = ",".join(row1)
    sp = row1a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp[0], x, 'li')>0.5:
           print("Word Similarity:" + sp[0] + "," + x)
           print(wns.word_similarity(sp[0], x, 'li'))


for row2 in reader4:
    sp = []
    row2a = ",".join(row2)
    sp1 = row2a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp1[0], x, 'li')>0.5:
           print("Word Similarity:" + sp1[0] + "," + x)
           print(wns.word_similarity(sp1[0], x, 'li'))

for row3 in reader3:
    sp = []
    row3a = ",".join(row3)
    sp2 = row3a.split(',')
    for x in category:

        # print(entity_sim.similarity(x,y))
       if wns.word_similarity(sp2[0], x, 'li') >0.5:
           print("Word Similarity:" + sp2[0] + "," + x)
           print(wns.word_similarity(sp2[0], x, 'li'))

for row4 in reader2:
    sp = []
    row4a = ",".join(row4)
    sp3 = row4a.split(',')
    for x in category:
       if wns.word_similarity(sp2[0], x, 'li') > 0.5:
          print("Word Similarity:" + sp3[0] + "," + x)
       #print(entity_sim.similarity(x,y))
          print(wns.word_similarity(sp3[0], x, 'li'))

for row5 in reader1:
    sp = []
    row5a = ",".join(row5)
    sp4 = row5a.split(',')
    #for x in category:
       #print("Word Similarity:" + sp4[0] + "," + x)
        # print(entity_sim.similarity(x,y))
       #print(wns.word_similarity(sp4[0], x, 'li'))






   # print(wikipedia.)
#print(category)
#print(anydup(category))


