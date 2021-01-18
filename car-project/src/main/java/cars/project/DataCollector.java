package cars.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DataCollector {

    /*
     * public void writeToCsv() { try { int[] modelYears =
     * WebParser.getModelYears(); FileWriter csvWriter = new FileWriter("years.csv",
     * false); csvWriter.write("YEAR,MAKE\n"); for (int i = 0; i <
     * modelYears.length; i++) { String[] makes = WebParser.getMakes(modelYears[i]);
     * if (!(String.valueOf(modelYears[i]).equals("9999"))) {
     * csvWriter.write(String.valueOf(modelYears[i]) + ","); for (int j = 0; j <
     * makes.length; j++) { csvWriter.write(makes[j]); if (j != makes.length - 1) {
     * csvWriter.write(","); } } csvWriter.write("\n"); } } // for
     * System.out.println("completed"); csvWriter.flush(); csvWriter.close(); }
     * catch (IOException ioe) { System.out.println("IOException occurred"); } }
     */
    public static void main(String[] args) {
        getModelYears();

    }

    public static ArrayList<Integer> getModelYears() {
        ArrayList<Integer> yearsList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/car_project";
        String username = "root";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String SQL = "select distinct Year from car_project.car_information;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                int year = rs.getInt("Year");
                yearsList.add(year);
            }
           
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        for (int i = 0; i < yearsList.size(); i++) {
            System.out.println(yearsList.get(i).toString());
        }
        return yearsList;
    } // getModelYears

    /**
     * Returns the car makes registered with the selected model year.
     */
    public static ArrayList<String> getMakes(int modelYear) {
        ArrayList<String> makesList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/car_project";
        String username = "root";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String SQL = "select distinct Make from car_project.car_information where year='" + modelYear + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                String make = rs.getString("Make");
                makesList.add(make);
            }
           
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        for (int i = 0; i < makesList.size(); i++) {
            System.out.println(makesList.get(i).toString());
        }
        return makesList;
    } // getMakes

    public static ArrayList<String> getModels(int modelYear, String make) {
        ArrayList<String> modelsList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/car_project";
        String username = "root";
        String password = "password";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String SQL = "select distinct Model from car_project.car_information where year='" + modelYear + "' and make='" + make + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                String model = rs.getString("Model");
                modelsList.add(model);
            }
           
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        for (int i = 0; i < modelsList.size(); i++) {
            System.out.println(modelsList.get(i).toString());
        }
        return modelsList;
    } // getModels
}
