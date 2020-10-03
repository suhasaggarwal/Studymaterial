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
        'qwerty12@', 'middleware');



brand= ""
description=""
productImage=""
productId=""
price=""
category=""
seller=""

for x in content:
    r = requests.get(x.strip())
    soup = BeautifulSoup(r.content, "html.parser")

    for span in soup.findAll("div",class_="prd_mid_info"):
        for a in span.findAll(itemprop="description"):
            #print("Description:"+a.text.strip())
            description=a.text.strip()
        for a in span.findAll(itemprop="brand"):
            #print("Brand:"+a.text.strip())
            brand=a.text.strip()
        for a in span.findAll("img"):
            #print("Product Image:"+a['src'])
            productImage=a['src']
        for a in span.findAll(class_="pID"):
            #print("Product Id:"+a.text.strip())
            productId=a.text.strip()
        for a in span.findAll(class_="f_price"):
            #print("Price :"+a.text.strip())
            price=a.text.strip()
#for div in soup.findAll("div",class_="pdp_info wrapper"):
    #for div1 in div.findAll("div",class_="breadcrums"):
    for b in soup.findAll("span",attrs={"itemprop":"title"}):
        #print("Category:\n"+b.text.strip())
        category=category+">"+b.text

    for span in soup.findAll("div",itemprop="seller"):
        for seller in span.findAll(itemprop="name"):
            #print("Seller:"+seller.text.strip())
            seller=seller.text.strip()

    category = category.strip()
    print(brand)
    print(seller)
    print(productId)
    print(productImage)
    print(category)
    print(price)

    productId = productId.split(":")[1].strip()
    description=description.replace("\n",",")
    print(description + "\n")
    productTopicsMeta = description.split(",")
    productTopics="Brand:"+brand
    productTopics = productTopics.strip()
    i=0
    for k in productTopicsMeta:

        if ":" in k:
            k1 = k
            if i==0:
               if brand!='':
                  productTopics = productTopics + ","+k1.strip()
               else:
                   productTopics=k1.strip()

            else:
                productTopics = productTopics + "," + k1.strip()



        i=i+1

    print(productTopics)
    if productTopics != "Brand:":
       sql = "UPDATE ProductDetails SET Description = '%s' WHERE ProductUrl like '%%%s%%'" % (productTopics.strip(), x.strip())
       print(sql)
       cur2 = con.cursor()
       cur2.execute(sql)
       con.commit()




