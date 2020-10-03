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
    cur.execute("SELECT * FROM Article Where Id > 42933")
    cur1 = con.cursor()
    rows = cur.fetchall()

    for row in rows:
      try:
       #print(row)
       url = row[1]
       url = "https://www.foodmate.me/food-combinations-you-should-avoid-67197"
       article = Article(url)
       article.download()
     #  print(article.html)
       article.parse()
       a=article.authors
       print(a)
       b=article.publish_date
       print(b)
       c=article.top_image
       print(c)
       d=article.title
       print(d)
       sql="UPDATE Article SET Author= '%s', PublishDate = '%s', MainImage = '%s',ArticleTitle = '%s' WHERE ArticleUrl = '%s'" % (a,b,c,d,url)
       print(sql)
       cur1.execute(sql)
       con.commit()

      except Exception:
          pass