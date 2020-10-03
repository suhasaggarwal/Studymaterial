import requests
from lxml import html
from bs4 import BeautifulSoup

import MySQLdb as mdb
import sys
import newspaper

#fname="UrlListSimulatedTraffic.txt"
#with open(fname) as f:
 #   content = f.readlines()


#con = mdb.connect('205.147.102.47', 'root',
 #       'qwerty12@', 'middleware');
from newspaper import Article


brand= ""
description=""
productImage=""
productId=""
price=""
category=""
seller=""
#content = ['https://www.proptiger.com/chennai/arakkonam/gkp-city-sri-krishna-nagar-664111']
#content = ['https://www.proptiger.com/greater-noida/techzone-4/gaursons-gaur-city-center-1651624']
#content = ['https://www.proptiger.com/greater-noida/techzone-4/rsl-sports-home-662979']
#content = ['https://www.proptiger.com/chennai/gkp-city-sri-krishna-nagar-arakkonam-5236566/940-sqft-plot']
#content = {'https://traveltriangle.com/packages/4nights-5days-andaman-honeymoon?id=1136&travelmonth=7'}
#content = ['https://traveltriangle.com/packages/1night-2days-jim-corbett-family-tour?id=5809&travelmonth=7']
#content = ['https://traveltriangle.com/packages/3nights-4days-kasol-tour?id=5285&travelmonth=7']
#content = ['https://traveltriangle.com/packages/manali_new_year_hilly_escape_2nights_3days?id=5153&pprod=f']
#content = ['https://traveltriangle.com/packages/chakrata_nature_delight_1night_2days?id=5000&pprod=f']
#content = ['https://traveltriangle.com/packages/7nights-8days-jammu-kashmir-tour?id=479&travelmonth=7']
#content = ['https://traveltriangle.com/packages/4nights-5days-bestselling-sikkim-gangtok-darjeeling-tour?id=531&travelmonth=7']
#content = ['https://traveltriangle.com/packages/2nights-3days-goa-sightseeing-tour?id=9302&travelmonth=7']
content = ['https://traveltriangle.com/packages/2nights-3days-goa-vacation-delight?id=4909&travelmonth=7']
#content = ['https://traveltriangle.com/tamil-nadu-tourism/ooty/places-to-visit/pykara-lake']
content = ['https://traveltriangle.com/packages/7nights-8days-vietnam-with-cambodia-trip?id=4031&travelmonth=7']
content=['https://traveltriangle.com/packages/4nights-5days-goa-family-tour?id=792&travelmonth=7']
content=['https://traveltriangle.com/packages/2nights-3days-fascinating-auli-tour?id=5275&travelmonth=7']
content=['https://traveltriangle.com/packages/2nights-3days-goa-sightseeing-tour?id=9302&travelmonth=7']
#url='https://traveltriangle.com/packages/2nights-3days-goa-sightseeing-tour?id=9302&travelmonth=7'
#content=['https://traveltriangle.com/packages/1night-2days-haridwar-voyage?id=6937&travelmonth=7']
#url='https://traveltriangle.com/packages/1night-2days-haridwar-voyage?id=6937&travelmonth=7'
content = ['https://traveltriangle.com/packages/3nights-4days-kathmandu-delight?id=4407&travelmonth=7']
url='https://traveltriangle.com/packages/3nights-4days-kathmandu-delight?id=4407&travelmonth=7'
#content = ['https://traveltriangle.com/packages/2n-3d-udaipur-tour']
#url = 'https://traveltriangle.com/packages/2n-3d-udaipur-tour'
list1=[]
list3=[]
lista=[]
location=""
k=0
data1=""
data2=""
data3=""
tags= set()
category1= ""
author=""
d={}

with open('ixigo.csv') as f:
    content = f.read().splitlines()


for x in content:
    r = requests.get(x.strip())
    soup = BeautifulSoup(r.content, "html.parser")

    list1 = []
    list3 = []
    lista = []
    location = ""
    k = 0
    data1 = ""
    data2 = ""
    data3 = ""
    tags = set()
    category = ""
    author = ""
    d = {}
    maincategory = ""
    image1=""
    image=""
    a=""
    b=""
    c=""
    titlev1=""
    section=""
    price=""
    for span in soup.findAll("ol",class_="breadcrumbs"):
        for c in span.findAll(itemprop="name"):
            category =category +"/"+ c.text.strip()
            #print(category)
          # for d1 in span.findAll("li"):
          #  category1=category1+d1.text.replace(" ","").replace(">","/")
            #print(d1.text)

    #for a in soup.findAll("div",class_="type u-ib"):
     #   tags.add(a.text)

