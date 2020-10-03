from newspaper import Article
import MySQLdb as mdb
import sys

import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:

    cur = con.cursor()
    cur.execute("Select MAX(Id) from Article");
    rows = cur.fetchall()
    row = rows[0]
    print("SELECT * FROM Article Where Id > "+str(row[0]))
    cur.execute("SELECT * FROM Article Where AdditionTime >= NOW() - INTERVAL 4 HOUR")
    cur1 = con.cursor()
    rows = cur.fetchall()

    for row in rows:
      try:
      # print(row)
       url = row[1]
       #url = "http://womansera.com/food/7-bitterly-honest-reasons-momos-worst-junk-food-ever"
       article = Article(url)
       article.download()
     #  print(article.html)
       article.parse()
       a=article.authors
       a=",".join(a)
       print(a)
       b=article.publish_date
       print(b)
       c=article.top_img
       print(c)
       d=article.title
       print(d)
       e=article.tags
       e=",".join(e)
       print(e)
       sql="UPDATE Article SET Author= '%s',Tags = '%s', PublishDate = '%s', MainImage = '%s',ArticleTitle = '%s' WHERE ArticleUrl = '%s'" % (a,e,b,c,d,url)
       print(sql)
       cur1.execute(sql)
       con.commit()

      except Exception:
          pass
