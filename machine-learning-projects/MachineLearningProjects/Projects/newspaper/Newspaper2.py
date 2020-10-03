from newspaper import Article
import MySQLdb as mdb
import sys
import string

import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:

    cur = con.cursor()
    cur.execute("Select MAX(Id) from Article");
    rows = cur.fetchall()
    row = rows[0]
    print("SELECT * FROM Article Where Id > "+str(row[0]))
    cur.execute("SELECT * FROM Article Where Id > 200")
    cur1 = con.cursor()
    rows = cur.fetchall()

    for row in rows:
      try:
       print(row)
       url = row[1]
       url = "https://www.socialpost.news/national/are-most-journalists-in-india-becoming-paid-activists-and-ruining-the-fourth-estate/"
       article = Article(url)
       article.download()
     #  print(article.html)
       article.parse()
       a=article.authors
       b=article.publish_date
       c=article.top_image
       d=article.title
       e=article.meta_keywords
       g=article.tags

       f=article.keywords
       h=','.join(g)

     #  i=article.
       print(e)
       print(g)
       print(f)
       print(h)
       sql="UPDATE Article SET Tags= '%s', MetaKeywords= '%s' WHERE ArticleUrl = '%s'" % (h,e,url)
       print(sql)
     #  cur1.execute(sql)
     #  con.commit()

      except Exception:
          pass