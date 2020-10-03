#!/usr/bin/python
# -*- coding: utf-8 -*-

import pprint
import datetime
from wikitools import wiki
from wikitools import api
from wikitools import category
import wikitools
from wikitools import page
import re
from wikitools.page import NoPage

people = re.compile(r'.*Category:.*People.*|.*Category:.*people.*')
badlinks = re.compile(r'.* stubs.*|.*Help:.*|.*Talk:.*|.*Wikipedia.*|.*Template:.*|.*Template.*|.*Portal:.*|.*Outline of.*|.*List of.*|.*Outlines of.*|.*Catalog of.*|.*Lists of.*|.*Glossary.*|.*Glossaries.*|.*Index of.*|.*Timeline of.*|.*History of.*|.*Chronology.*|.*Index of.*|.*Overview.*|.*Journals.*|.*Redirects.*|.*Book:.*|.*Help:.*')
needless = re.compile(r' \(.*')
site = wiki.Wiki("http://en.wikipedia.org/w/api.php")

def log(msg):
    print("{} {}".format(str(datetime.datetime.now()), msg))

def WTree(name, CategoryTree):
    wronglinks = re.search(badlinks, name)
    if wronglinks:
        log("wrongslinks matched, exiting")
        return

    try:
        cat = category.Category(site, name)
        page = wikitools.Page(site, title=name, check=True)
        text = page.getWikiText(expandtemplates=False, force=False)

        # if page/category about people, skip out
        if re.search(people, text):
            log("'people' matched, exiting")
            return

        catlist = cat.getAllMembers(namespaces=[14], titleonly=True)
        pagelist = cat.getAllMembers(namespaces=[0], titleonly=True)
        #deleted the cleaning that was being done here
        catpagelist = catlist + pagelist
        repository = []
        for i in catpagelist:
            noparenthesis = needless.sub('', i)
            if len(noparenthesis) > 0:
                try:
                    pagez = wikitools.Page(site, title=noparenthesis, check=True)
                    if pagez is None: continue
                    textz = pagez.getWikiText(expandtemplates=False, force=False)
                    if textz is None: continue

                    personz = re.search(people, textz)
                    if not personz:
                        repository.append(noparenthesis)
                except Exception as ex:
                    if ex is NoPage:
                        log("Page not found! {}".format(noparenthesis))
                    else:
                        log('exception occured! page: {}, msg: {}'.format(noparenthesis, ex.message))
        clean = [s.encode('ascii', 'ignore').strip().replace('Category:','') for s in repository]
        name=name.encode('ascii', 'ignore').strip().replace('Category:','')
        #changed the cleaning here. before it interfered with the results and added to the error
        if name not in CategoryTree.keys():
            CategoryTree[name] = (clean)  #possibly add a key clause

            for ncat in catlist:
                print("{} - about to dive into subcategory '{}'".format(str(datetime.datetime.now()), ncat))
                WTree(ncat, CategoryTree)

    except Exception as ex:
#       if ex is NoPage:
        log('main exception occurred! page not found='+ex.message)
        pprint.pprint(ex)
        pass


if __name__ == "__main__":

    CategoryTree = {}

    cat = 'Machine learning'

    print("{} Started processing category '{}'".format(str(datetime.datetime.now()), cat))

    WTree('Machine learning', CategoryTree)

    print("{} Finished processing category '{}'".format(str(datetime.datetime.now()), cat))

    pprint.pprint(CategoryTree)