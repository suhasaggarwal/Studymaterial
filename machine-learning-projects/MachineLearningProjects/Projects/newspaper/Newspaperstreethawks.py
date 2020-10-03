from newspaper import Article
#import MySQLdb as mdb
import sys
from bs4 import BeautifulSoup
import newspaper
import requests

with open('streethawksurllist.txt') as f:
    lines = f.read().splitlines()
i=0
for line in lines:
    try:
       line = line.strip() #or some other preprocessing
       url=line
       #url=url.replace("https","http")
    #url = "https://streethawks.in/b/4/New-Concept-Cruiser-bike:-TVS-Zeppelin"
#url = "http://www.siliconindia.com/online_courses/dotnet_certification-cid-13.html"
    #from urllib import parse
       #print(url)
       file = open('articlelistv5.txt', 'a')
       r = requests.get(url.strip(),verify=False)
       soup = BeautifulSoup(r.content, "html.parser")
       title = soup.find("meta", property="og:title")
       print(title["content"])
       if title["content"] is not None:
           i=i+1
       image = soup.find("meta", property="og:image")
       print(image["content"])
       if image["content"] is not None:
           i=i+1
       if i==2:

          file.write("Title:" + title["content"] + "\n")
          file.write("Url:" + url + "\n")
          file.write("Image:"+image["content"] + "\n")
       i=0
       # imageurl = results[0]  # first result in the list, hopefully only one match is found, otherwise, you'll have to do some more work
      # imageurlv1 = imageurl.text
      # print(imageurlv1)
    except Exception:
          pass
    #import urlparse
    #parsed_url = parse.urlparse(url)
    #parsed_url = urlparse.urlparse(url)
    #path=parsed_url.path
    #parsed_url= urlparse(url)
    #path=path.split("/")
    #data=path[len(path)-1]
    #from urllib.parse import unquote
    #data = unquote(data)
    #import urllib
    #data = urllib.unquote(data).decode('utf8')
    #data=data.replace("-"," ").replace(","," ").replace(":"," ").replace("'"," ").replace("  ","")
    #data=data.lower()
    #if len(data)>6:
     #  print(data)
   # url=url.replace("https","http")
    #article = Article(url)
    #article.download()
    #  print(article.html)
    #article.parse()
   # a = article.authors
   # a = ",".join(a)
   # print(a)

    #c = article.top_img
    #print(c)