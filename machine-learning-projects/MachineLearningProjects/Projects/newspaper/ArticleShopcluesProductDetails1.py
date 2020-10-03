import requests
from lxml import html
from bs4 import BeautifulSoup
from urllib.parse import urlparse
import MySQLdb as mdb
import sys
import newspaper

fname="UrlListSimulatedTraffic.txt"
with open(fname) as f:
    content = f.readlines()

con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:
    cur = con.cursor()
    cur.execute("Select * from ProductDetails Where ProductUrl Like '%yaantra%'");
    rows = cur.fetchall()

    for row in rows:
       try:
         x=row[1]
         x=x.strip()
         brand=row[2]
         brand=brand.strip()
         parse_object = urlparse(x)
         productname= parse_object.path
         seller=row[5]
         seller=seller.strip()
         title=row[10]

         title=title.strip()
         productImage =row[9]
         productImage = productImage.strip()
         cur = con.cursor()
         print(x,brand,seller,title,productImage,productname)
         query = """INSERT INTO Article (ArticleUrl,Tags,Author,ArticleTitle,MainImage,SiteId,ArticleName) VALUES (%s,%s,%s,%s,%s,%s,%s)"""
         print(query)
         cur.execute(query, (x, brand, seller,title,productImage,"6",productname))
         con.commit()
       except Exception:
           pass