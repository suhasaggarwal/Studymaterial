import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper

fname="mobilephoneurls.txt"
with open(fname) as f:
    content = f.readlines()

con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');


brand= ""
description=""
productImage=""
productId=""
price=""
category=""
seller=""
title=""


content = ['https://www.yaantra.com/letv-x526-le-tv2-grey-32gb.html']
for x in content:
    r = requests.get(x.strip())
    soup = BeautifulSoup(r.content, "html.parser")
    i=0
    for span in soup.findAll("div",class_="product-name"):
        for span1 in span.findAll("span"):
            i=i+1
            if i==1:
               title=span1.text.strip()
            if i==2:
               seller = span1.text.strip()
    print(title)
    print(seller)

    i=0
    for span in soup.findAll("div", class_="tab-content"):
        a = span.findAll("tr")
        while i < len(list(a)):
                if a[i].text == "Color":
                   color = "Color:"+a[i+1].text

                if a[i].text == "Brand":
                   brand = "Brand:" + a[i + 1].text

                if a[i].text == "Operating System":
                   os = "Operating System" + a[i + 1].text

                if a[i].text == "Rear Camera":
                   rearCamera = "Rear Camera:" + a[i + 1].text

                if a[i].text == "Front Camera":
                   frontCamera = "Front Camera:" + a[i + 1].text

                if a[i].text == "Internal Memory":
                   internalMemory = "Internal Memory:" + a[i + 1].text

                if a[i].text == "External Memory":
                   externalMemory = "External Memory:" + a[i + 1].text

                if a[i].text == "Ram":
                   ram = "Ram:" + a[i + 1].text

                if a[i].text == "Processor":
                   processor = "Processor:" + a[i + 1].text

                if a[i].text == "Screen Size":
                   screensize = "Screen Size:" + a[i + 1].text

                if a[i].text == "Network":
                   network = "Network:" + a[i + 1].text


    print(brand)

    with con:

         cur = con.cursor()

         query = """INSERT INTO ProductDetails (ProductUrl,Brand,Description,Price,Seller,EcommerceSegment,IABSegment,ProductId,ProductImage) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)"""
         print(query)
       #  cur.execute(query, (x, brand, description, price, seller, category,"",productId,productImage))
    category = ""