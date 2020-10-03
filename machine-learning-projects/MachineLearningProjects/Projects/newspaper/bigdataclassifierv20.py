
import urllib.request
import requests
import json
import sys
import csv
import urllib



text2 = "https://www.wittyfeed.com/story/64292/which-bollywood-actor-will-play-you-in-a-film"
text2 =text2.replace(" ","%20")
print(text2)
r1 = requests.get("http://101.53.130.215:83/getTextCategoryv1?url="+text2)

jData = json.loads(r1.content)
print(jData)

