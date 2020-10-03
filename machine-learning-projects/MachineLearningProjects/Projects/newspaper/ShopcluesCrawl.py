import xml.etree.cElementTree as etree
from bs4 import BeautifulSoup
from urllib.request import urlopen,Request
url = "http://www.shopclues.com/sitemap/sitemap_product.xml"
req = Request(url, headers={'User-Agent' : "Magic Browser"})
data = urlopen( req )
print(data.read())
soup = BeautifulSoup(data.read(),"lxml")
linkslist = []
for link in soup.find_all("loc"):
    url1 = link.text
    print(link.text)
    req1 = Request(url1, headers={'User-Agent': "Magic Browser"})
    data1 = urlopen(req1)
    soup1 = BeautifulSoup(data1.read(), "lxml")
    for link1 in soup1.find_all("loc"):
        linkslist.append(link1.text)


print(linkslist)