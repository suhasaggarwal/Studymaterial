import MySQLdb as mdb
import sys

import newspaper


con = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware');

con1 = mdb.connect('205.147.102.47', 'root',
        'qwerty12@', 'middleware1');

with con:

    cur = con.cursor()
    cur.execute("Select * from Article");
    rows = cur.fetchall()


    for row in rows:
      try:
       print(row)
       cur1 = con1.cursor()
       print("Select Id from Author where AuthorName='"+row[0]+"'")
       cur1.execute("Select Id from Author where AuthorName='"+row[0]+"'")
       rows1 = cur1.fetchall()
       row2= rows1[0]
       id=row2[0]
       print(id)
       sql = "UPDATE Article SET AuthorId = '%s' where Author = '%s'" % (id,row[0])
       print(sql)
       cur2 = con.cursor()
       cur2.execute(sql)
       con.commit()

      except Exception:
       pass