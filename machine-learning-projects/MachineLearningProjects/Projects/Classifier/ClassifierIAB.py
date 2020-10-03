import classifier_LICA
import classifier_DFR
from urlparse import urlparse
import newspaper
from newspaper import Article
import sys
reload(sys)
sys.setdefaultencoding('utf8')
url="https://www.setindia.com/in-en/shows/kaun-banega-crorepati/33"
article = Article(url)
article.download()
article.parse()
article.nlp()
text1 = article.summary
print(text1)
#text2 = " ".join(article.keywords)
#print(text2)
text3 = article.title
print(text3)
classifier = classifier_LICA.LICA()
classifier1 = classifier_DFR.DFR()
print(classifier.classify(url,title=text1))
print(classifier1.classify(url,title=text1))