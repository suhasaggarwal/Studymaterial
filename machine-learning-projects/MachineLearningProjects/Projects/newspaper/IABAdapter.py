import tagme
import wikipedia
import mediawikiapi
import wikidata
import collections
from newspaper import Article
import urllib.request
import requests
import json
import sys
import csv




with open('FacebookInterestSegment.csv', encoding="utf8") as csv_file:
    for row in csv.reader(csv_file, delimiter='\t'):
      #  print(row[0])
        text1 =row[0].split(";")
        text = text1[0].replace("/"," ").replace('"',"")
        text=text.lower()
        print(text)

        r1 = requests.get("http://101.53.130.215:82/IAB1/"+text)
        print(r1.content)



