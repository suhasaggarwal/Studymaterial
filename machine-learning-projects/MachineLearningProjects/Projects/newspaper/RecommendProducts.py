import collections
from newspaper import Article

import requests
import json
import sys
import csv


#text1 = "https://www.kyarate.com/Eureka-Forbes-Aquaguard-Xtra-Tuff-16-Liter-Water-Purifier/p/1351"
#text1 = "https://www.kyarate.com/Umda-LPS202-17-Inch-Laptop-Sleeve-Bag-Waterproof-Polyester/p/233"
import urllib
from bs4 import BeautifulSoup
#k='havells leganza 4b 1200mm ceiling fan (bronze gold) , fans'
#k='venus handy washing machine , washing machines'
k='pari designerr yellow georgette self design saree with blouse: buy pari designerr yellow georgette self design saree with blouse online at best prices from shopclues.com'
r1 = requests.get("http://101.53.130.215:96/SimilarProducts/"+k.lower().replace("-",""))

print(r1.content)


