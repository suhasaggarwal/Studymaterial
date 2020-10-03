import collections
import requests
import json
import sys
import csv


#text1 = "https://www.kyarate.com/Eureka-Forbes-Aquaguard-Xtra-Tuff-16-Liter-Water-Purifier/p/1351"
#text1 = "https://www.kyarate.com/Umda-LPS202-17-Inch-Laptop-Sleeve-Bag-Waterproof-Polyester/p/233"
import urllib
from bs4 import BeautifulSoup
k='movie-buff name-all-the-movies-featured-in-this-quiz movie-quiz'
r1 = requests.get("http://101.53.130.215:98/SimilarArticlesWittyfeed/"+k.lower().replace("-",""))

print(r1.content)


