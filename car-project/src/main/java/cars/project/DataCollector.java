package cars.project;

import java.io.FileWriter;
import java.io.IOException;

public class DataCollector {

    public void writeToCsv() {
        try {
            FileWriter csvWriter = new FileWriter("years.csv");
            csvWriter.write("YEAR,MAKE");
            System.out.println("completed");
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ioe) {
            System.out.println("IOException occurred");
        }
    }

    public static void main(String[] args) {
        DataCollector dc = new DataCollector();
        dc.writeToCsv();
    }
}
