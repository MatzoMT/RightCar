package cars.project;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Represents an iTunes GalleryApp.
 */
public class WebParser {
    Scanner keyboard = new Scanner(System.in);

    public void searchData() {
        String year = keyboard.nextLine();
        year = year.trim();
        String make = keyboard.nextLine();
        if (make.trim().contains(" ") == true) {
            make = make.replace(" ", "%20");
        }
        make = "/make/" + make.trim();
        String model = keyboard.nextLine();
        if (model.trim().contains(" ") == true) {
            model = model.replace(" ", "%20");
        }
        model = "/model/" + model.trim() + "?format=json";
        // Forms valid url to search NHTSA for given model year, make, and model
        String searchUrl = "https://webapi.nhtsa.gov/api/Complaints/vehicle/modelyear/" + year + make + model;
        System.out.println(searchUrl);
        try {
            URL url = new URL(searchUrl);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            // Obtains primitive type at Json category "Count" containing number of complaints
            JsonPrimitive complaints = root.getAsJsonPrimitive("Count");
            System.out.println("Number of Complaints: " + complaints);
        } catch (MalformedURLException mue) {
            System.out.println("Error: URL invalid.");
        } catch (IOException ioe) {
            System.out.println("ioe exception");
        }
    } // searchData

    public static void main(String[] args) {
         WebParser wp = new WebParser();
         wp.searchData();
    } // main

} // WebParser
