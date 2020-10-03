import requests
from lxml import html
from bs4 import BeautifulSoup
import re
#import MySQLdb as mdb
import sys
import newspaper
from newspaper import Article
#fname="UrlListSimulatedTraffic.txt"
#with open(fname) as f:
 #   content = f.readlines()


#con = mdb.connect('205.147.102.47', 'root',
 #       'qwerty12@', 'middleware');

import scrapy
from bs4 import BeautifulSoup
from langdetect import detect
import requests


class proptigerSpider(scrapy.Spider):
   name = 'proptigerv2'

   with open('/root/proptiger.txt') as f:
       start_urls = [url.strip() for url in f.readlines()]

   def parse(self, response):
    soup = BeautifulSoup(response.text, "html.parser")
    d= {}
    data={}
    global k
    publisher= ""
    author=""
    section=""
    title=""
    publishedTime=""
    i=1
    additiontime=""
    tag=""
    scraped_info={}
    a=""
    b=""
    c=""
    k=""
    e=""
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
#content = ['https://traveltriangle.com/packages/2nights-3days-goa-vacation-delight?id=4909&travelmonth=7']
#content = ['https://traveltriangle.com/tamil-nadu-tourism/ooty/places-to-visit/pykara-lake']
#url = 'https://traveltriangle.com/packages/7nights-8days-vietnam-with-cambodia-trip?id=4031&travelmonth=7'
#content =  ['https://www.lenskart.com/black-sky-blue-full-rim-rectangle-medium-size-51-vincent-chase-air-vc-0318-uo20-eyeglasses.html']
#content = ['https://www.lenskart.com/tommy-hilfiger-th9018-c3-size-50-sunglasses.html']
#content = ['https://www.lenskart.com/silver-rimless-rectangle-medium-size-53-john-jacobs-shibuya-crossing-jj-0039-c24-eyeglasses.html']
#content = ['https://www.lenskart.com/black-sky-blue-full-rim-rectangle-medium-size-51-vincent-chase-air-vc-0318-uo20-eyeglasses.html']
#content= ['https://www.lenskart.com/vogue-vo2787-2278-size-51women-s-eyeglasses.html']
#content = ['https://www.lenskart.com/blue-gunmetal-matte-blue-full-rim-wayfarer-medium-size-50-vincent-chase-vc-air-007-vc-e10115-c3-eyeglasses.html']
    list1=[]
    list3=[]
    lista=[]
    location=""
    k=0
    data1=""
    data2=""
    data3=""
    url = ""
    category1=""
    variable1=""
    variable2 =""
    builder=""
    image=""
    titledata=""
    parsedurl=""
    additiontime=""
    pbHash=""
    category1=""
    category2=""
    k=0
    maincategory=""
#    soup = BeautifulSoup(, "html.parser")


    for span in soup.findAll("div",class_="js-breadcrumb-seo breadcrumb-seo ta-l black-bc"):
       # for a in span.findAll('ul'):
            #print("Description:"+a.text.strip())
        #external_span = soup.find('li')

        for d in span('ul'):
            d.decompose()
        for c in span.findAll(itemprop="name"):
           if k==0:
              category1 = c.text.strip() 
           if k==1:
              category2 = c.text.strip()
           category=category+"/"+c.text.strip()
            #list2.append(category)


    for span in soup.findAll("div",class_="title-builder"):
        for b in span.findAll('a'):
            builder=b.text

    for span in soup.findAll("div",class_="max1140 pos-rel ht100 d-flex js-gallery-open"):
        image=span.attrs['data-absolutepath']


    for span in soup.findAll("span",class_="va-middle proj-address"):
        for b in span.findAll('a'):
            location=b.text.replace("(show on map)","")
            break;

    for r in soup.findAll("div", class_="spec-value f16"):
        if k==0:
           data1=r.text

        if k==1:
           data2=r.text
        k=k+1

    for r1 in soup.findAll("div", class_="general-range"):
        builderprice=r1.text
        builderprice1=builderprice.strip()
        #print(builderprice1)
        data3=builderprice1
        break;#for a in span.findAll(itemprop="brand"):
            #print("Brand:"+a.text.strip())
 
#    title = soup.find("meta", property="og:title")
#    titledata=title["content"]

   


    from urllib.parse import urlparse
    parsedurl = urlparse(response.url)
    import datetime
    additiontime= datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
   # from urllib.parse import urlparse
   # parsedurl = urlparse(response.url)

    import hashlib
    pbHash=hashlib.sha1(str(response.url).encode('utf-8')).hexdigest()






    d["Entity6"]=data1+";"+data2+";"+data3
    d["Entity4"]=builder
    d["Entity7"]=image
    d["Entity5"]=""
    d["Entity3"]=""
    d["Entity8"]=category
    categorydata = category.split("/")

    maincategory = category1 + "/" + category2
    if category1 == "":
       maincategory = ""
 
       

    d["Entity10"]=maincategory

    d["Entity11"]="gaac82b3ceae411d8bc38bced7a1fff555"
    d["Entity1"]=response.url
    d["Entity12"]=location
    import string
    d["Entity9"]=string.capwords(parsedurl.path.replace("-"," "))
    d["Entity2"]=parsedurl.path
    d["Entity13"]=additiontime
    d["EntityId"]=pbHash
 #   yield d.values()

    import json
    #with open('data30.json', 'a') as fp:
    import codecs
    fp= codecs.open('proptigerdata.json', mode = 'a', encoding = 'utf-8')
    if len(d) != 0:
       line1 = '{"index":{}}'+"\n"
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





