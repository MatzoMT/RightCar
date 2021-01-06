package cars.project;

import com.google.gson.*;

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

/**
 * Represents an iTunes GalleryApp.
 */
public class WebParser {
    Scanner keyboard = new Scanner(System.in);

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
        String searchUrl = "https://webapi.nhtsa.gov/api/Complaints/vehicle/modelyear/" + year.trim() + "/make/" + make.trim() + "/model/" + model.trim() + "?format=json";
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
            System.out.println("Number of Complaints: " + complaints);
        } catch (MalformedURLException mue) {
            System.out.println("Error: URL invalid.");
        } catch (IOException ioe) {
            System.out.println("ioe exception");
        }
        getSales(year.trim(), originalMake.replace(" ", "-"), originalModel.replace(" ", "-"));
    } // searchData

    public void getSales(String year, String make, String model) {
        try {
            String link = "https://carsalesbase.com/us-" + make + "-" + model + "/";
            Document document = Jsoup.connect(link).get();
            // Document document = Jsoup.connect("https://carsalesbase.com/us-bmw-x5/").get(); // With the
            Element table = document.select("table").get(1); // select the first table.
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                int intYear = Integer.parseInt(year) - 1;
                if (cols.get(0).text().equals(String.valueOf(intYear))) {
                    System.out.println("REACHED");
                    Element thing = cols.get(1);
                    System.out.println("THING: " + thing.text().replace(".", ""));
                }

            
            }
            /*
             * // Here we create a document object and use JSoup to fetch the website
             * Document doc =
             * Jsoup.connect("https://www.codetriage.com/?language=Java").get(); // With the
             * document fetched, we use JSoup's title() method to fetch the title
             * System.out.printf("Title: %s\n", doc.title()); // Get the list of
             * repositories Elements repositories = doc.getElementsByClass("repo-item");
             * 
             * for (Element repository : repositories) { // Extract the title String
             * repositoryTitle = repository.getElementsByClass("repo-item-title").text(); //
             * Extract the number of issues on the repository String repositoryIssues =
             * repository.getElementsByClass("repo-item-issues").text(); // Extract the
             * description of the repository String repositoryDescription =
             * repository.getElementsByClass("repo-item-description").text(); // Get the
             * full name of the repository String repositoryGithubName =
             * repository.getElementsByClass("repo-item-full-name").text(); // The reposiory
             * full name contains brackets that we remove first before generating the valid
             * Github link. String repositoryGithubLink = "https://github.com/" +
             * repositoryGithubName.replaceAll("[()]", ""); // Format and print the
             * information to the console System.out.println(repositoryTitle + " - " +
             * repositoryIssues); System.out.println("\t" + repositoryDescription);
             * System.out.println("\t" + repositoryGithubLink); System.out.println("\n"); }
             */
            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // getSales

    public static void main(String[] args) {
        WebParser wp = new WebParser();
        wp.searchData();
      //  wp.getSales();
    } // main

} // WebParser
