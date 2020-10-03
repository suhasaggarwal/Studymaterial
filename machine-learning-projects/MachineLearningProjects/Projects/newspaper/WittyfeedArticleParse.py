import requests

from bs4 import BeautifulSoup

#r = requests.get("https://www.wittyfeed.com/videos/65056/dharmesh-sir-first-audition-video-of-dance-india-dance")
#r = requests.get("https://www.wittyfeed.com/story/65031/unexplored-hill-stations-of-india-to-visit-this-summers")
#r = requests.get("https://www.wittyfeed.com/story/64872/top-batsmen-from-delhi-daredevils-in-2018")
#r = requests.get("https://www.wittyfeed.com/story/65068/lodhi-run-happened-in-new-delhi-with-a-purpose-to-motivate-people-to-stay-fit")

r=requests.get("https://www.foodmate.me/food-combinations-you-should-avoid-67197")
soup = BeautifulSoup(r.content, "html.parser")

'''
publisher = soup.find("meta",  property="article:publisher")
print(publisher['content'])
author = soup.find("meta",  property="article:author")
print(author['content'])
section = soup.find("meta",  property="article:section")
print(section['content'])
title = soup.find("meta",  property="og:title")
print(title['content'])
publishedTime = soup.find("meta",  property="article:published_time")
print(publishedTime['content'])

tag = soup.findAll("meta",  property="article:tag")
for tagdata in tag:
    print(tagdata['content'])
'''
import re
for data in soup.find_all("img",class_="responsive-img"):
    print(data)
#print(datav1)
