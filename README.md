# RightCar
RightCar is a planned JavaFX GUI application that analyzes the reliability data of a specific passenger car in the United States. 

# Methodology
RightCar works by using Python and its BeautifulSoup library to parse JSON data provided by the National Highway Traffic Safety Administration's (NHTSA) API and write
information for each year, make, and model into a SQL database. A class written in Java connects to this database in order to determine how to fill out the GUI's
information, such as the comboboxes. Java is also used in this application to webscrape data to find the sales number of a selected model
being sold in the Untied States. This information, complained with the number of vehicle complaints provided by NHTSA, is used to determine a score to calculate the 
selected model's reliability.

# Other Information
This program is only designed to work for passenger cars, which are more likely to have substantial complaints submitted to NHTSA and sales information available when compared to other vehicles such as recreational vehicles or motorcycles. This includes automakers that are recognized as selling passenger cars by autotrader.com.
