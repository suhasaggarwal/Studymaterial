import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper



con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:
    cur = con.cursor()
    cur.execute("Select ArticleUrl from Article where ArticleUrl Like '%yaantra%'")
    rows = cur.fetchall()

    for row in rows:

         x=row[0]
         x=x.strip()

         cur = con.cursor()
         print(x)
         try:
             query = """INSERT INTO ProductDetails (ProductUrl,Brand,Description,Price,Seller,EcommerceSegment,IABSegment,ProductId,ProductImage) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)"""
             print(query)
             cur.execute(query, (x, "","","", "", "", "","","") )
             con.commit()
         except Exception:
            pass
