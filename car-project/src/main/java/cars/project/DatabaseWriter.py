import mysql.connector

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