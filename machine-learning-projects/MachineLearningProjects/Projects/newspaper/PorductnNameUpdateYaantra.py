import requests
from lxml import html
from bs4 import BeautifulSoup
from urllib.parse import urlparse
import MySQLdb as mdb
import sys
import newspaper

con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

values = []

with con:
    cur = con.cursor()
    cur.execute("Select * from ProductDetails Where ProductUrl Like '%yaantra%'");
    rows = cur.fetchall()

    for row in rows:
       try:
         x=row[1]
         x=x.strip()
         parse_object = urlparse(x)
         articlename= parse_object.path
      #   articlename = articlename.replace("-"," ")
       #  articlename = articlename.replace(".html","")
        # articlename = articlename.capitalize()
         print(articlename)

         query = "Update Article SET ArticleName='%s' WHERE ArticleUrl = '%s'" % (articlename, x)
         print(query)
         cur.execute(query)
         con.commit()
       except Exception:
           pass