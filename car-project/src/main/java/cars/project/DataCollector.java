package cars.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class DataCollector {

    /*
    public void writeToCsv() {
        try {
            int[] modelYears = WebParser.getModelYears();
            FileWriter csvWriter = new FileWriter("years.csv", false);
            csvWriter.write("YEAR,MAKE\n");
            for (int i = 0; i < modelYears.length; i++) {
                String[] makes = WebParser.getMakes(modelYears[i]);
                if (!(String.valueOf(modelYears[i]).equals("9999"))) {
                    csvWriter.write(String.valueOf(modelYears[i]) + ",");
                    for (int j = 0; j < makes.length; j++) {
                        csvWriter.write(makes[j]);
                        if (j != makes.length - 1) {
                            csvWriter.write(",");
                        }
                    }
                    csvWriter.write("\n");
                }
            } // for
            System.out.println("completed");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ioe) {
            System.out.println("IOException occurred");
        }
    }
*/
    public static void main(String[] args) {
        try {
            /* 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:3306;" +  
   "databaseName=car_project;user=root;password=password;";  
            Connection con = DriverManager.getConnection(connectionUrl);
            **/
            Connection conn = null;
            Properties connectionProps = new Properties();
            connectionProps.put("user", "root");
            connectionProps.put("password", "password");
            conn = DriverManager.getConnection(
                   "mariaDB:mysql://localhost:3306/",
                   connectionProps);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage()); 
        } 
        //  DataCollector dc = new DataCollector();
      //  dc.writeToCsv();
    }
}
