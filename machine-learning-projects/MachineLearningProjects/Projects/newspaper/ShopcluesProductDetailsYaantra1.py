import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper



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

cur1 = con.cursor()
cur1.execute("SELECT ProductUrl FROM ProductDetails Where ProductUrl Like '%yaantra%'")
rows = cur1.fetchall()

values = []



content = ['https://www.yaantra.com/letv-x526-le-tv2-grey-32gb.html']
content = ['https://www.yaantra.com/products/mobile/refurbished/excellent/refurbished-lenovo-z2-plus-black-64gb.html']
for x in rows:
   try:
    x=x[0]
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
    #print(title)
    #print(seller)

    i=0
    for span in soup.findAll("div", class_="tab-content"):
        for span1 in span.findAll("tr"):
            i = i + 1
            if i == 2:
               brand = span1.text.strip().replace("\n",",")
            if i == 4:
               property  = span1.text.strip()
    if ',' in brand:
       brand = brand.split(",")[1]
    #print(brand)

    for span in soup.findAll("div", class_="product-image-gallery"):
        for span1 in span.findAll("img"):
            productImage=span1.get('src','')
            break
        break


    #print(productImage)

    for span in soup.findAll("div", class_="breadcrumbs"):
        for span1 in span.findAll("li"):
            category = category+span1.text.strip()
        break

    categorypath = []
    categorypath1 = []
    #print(category.replace("\n",''))
    categorypath = category.split("/")
    categorypath = categorypath[1:-1]
    categorypath1 = '_'.join(categorypath).replace("\n","")
    category=""
    #print(categorypath1)
    if " " in categorypath1:
        categorypath1= ""




    with con:

         cur = con.cursor()

         query = "Update ProductDetails SET ProductTitle='%s',Brand='%s',Seller='%s',EcommerceSegment='%s',ProductImage='%s' WHERE ProductUrl = '%s'" % (title.strip(),brand,seller,categorypath1,productImage,x)
         print(query)
         cur.execute(query)
         con.commit()
   except Exception:
       pass