from newspaper import Article
import MySQLdb as mdb
import sys

import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');


#url = "https://www.wittyfeed.com/videos/65056/dharmesh-sir-first-audition-video-of-dance-india-dance"
#url = "https://www.wittyfeed.com/story/65084/nora-fateh-ali-make-up-video-on-instagram-goes-viral"
url = "https://www.digit.in/internet/twitter-also-sold-data-to-cambridge-analytica-researcher-40774.html"
article = Article(url)
article.download()
     #  print(article.html)
article.parse()
a=article.authors
a=",".join(a)
print(a)
#b=article.publish_date
#print(b)
c=article.top_img
print(c)
d=article.title
print(d)
e=article.tags
e=",".join(e)
print(e)
#sql="UPDATE Article SET Author= '%s',Tags = '%s', PublishDate = '%s', MainImage = '%s',ArticleTitle = '%s' WHERE ArticleUrl = '%s'" % (a,e,b,c,d,url)
#print(sql)
  #     cur1.execute(sql)
  #     con.commit()



