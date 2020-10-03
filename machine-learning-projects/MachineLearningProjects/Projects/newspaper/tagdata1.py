import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article
tagme.GCUBE_TOKEN = "1a65e79d-4bd6-453a-946c-a4a1f8a13acc-843339462"


def anydup(thelist):
  seen = []
  for x in thelist:
    if x in seen: print(x)
    seen.append(x)


article = Article("http://www.shopclues.com/flashing-lights-for-bikes-set-of-2.html")
article.download()
article.parse()
article.nlp()
print(article.keywords)
print(article.meta_keywords)
print(article.title)
text = ' '.join(article.keywords)
print(text)
lunch_annotations = tagme.annotate(text)
#lunch_annotations = tagme.annotate("flower decor")
#lunch_annotations = tagme.annotate("raees shahrukh khan")
# lunch_annotations = tagme.annotate("Kwality Walls is best ice cream brand")
category = []
# Print annotations with a score higher than 0.1
for ann in lunch_annotations.get_annotations(0.1):
    print(ann.entity_title)
    category = category + wikipedia.page(ann.entity_title).categories
   # wikidata.
    print(ann.score)
    print(mediawikiapi.WikipediaPage(ann.entity_title).categories)
   # print(wikipedia.)
print(category)
print(anydup(category))


