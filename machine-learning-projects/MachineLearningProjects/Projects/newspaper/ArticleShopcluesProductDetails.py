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
        'qwerty12@', 'middleware1');

with con:
    cur = con.cursor()
    cur.execute("Select * from ProductDetails");
    rows = cur.fetchall()

    for row in rows:

         x=row[1]
         x=x.strip()
         brand=row[2]
         brand=brand.strip()
         productname= x.split("/")[3].strip()
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
         cur.execute(query, (x, brand, seller,title,productImage,"5",productname))
