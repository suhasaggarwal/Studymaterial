import csv
import json
import sys
csv.field_size_limit(sys.maxsize)
with open('ArticleTable.csv', 'r',encoding='utf-8') as f:
    reader = csv.reader(f, delimiter=';')
    data_list = list()
    for row in reader:
        data_list.append(row)
data = [dict(zip(data_list[0],row)) for row in data_list]
data.pop(0)
s = json.dumps(data)
print (s)