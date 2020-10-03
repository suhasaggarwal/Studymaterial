import requests
import datetime
import requests
from langdetect import detect
import sys
import csv
import urllib.parse

f = open("newbigfilev3.txt")
lines = f.readlines()

for url in lines:
    try:
        r1 = requests.get("http://101.53.130.215:5001/getTextAnalytics?url=" + url)
        import json

        jData = r1.json()
        # print(jData)
        for x in jData:
            if x['rho'] < 0.1:
                jData.remove(x)
        y = set()
        k = ""
        for x in jData:
            print(x['Title'])
            y.add(x['Title'])

        k = ';'.join(str(s) for s in y)
        with open("topicdata1.txt", "a") as myfile:
            myfile.write(url.strip()+"@"+k.strip()+"\n")

    except:
        pass