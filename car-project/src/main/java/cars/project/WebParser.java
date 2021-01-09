package cars.project;

import com.google.gson.*;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebParser {
    Scanner keyboard = new Scanner(System.in);
    int sales = 0;
    int numberComplaints = 0;
    int[] modelYears;

    public void searchData() {
        String year = keyboard.nextLine();
        String make = keyboard.nextLine();
        String originalMake = make.trim();
        if (make.trim().contains(" ") == true) {
            make = make.replace(" ", "%20");
        }
        String model = keyboard.nextLine();
        String originalModel = model.trim();
        if (model.trim().contains(" ") == true) {
            model = model.replace(" ", "%20");
        }
        // Forms valid url to search NHTSA for given model year, make, and model
        String searchUrl = "https://webapi.nhtsa.gov/api/Complaints/vehicle/modelyear/" + year.trim() + "/make/"
                + make.trim() + "/model/" + model.trim() + "?format=json";
        System.out.println(searchUrl);
        try {
            URL url = new URL(searchUrl);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            // Obtains primitive type at Json category "Count" containing number of
            // complaints
            JsonPrimitive complaints = root.getAsJsonPrimitive("Count");
            numberComplaints = complaints.getAsInt();
            System.out.println("Number of Complaints: " + complaints);
        } catch (MalformedURLException mue) {
            System.out.println("Error: URL invalid.");
        } catch (IOException ioe) {
            System.out.println("ioe exception");
        }
        printSales(year.trim(), originalMake.replace(" ", "-"), originalModel.replace(" ", "-"));
        System.out.println(sales);
        System.out.println(numberComplaints);
        System.out.println("SCORE: " + getReliability(numberComplaints, sales));
        System.out.println("A lower score indicates greater reliability.");
    } // searchData

    public void printSales(String year, String make, String model) {
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
    } // printSales

    public double getReliability(double numberComplaints, double sales) {
        double score = (numberComplaints / sales) * 100;
        return score;

    }

    /**
     * Returns the valid model years given in the NHTSA complaints website.
     */
    public void getModelYears() {
        String link = "https://webapi.nhtsa.gov/api/Recalls/vehicle?format=json";
        try {
            URL url = new URL(link);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            JsonObject result;
            JsonElement my;
            // Obtains primitive type at Json category "Count" containing number of
            // complaints
            JsonArray jsonArray = root.getAsJsonArray("Results");
            System.out.println("Number" + jsonArray.size());
            modelYears = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                result = jsonArray.get(i).getAsJsonObject();
                my = result.get("ModelYear");
                System.out.println(i);
                modelYears[i] = my.getAsInt();
            } // for
        } catch (MalformedURLException mue) {

        } catch (IOException ioe) {

        } // try-catch
        /*
         * try { URL url = new URL(searchUrl); InputStream is = url.openStream();
         * InputStreamReader reader = new InputStreamReader(is); JsonElement je =
         * JsonParser.parseReader(reader); JsonObject root = je.getAsJsonObject(); //
         * Obtains primitive type at Json category "Count" containing number of //
         * complaints JsonPrimitive complaints = root.getAsJsonPrimitive("Count");
         * numberComplaints = complaints.getAsInt();
         * System.out.println("Number of Complaints: " + complaints); } catch
         * (MalformedURLException mue) { System.out.println("Error: URL invalid."); }
         * catch (IOException ioe) { System.out.println("ioe exception"); }
         */
    } // getModelYears

    /**
     * Returns the car makes registered with the selected model year.
     */
    public void getMakes(int modelYear) {
        String link = "https://webapi.nhtsa.gov/api/Recalls/vehicle/modelyear/" + modelYear + "?format=json";
        try {
            URL url = new URL(link);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            JsonObject result;
            JsonElement make;
            // Obtains primitive type at Json category "Count" containing number of
            // complaints
            JsonArray jsonArray = root.getAsJsonArray("Results");
            System.out.println("Number" + jsonArray.size());
            modelYears = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                result = jsonArray.get(i).getAsJsonObject();
                make = result.get("Make");
                System.out.println(make);
            } // for
        } catch (MalformedURLException mue) {

        } catch (IOException ioe) {

        } // try-catch
    } // getMakes

/**
     * Returns the car models registered with the selected automaker and model year.
     */
    public void getModels(int modelYear, String make) {
        String link = "https://webapi.nhtsa.gov/api/Recalls/vehicle/modelyear/" + modelYear + "/make/" + make.toLowerCase() + "?format=json";
        try {
            URL url = new URL(link);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            JsonObject result;
            JsonElement model;
            // Obtains primitive type at Json category "Count" containing number of
            // complaints
            JsonArray jsonArray = root.getAsJsonArray("Results");
            System.out.println("Number" + jsonArray.size());
            modelYears = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                result = jsonArray.get(i).getAsJsonObject();
                model = result.get("Model");
                System.out.println(model);
            } // for
        } catch (MalformedURLException mue) {

        } catch (IOException ioe) {

        } // try-catch
    } // getModels

    public static void main(String[] args) {

        WebParser wp = new WebParser();
        wp.getModelYears();
        wp.getMakes(2019);
        wp.getModels(2018, "toyota");
        // wp.getSales();
    } // main

} // WebParser
