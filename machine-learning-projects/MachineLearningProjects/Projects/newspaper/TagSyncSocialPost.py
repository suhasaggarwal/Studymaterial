from newspaper import Article
import MySQLdb as mdb
import sys
import string

import newspaper
from bs4 import BeautifulSoup
import requests


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:

    cur = con.cursor()
    cur.execute("Select MAX(Id) from Article");
    rows = cur.fetchall()
    row = rows[0]
    #print("SELECT * FROM Article Where Id > "+str(row[0]))
    id='socialpost'
    cur.execute("SELECT * FROM Article Where ArticleUrl Like '%%%s%%'" % (id))
    cur1 = con.cursor()
    rows = cur.fetchall()

    for row in rows:
      try:
       #print(row)
       url = row[1]
    #   url = "https://www.socialpost.news/national/are-most-journalists-in-india-becoming-paid-activists-and-ruining-the-fourth-estate/"
       article = Article(url)
       article.download()
     #  print(article.html)
       article.parse()
       a=article.authors
       author=a[0]
       #print(author)

       e=article.meta_keywords
      # g=article.tags
       e=",".join(e)
       tags = ""
       r = requests.get(url)
       soup = BeautifulSoup(r.content, "html.parser")

       for span in soup.findAll("meta", {"property": "article:tag"}):
           # print(span)
           #print(span['content'])
           if tags == "":
               tags = span['content']
           else:
               tags = tags + "," + span['content']

       #print(tags)

       #sql="UPDATE Article SET Tags= '%s', MetaKeywords= '%s',Author= '%s' WHERE ArticleUrl = '%s'" % (tags,e,url)
       sql = "UPDATE Article SET Tags= '%s', Author= '%s' WHERE ArticleUrl = '%s'" % (tags, author, url)

       print(sql)
       cur1.execute(sql)
       con.commit()

      except Exception:
          pass