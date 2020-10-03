import MySQLdb as mdb
import sys

import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper



con = mdb.connect('205.147.102.47', 'root',
      'qwerty12@', 'middleware1');



fname="UrlListSimulatedTraffic.txt"
with open(fname) as f:
    content = f.readlines()



with con:
    cur = con.cursor()
    productTitle = ""

    for x in content:
        r = requests.get(x.strip())
        soup = BeautifulSoup(r.content, "html.parser")

        # for div in soup.findAll("div",class_="pdp_info wrapper"):
        # for div1 in div.findAll("div",class_="breadcrums"):
        for b in soup.findAll("h1", attrs={"itemprop": "name"}):
            # print("Category:\n"+b.text.strip())
            productTitle = b.text
            productTitle = productTitle.strip()
            print(productTitle)

            try:

                sql = "UPDATE ProductDetails SET ProductTitle = '%s' WHERE ProductUrl like '%%%s%%'" % (
                productTitle.strip(), x.strip())
                print(sql)

                cur.execute(sql)
                con.commit()
            except Exception:
                print("In Exception")
                pass
            break


