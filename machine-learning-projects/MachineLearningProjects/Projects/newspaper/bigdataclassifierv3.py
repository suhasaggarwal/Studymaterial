
import urllib.request
import requests
import json
import sys
import csv
import urllib



text2 = "parineeti shilpa kriti sanon rule filmfare 2017 red carpet"
text2 =text2.replace(" ","%20")
print(text2)
r1 = requests.get("http://101.53.130.215:82/IAB1/"+text2)

jData = json.loads(r1.content)
print(jData)

