import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article

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



article = Article("http://www.shopclues.com/swipe-elite-note-16gb.html")
article.download()
article.parse()
article.nlp()
print(article.keywords)

text1 = ' '.join(article.keywords)

text1 = "Backpacks & Laptop Bags"

lunch_annotations = tagme.annotate(text1)
#lunch_annotations = tagme.annotate("flower decor")
#lunch_annotations = tagme.annotate("raees shahrukh khan")
# lunch_annotations = tagme.annotate("Kwality Walls is best ice cream brand")
category = []
# Print annotations with a score higher than 0.1
for ann in lunch_annotations.get_annotations(0.1):
    print(ann.entity_title)
    category1 = wikipedia.page(ann.entity_title).categories
    if "Article" not in category1:
        if "articles" not in category1:
            if "history" not in category1:
                category = category + category1
   # wikidata.
            print(ann.score)
 #   print(mediawikiapi.WikipediaPage(ann.entity_title).categories)

categorycheck = ' '.join(category)
categorycheck = categorycheck.lower()
print(categorycheck)

for row1 in reader5:
  #  print(row)
    sp = []
    row1a = ",".join(row1)
    sp = row1a.split(',')
    for st in sp:
        if st.lower() in categorycheck:
            print(sp[0])
            break
        else:
            None



for row2 in reader4:
    sp = []
    row2a = ",".join(row2)
    sp1 = row2a.split(',')
    for st in sp1:
        if st.lower() in categorycheck:
            print(sp1[0])
            break
        else:
            None

for row3 in reader3:
    sp = []
    row3a = ",".join(row3)
    sp2 = row3a.split(',')
    for st in sp2:
        if st.lower() in categorycheck:
            print(sp2[0])
            break
        else:
            None

for row4 in reader2:
    sp = []
    row4a = ",".join(row4)
    sp3 = row4a.split(',')
    for st in sp3:
        if st.lower() in categorycheck:
            print(sp3[0])
            break
        else:
            None

for row5 in reader1:
    sp = []
    row5a = ",".join(row5)
    sp4 = row5a.split(',')
    for st in sp4:
        if st.lower() in categorycheck:
            print(sp4[0])
            break
        else:
            None







   # print(wikipedia.)
#print(category)
#print(anydup(category))


