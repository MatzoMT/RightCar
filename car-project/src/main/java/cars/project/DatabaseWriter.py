import mysql.connector
import json
import requests
from bs4 import BeautifulSoup

def parseYears():
    url = "https://webapi.nhtsa.gov/api/Recalls/vehicle?format=json"
    source_code = requests.get(url)
    plain_text = source_code.text
    site_json = json.loads(plain_text)
    for year in site_json["Results"]:
        if year["ModelYear"] != "9999":
            print(year["ModelYear"])

parseYears()

"""
mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="password",
  database="car_project"
)

mycursor = mydb.cursor()

sql = "INSERT INTO car_information (Year, Make, Model) VALUES (%s, %s, %s)"
val = ("2015","Volkswagen", "Passat")
mycursor.execute(sql, val)

mydb.commit()

print(mycursor.rowcount, "record inserted.")
"""