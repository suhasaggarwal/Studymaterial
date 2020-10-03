import MySQLdb as mdb
import sys

import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

con1 = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware1');

with con1:

    cur = con1.cursor()
    cur.execute("Select * from ProductDetails");
    rows = cur.fetchall()


    for row in rows:
      try:

       print(row[1])
       print(row[2])
       print(row[4])
       print(row[5])
       print(row[9])




      except Exception:
       pass