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
import java.net.URL;
import com.google.gson.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebAssert;
// import static org.junit.Assert.*;

public class DataCollector {

    private WebClient webClient;

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
            String SQL = "select distinct Model from car_project.car_information where year='" + modelYear
                    + "' and make='" + make + "';";
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

    public static void getImageLink(int year, String make, String model) {
        /*
         * if (make.trim().contains(" ") == true) { make = make.replace(" ", "%20"); }
         * // if if (model.trim().contains(" ") == true) { model = model.replace(" ",
         * "%20"); } // if make = make.toUpperCase(); model = model.toUpperCase(); try {
         * String link = "https://www.nhtsa.gov/vehicle/" + year + "/" + make + "/" +
         * model; //String link =
         * "https://webapi.nhtsa.gov/api/Recalls/vehicle/modelyear/" + year + "/make/" +
         * make.toLowerCase() + "/model/" + model + "?format=json";
         * System.out.println(link); Document document = Jsoup.connect(link).get();
         * System.out.println(document); Elements elements =
         * document.getElementsByTag("img"); Element img =
         * document.select("div.vehicle-base-details").first(); for (Element e:
         * elements) { String alt = e.attr("alt"); System.out.println("alt:  " + alt); }
         * System.out.println(img); } catch (IOException ioe) {
         * System.out.println("error"); }
         */

    } // getImageLink

    public void givenAClient_whenEnteringBaeldung_thenPageTitleIsOk() throws Exception {
        HtmlPage page = webClient.getPage("/");

       // Assert.assertEquals("Baeldung | Java, Spring and Web Development tutorials", page.getTitleText());
    }

    public static int printSales(String year, String make, String model) {
        int sales = 0;
        try {
            String link = "https://carsalesbase.com/us-" + make + "-" + model + "/";
            System.out.println(link);
            Document document = Jsoup.connect(link).get();
            Element table = document.select("table").get(1); // select the first table.
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                int intYear = Integer.parseInt(year) - 1;
                if (cols.get(0).text().equals(String.valueOf(intYear))) {
                    System.out.println("REACHED");
                    Element thing = cols.get(1);
                    sales = Integer.parseInt(thing.text().replace(".", ""));
                    System.out.println("SALES: " + sales);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sales;
    } // printSales

    /*
    public void homePage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
            Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
    
            final String pageAsXml = page.asXml();
            Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
    
            final String pageAsText = page.asText();
            Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
        }
    }
    */
}
