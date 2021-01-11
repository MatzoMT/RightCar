package cars.project;

import java.io.FileWriter;
import java.io.IOException;

public class DataCollector {

    public void writeToCsv() {
        try {
            int[] modelYears = WebParser.getModelYears();
            FileWriter csvWriter = new FileWriter("years.csv", false);
            csvWriter.write("YEAR,MAKE\n");
            for (int i = 0; i < modelYears.length; i++) {
                if (!(String.valueOf(modelYears[i]).equals("9999"))) {
                    csvWriter.write(String.valueOf(modelYears[i]));
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

    public static void main(String[] args) {
        DataCollector dc = new DataCollector();
        dc.writeToCsv();
    }
}
