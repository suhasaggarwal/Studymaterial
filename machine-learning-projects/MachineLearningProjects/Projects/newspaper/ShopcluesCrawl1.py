import xml.etree.cElementTree as etree
from bs4 import BeautifulSoup
from urllib.request import urlopen,Request
url = "http://www.shopclues.com/sitemap/sitemap_product.xml"
hdr = {'User-Agent': 'Mozilla/5.0'}
req = Request(url,headers=hdr)
page = urlopen(req)
soup = BeautifulSoup(page.read(),"lxml")
linkslist = []
for link in soup.find_all("loc"):
  try:
    url1 = link.text
    print(link.text)
    hdr = {'User-Agent': 'Mozilla/5.0'}
    req1 = Request(url1, headers=hdr)
    data1 = urlopen(req1)
    soup1 = BeautifulSoup(data1.read(), "lxml")
    for link1 in soup1.find_all("loc"):
        linkslist.append(link1.text)
  except Exception:
      pass

thefile = open('shopcluesurls.txt', 'w')
for item in linkslist:
  print>>thefile, item
