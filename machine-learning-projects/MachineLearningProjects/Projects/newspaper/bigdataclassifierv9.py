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
from flask import Flask, request
from flask_restful import Resource, Api
import numpy as np
import gensim
from json import dumps

app = Flask(__name__)
api = Api(app)





class computeSummary(Resource):
    def get(self):
        #Connect to databse

        import urllib
        from flask import request
        args = request.args
        print(args)  # For debugging
        text = args['url']
        import urllib.parse
        text=urllib.parse.unquote(text)
        text1 = text
        article = Article(text1)
        article.download()
        article.parse()
        # article.nlp()
        article.nlp()
        data=article.summary


        return data


api.add_resource(computeSummary, '/getArticleSummary',endpoint='getArticleSummary')

if __name__ == '__main__':
     app.run(host='0.0.0.0',port=90,threaded=True)



