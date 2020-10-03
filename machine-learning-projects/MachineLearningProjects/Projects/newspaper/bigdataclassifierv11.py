import collections
from newspaper import Article

import requests
import json
import sys
import csv


#text1 = "https://www.kyarate.com/Eureka-Forbes-Aquaguard-Xtra-Tuff-16-Liter-Water-Purifier/p/1351"
#text1 = "https://www.kyarate.com/Umda-LPS202-17-Inch-Laptop-Sleeve-Bag-Waterproof-Polyester/p/233"
text1="https://www.kyarate.com/Sheffield-Classic-1010-Garment-Steamer/p/339"
import urllib
from bs4 import BeautifulSoup

soup = BeautifulSoup(urllib.urlopen(text1))
name = ""
for span in soup.findAll("div",class_="product-name"):
    name=span.text
    print(name)
i=0
c=""
for span1 in soup.findAll("div",class_="col-xs-12"):
    name1=span1.text
    for a in span1.findAll("li"):
        for b in a.findAll("a"):
            d=b.text.split("Home")
            if i==3:
                c=d
            if i==4:
                c=c+d
            if i==5:
                c=c+d
            i=i+1

               #data = name1.split(">>")
    #print(data)
k=" ".join(c)
k=k.strip()
print(k)
#k='video: when judge anu malik insulted contestant kapil sharma'
r1 = requests.get("http://101.53.130.215:95/SimilarItems/"+k.lower().replace("-",""))

print(r1.content)