#    for b in soup.findAll("div",class_="c-price-display u-text-ellipsis"):
 #       for c in b.findAll('span'):
  #          tags.add(c.text)

       # for a in span.findAll('ul'):
            #print("Description:"+a.text.strip())
        #external_span = soup.find('li')

    for b2 in soup.findAll("div", class_="c-price-display u-text-ellipsis"):
        for c2 in b2.findAll('span'):
              price=c2.text

    for k in soup.findAll("div", class_="hotel-address"):
        for g in k.findAll("h4"):
            section=g.text

     # for a in span.findAll('ul'):
     # print("Description:"+a.text.strip())

    for a1 in soup.findAll("div", class_="name-rating"):
        for c1 in a1.findAll(itemprop="name"):
            author=c1.text





   # print("Section:"+section)
    #print(tags)
    #tags1 = ";".join(tags)
    #print(tags1)
    print(category)
    print(author)
    print(section)
    print(price)
    from urllib.parse import urlparse

    parsedurl = urlparse(x.strip())
    import datetime

    additiontime = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    # from urllib.parse import urlparse
    # parsedurl = urlparse(response.url)

    import hashlib

    pbHash = hashlib.sha1(str(x.strip()).encode('utf-8')).hexdigest()

    d["Entity6"] = "Price:"+price
    d["Entity4"] = author
    d["Entity7"] = ""
    d["Entity5"] = ""
    d["Entity3"] = ""
    d["Entity8"] = category
    categorydata = category.split("/")

    if len(categorydata) > 2:
        maincategory = categorydata[1] + "/" + categorydata[2] + "/" + categorydata[3]
    if category == "":
        maincategory = ""

    d["Entity10"] = maincategory

    d["Entity11"] = "gaac82b3ceae411d8bc38bced7a1fff559"
    d["Entity1"] = x.strip()
    d["Entity12"] = section
    import string

    d["Entity9"] = string.capwords(parsedurl.path.replace("-", " "))
    d["Entity2"] = parsedurl.path
    d["Entity13"] = additiontime
    d["EntityId"] = pbHash

    import json
    # with open('data30.json', 'a') as fp:
    import codecs

    fp = codecs.open('ixigodatav27.json', mode='a', encoding='utf-8')
    if len(d) != 0:
        line1 = '{"index":{}}' + "\n"
        line = line1 + json.dumps(d) + "\n"
        fp.write(line)
        fp.close


        #brand=a.text.strip()
        #for a in span.findAll("img"):
            #print("Product Image:"+a['src'])
            #productImage=a['src']
        #for a in span.findAll(class_="pID"):
            #print("Product Id:"+a.text.strip())
            #productId=a.text.strip()
        #for a in span.findAll(class_="f_price"):
            #print("Price :"+a.text.strip())
            #price=a.text.strip()
#for div in soup.findAll("div",class_="pdp_info wrapper"):
    #for div1 in div.findAll("div",class_="breadcrums"):
    #for b in soup.findAll("span",attrs={"itemprop":"title"}):
        #print("Category:\n"+b.text.strip())
        #category=category+">"+b.text

    #for span in soup.findAll("div",itemprop="seller"):
        #for seller in span.findAll(itemprop="name"):
            #print("Seller:"+seller.text.strip())
            #seller=seller.text.strip()

    #category = category.strip()
    #print(brand)
    #print(seller)
    #print(productId)
    #print(productImage)
    #print(category)
    #print(price)

    #productId = productId.split(":")[1].strip()
    #description=description.replace("\n",",")
    #print(description + "\n")
    #productTopicsMeta = description.split(",")
    #productTopics="Brand:"+brand
    #productTopics = productTopics.strip()
    #i=0
    #for k in productTopicsMeta:

        #if ":" in k:
         #   k1 = k
          #  if i==0:
           #    if brand!='':
            #      productTopics = productTopics + ","+k1.strip()
             #  else:
              #     productTopics=k1.strip()

            #else:
             #   productTopics = productTopics + "," + k1.strip()



    #    i=i+1

#    print(productTopics)
 #   if productTopics != "Brand:":
  #     sql = "UPDATE Article SET Tags = '%s' WHERE ArticleUrl like '%%%s%%'" % (productTopics.strip(), x.strip())
   #    print(sql)
    #   cur2 = con.cursor()
     #  cur2.execute(sql)
      # con.commit()




