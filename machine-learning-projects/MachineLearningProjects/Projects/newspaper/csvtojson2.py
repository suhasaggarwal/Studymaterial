import csv, json
import sys
csv.field_size_limit(sys.maxsize)
def dump_to_json():
    csv_result = []
    with open('ArticleTable.csv', "rt", encoding='utf8') as csvfile:
        for row in csv.DictReader(csvfile, delimiter=';', quotechar='"'):
            csv_result.append({'EntityId': row['EntityId'], 'Entity1': row['Entity1'],
                               'Entity2': row['Entity2'], 'Entity3': row['Entity3'],'Entity4': row['Entity4'], 'Entity5': row['Entity5'],'Entity6': row['Entity6'], 'Entity7': row['Entity7'],'Entity8': row['Entity8'], 'Entity9': row['Entity9'],'Entity10': row['Entity10'], 'Entity11': row['Entity11'],'Entity12': row['Entity12'], 'Entity13': row['Entity13'] })

    json_feed = csv_result

    with open('sample.json', 'w') as outfile:
        json.dump(json_feed, outfile)

dump_to_json()