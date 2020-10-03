from newspaper import Article
import MySQLdb as mdb
import sys
import newspaper


womansera_paper = newspaper.build('https://flipkart.com')
for category in womansera_paper.category_urls():
    print(category)
  #  con = mdb.connect('205.147.102.47', 'root',
   #     'qwerty12@', 'middleware');

#with con:

    #cur = con.cursor()
    #cur.execute("SELECT * FROM Article")
   # cur1 = con.cursor()
    #rows = cur.fetchall()

   # for article in cnn_paper.articles:
      #try:
      # print(article.url)
#url = 'http://www.siliconindia.com/online_courses/dotnet_certification-cid-13.html'
url =  	"http://blogs.siliconindia.com/facilitymanagementservices/What_are_Tier_II_and_Tier_III_Cities-bid-52pLs73x43343767.html"
#url='http://tv.puthiyathalaimurai.com/vod/news/special-news/35/93583/may18'
#url = 'https://thenortheasttoday.com/naga-peace-accord-update-premature-release-of-peace-pact-would-hamper-national-security-rn-ravi'
url='http://www.siliconindia.com/online_courses/java_certification-cid-2.html'
url='http://cuberoot.co/unlock-the-power-of-data-driven-marketing-data-management-platforms/'
article = Article(url)
print(article.canonical_link)
article.download()
#print(article.html)
article.parse()
a=article.authors
#b=article.publish_date
#c=article.top_image
#d=article.title
e=article.meta_keywords
article.nlp()
words =article.keywords
print(words)
from sematch.semantic.graph import SimGraph
from sematch.semantic.similarity import WordNetSimilarity
from sematch.nlp import Extraction, word_process
from collections import Counter
words = word_process(words)
print(words)
wns = WordNetSimilarity()
word_graph = SimGraph(words, wns.word_similarity)
word_scores = word_graph.page_rank()
words, scores = zip(*Counter(word_scores).most_common(2))
print(words)

       #cur1.execute(sql)
       #con.commit()

     # except Exception:
     #     pass