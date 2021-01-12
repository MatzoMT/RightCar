import mysql.connector
import json
import requests
from bs4 import BeautifulSoup

def parseYears():
    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="password",
        database="car_project"
    )
    
    url = "https://webapi.nhtsa.gov/api/Recalls/vehicle?format=json"
    source_code = requests.get(url)
    plain_text = source_code.text
    site_json = json.loads(plain_text)

    for year in site_json["Results"]:
        if year["ModelYear"] != "9999":
            url_year = "https://webapi.nhtsa.gov/api/Recalls/vehicle/modelyear/" + year["ModelYear"] + "?format=json"
            source_code_year = requests.get(url_year)
            plain_text_year = source_code_year.text
            year_site_json = json.loads(plain_text_year)
            
            for make in year_site_json["Results"]:
                url_make = "https://webapi.nhtsa.gov/api/Recalls/vehicle/modelyear/" + year["ModelYear"] + "/make/" + make["Make"] + "?format=json"
                source_code_make = requests.get(url_make)
                plain_text_make = source_code_make.text
                make_site_json = json.loads(plain_text_make)

                for model in make_site_json["Results"]:
                    print(year["ModelYear"] + " " + make["Make"] + " " + model["Model"])
                #print(year["ModelYear"] + make["Make"])
                

            


    mycursor = mydb.cursor()

"""
    #sql = "INSERT INTO car_information (Year) VALUES ()"
    for year in site_json["Results"]:
        if year["ModelYear"] != "9999":
            sql = "INSERT INTO car_information (Year) VALUES (%s)"
            print(year["ModelYear"])
            val = (year["ModelYear"])
            mycursor.execute(sql, val)
            mydb.commit()
    print(mycursor.rowcount, "record inserted.")
    """




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