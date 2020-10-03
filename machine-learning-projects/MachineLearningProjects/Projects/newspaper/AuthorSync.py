import requests
from lxml import html
from bs4 import BeautifulSoup

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
    cur.execute("Select Distinct(Tags) from ProductDetails");
    rows = cur.fetchall()

    for row in rows:

         x=row[0]
         x=x.strip()

         cur = con.cursor()
         print(x)
         query = """INSERT INTO Tag (Tag) VALUES (%s)"""
         print(query)
        # cur.execute(query,x)
