from newspaper import Article
import MySQLdb as mdb
import sys
import urllib.request
import requests
import json
import sys
import csv
import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:

    cur = con.cursor()
    cur.execute("Select MAX(Id) from Article");
    rows = cur.fetchall()
    row = rows[0]
    print("SELECT * FROM Article Where Id > "+str(row[0]))
    cur.execute("SELECT * FROM Article Where ArticleUrl like '%cuberoot%'")
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
       article.nlp()
       r1 = requests.get("http://101.53.130.215:8080/SemanticClassifierv2/getTextAnalysis?text=" + article.text)

       jData = json.loads(r1.content)
       #print(jData)

       for x in jData:
           if x['rho'] < 0.1:
               jData.remove(x)
       y=set()
       k=""
       for x in jData:
          print(x['Title'])
          y.add(x['Title'])
       k=','.join(str(s) for s in y)
       print(k)
       print(article.summary)
       e=article.meta_keywords
       e=",".join(e)
       print(e)
      # sql="UPDATE Article SET Tags = '%s' WHERE ArticleUrl = '%s'" % (k,url)
      # print(sql)
     #  cur1.execute(sql)
     #  con.commit()

      except Exception:
          pass
