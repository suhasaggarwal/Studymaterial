from newspaper import Article
import MySQLdb as mdb
import sys
import string
from bs4 import BeautifulSoup
import requests

url ="https://www.socialpost.news/national/are-most-journalists-in-india-becoming-paid-activists-and-ruining-the-fourth-estate/"


#url="http://timesofindia.indiatimes.com/business/india-business/markets-finish-flat-for-second-day-in-row/articleshow/60423873.cms"

#url="http://indianexpress.com/article/education/ensure-no-agitation-takes-place-over-neet-supreme-court-directs-tamil-nadu-government/"
#url="http://www.shopclues.com/fabiano-fabsurya-2-burner-7mm-toughened-glasstop-gas-cooktop.html"
article = Article(url)
article.download()
     #  print(article.html)
article.parse()
a=article.authors
print(a[0])
#b=article.publish_date
#print(b)
#c=article.top_image
#print(c)
d=article.title
print("Title:"+d)
e=article.meta_keywords
print("Keywords:"+','.join(e))
g=article.tags
print("Tags:"+','.join(g))
tags = ""
r = requests.get(url)
soup = BeautifulSoup(r.content, "html.parser")

for span in soup.findAll("meta", {"property":"article:tag"}):
   # print(span)
    print(span['content'])
    if tags == "":
       tags = span['content']
    else:
        tags = tags +","+span['content']
for span in soup.findAll("meta", {"property": "article:section"}):
    # print(span)
    print(span['content'])

print(tags)
     #  cur1.execute(sql)
     #  con.commit()

