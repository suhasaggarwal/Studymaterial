from newspaper import Article
import MySQLdb as mdb
import sys
import string

import newspaper
from bs4 import BeautifulSoup
import requests
from sematch.semantic.graph import SimGraph
from sematch.semantic.similarity import WordNetSimilarity
from sematch.nlp import Extraction, word_process
from sematch.semantic.sparql import EntityFeatures
from collections import Counter


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

with con:

    cur = con.cursor()
    cur.execute("Select MAX(Id) from Article");
    rows = cur.fetchall()
    row = rows[0]
    print("SELECT * FROM Article Where Id > "+str(row[0]))
    id='silicon'
    cur.execute("SELECT * FROM Article Where ArticleUrl Like '%%%s%%'" % (id))
    cur1 = con.cursor()
    rows = cur.fetchall()

    for row in rows:
      try:
       #print(row)
       url = row[1]
    #   url = "https://www.socialpost.news/national/are-most-journalists-in-india-becoming-paid-activists-and-ruining-the-fourth-estate/"
       article = Article(url)
       article.download()
     #  print(article.html)
       article.parse()
       #a=article.authors
       #author=a[0]
       #print(author)
       article.nlp()
       e=article.keywords
       words = ' '.join(e)
       words = Extraction().extract_words(words)
       words = word_process(words)
       #print(words)
       wns = WordNetSimilarity()
       word_graph = SimGraph(words, wns.word_similarity)
       word_scores = word_graph.page_rank()
       words, scores = zip(*Counter(word_scores).most_common(5))



       print(words)
       words=','.join(words)






       #sql="UPDATE Article SET Tags= '%s', MetaKeywords= '%s',Author= '%s' WHERE ArticleUrl = '%s'" % (tags,e,url)
       sql = "UPDATE Article SET Tags= '%s' WHERE ArticleUrl = '%s'" % (words, url)

       print(sql)
       cur1.execute(sql)
       con.commit()

      except Exception:
          pass