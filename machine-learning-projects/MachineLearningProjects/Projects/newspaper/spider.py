import scrapy

class ShopcluesSpider(scrapy.Spider):
   name = 'shopclues'
   with open('shopcluesurl1.txt') as f:
          start_urls = [url.strip() for url in f.readlines()]





   def parse(self, response):
      soup = BeautifulSoup(response, "html.parser")
     = {}
    data={}
    obal k

    for span in soup.findAll("div",class_="prd_mid_info"):

        for a in span.findAll(itemprop="brand"):
            #print("Brand:"+a.text.strip())
            brand=a.text.strip()
        for a in span.findAll("img"):
            #print("Product Image:"+a['src'])
            productImage=a['src']
        for a in span.findAll(class_="pID"):
            #print("Product Id:"+a.text.strip())
            productId=a.text.strip()
        for a in span.findAll(class_="f_price"):
            #print("Price :"+a.text.strip())
            price=a.text.strip()
#for div in soup.findAll("div",class_="pdp_info wrapper"):
    #for div1 in div.findAll("div",class_="breadcrums"):
    for b in soup.findAll("span",attrs={"itemprop":"title"}):
        #print("Category:\n"+b.text.strip())
        category=category+">"+b.text

    for span in soup.findAll("div",itemprop="seller"):
        for seller in span.findAll(itemprop="name"):
            #print("Seller:"+seller.text.strip())
            seller=seller.text.strip()

    for span in soup.findAll("div",class_="pd_tabs active"):
        #for a in span.findAll(class_="grpDesc"):
            #print("Description:"+a.text.strip())
        for b in span.findAll("span"):
            if i%2==0:
               specifications = specifications.strip()+b.text.strip()+","
