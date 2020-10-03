import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper

fname="urllistv1.txt"
with open(fname) as f:
    content = f.readlines()


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware1');



brand= ""
description=""
productImage=""
productId=""
price=""
category=""
seller=""

#content = "http://www.shopclues.com/moto-g-2nd-gen-xt1068-16gb-certified-pre-owned-acceptable-condition-3-months-seller-warranty-126825895.html";

#content = ['http://www.shopclues.com/kevin-80-cm-32-inches-hd-ready-led-tv-with-bluetooth-120632646.html']

content = ['http://www.shopclues.com/chilee-life-comfortable-cotton-hosiery-medium-coverage-padded-bras-pack-of-5.html']



specifications = ""

i=1

for x in content:
    print(x)
    r = requests.get(x.strip())
    soup = BeautifulSoup(r.content, "html.parser")

    for span in soup.findAll("div",class_="pd_tabs active"):
        #for a in span.findAll(class_="grpDesc"):
            #print("Description:"+a.text.strip())
        for b in span.findAll("span"):
            if i%2==0:
               specifications = specifications.strip()+b.text.strip()+","
            else:
                specifications = specifications.strip()+b.text.strip()
            i=i+1
            if "Warranty," in specifications:
                print(specifications)

                sql = "UPDATE ProductDetails SET Description = '%s' WHERE ProductUrl like '%%%s%%'" % (
                specifications.strip(), x.strip())
                print(sql)
                #cur2 = con.cursor()
                #cur2.execute(sql)
                #con.commit()
                specifications = ""
                break
        #for c in span.findAll(_class="stext"):
            #print("Set2:"+c.text.strip())
            #specifications = specifications + b.text.strip()+":"+c.text.strip()+"\n"

print(specifications)
