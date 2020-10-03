#!/usr/bin/env python
# coding: utf-8

# ## Functions for getting article information

# We want all articles starting in the beginning of June 2015 until now, and we will gather them in two week periods

# In[1]:


all_dates = []
for month in ["06", "07", "08", "09", "10", "11", "12"]:
    for date_pair in [("01", "15"), ("16", "31")]:
        date_range = ("2015" + month + date_pair[0], "2015" + month + date_pair[1])
        all_dates.append(date_range)

# In[2]:


for month in ["01", "02", "03", "04", "05", "06", "07", "08"]:
    for date_pair in [("01", "15"), ("16", "31")]:
        date_range = ("2016" + month + date_pair[0], "2016" + month + date_pair[1])
        all_dates.append(date_range)


# In[3]:


all_dates


# In[4]:


def get_articles(search_term, dates):
    url = "https://api.nytimes.com/svc/search/v2/articlesearch.json"
    api_infos = []
    # get all urls
    for start_date, end_date in dates: 
        for page in range(101):
            queries = {
          'api-key': XXX,
          'q': search_term,
          'begin_date': start_date, 
          'end_date': end_date, 
          'page' : page
            }
            req_t = requests.get(url, params = queries)
            adict = json.loads(req_t.text)
            doclist = adict['response']['docs']
            for j in range(len(doclist)):
                api_info = ([doclist[j]["web_url"], doclist[j]["headline"],
                      doclist[j]["section_name"],
                      doclist[j]["word_count"], doclist[j]["type_of_material"], 
                      doclist[j]["pub_date"]])
                api_infos.append(api_info)
    # transform data into dataset
    api_dataset = pd.DataFrame(api_infos, columns=['Url','Headline', 
                                           'Section_name', 'Word_Count', 
                                           'Type', 'Pub_Date'])
    return api_dataset


# In[5]:


def get_text(url_list):
    articles = []
    for address in url_list: 
        try:
            article = Article(address)
            article.download()
            article.parse()
            article.nlp()
            article_info = ([article.publish_date, article.authors,
                      article.summary, article.keywords, article.text,
                     article.url])
        except:
            pass
        
        articles.append(article_info)
        
    newspaper_dataset = pd.DataFrame(articles, columns=['Date','Authors', 'Summary', 'Keywords', 'Text', "Url"])
    newspaper_dataset["Text_Decode"] = newspaper_dataset["Text"].apply(lambda x: x.encode('utf-8'))
    newspaper_dataset["Text_Clean"] = newspaper_dataset["Text_Decode"].apply(lambda x: x.replace("\n", " "))
    newspaper_dataset = newspaper_dataset.drop('Text_Decode', 1)
    return newspaper_dataset


# ## Gather Hillary and Trump Articles

# In[ ]:


HC_articles = get_articles("Hillary Clinton", all_dates)


# In[ ]:


Hillary_text = get_text(HC_articles["Url"])


# In[ ]:


Trump_articles = get_articles("Donald Trump", all_dates)


# In[ ]:


Total_Trump = get_text(Trump_articles["Url"])


# ## Clean, Merge, and Pickle Datasets

# #### Combine text and api datasets, get rid of duplicates and bad links, and add a column of Text without the "\n"

# In[ ]:


Trump_merged = Trump_articles.merge(Total_Trump, on = "Url", how = "inner")
good_links = Trump_merged[Trump_merged["Text"] != "NYTimes.com no longer supports Internet Explorer 9 or earlier. Please upgrade your browser."]
Trump_no_duplicates = good_links.drop_duplicates("Text")
Trump_no_duplicates["Text_Decode"] = Trump_no_duplicates["Text"].apply(lambda x: x.encode('utf-8'))
Trump_no_duplicates["Text_Clean"] = Trump_no_duplicates["Text_Decode"].apply(lambda x: x.replace("\n", " "))


# In[ ]:


Hillary_merged = all_newspaper_articles.merge(HC_articles, on = "Url", how = "inner")
Hillary_no_duplicates = Hillary_merged.drop_duplicates("Text")
Hillary_no_duplicates = Hillary_no_duplicates[Hillary_no_duplicates["Text"] != "NYTimes.com no longer supports Internet Explorer 9 or earlier. Please upgrade your browser."]
final_Hillary = Hillary_no_duplicates
final_Hillary["Text_Decode"] = final_Hillary["Text"].apply(lambda x: x.encode('utf-8'))
final_Hillary["Text_Clean"] = final_Hillary["Text_Decode"].apply(lambda x: x.replace("\n", " "))


# In[ ]:


final_Hillary["Trump_subject"] = 0
Trump_no_duplicates["Trump_subject"] = 1
Trump_Clinton_dataset = final_Hillary.append(Trump_no_duplicates)


# In[ ]:


with open('Trump_Clinton_dataset.pkl', 'w') as picklefile:
    pickle.dump(Trump_Clinton_dataset, picklefile)

