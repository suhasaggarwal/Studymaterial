import csv
import json

csvfile = open('ArticleTable.csv', 'r', encoding="utf8")
jsonfile = open('ArticleTablev10.json', 'w')

fieldnames = ("EntityId","Entity1","Entity2","Entity3","Entity4","Entity5","Entity6","Entity7","Entity8","Entity9","Entity10","Entity11","Entity12")
reader = csv.DictReader( csvfile, delimiter=';')
for row in reader:
    json.dump(row, jsonfile)
    jsonfile.write('\n')