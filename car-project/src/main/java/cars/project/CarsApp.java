package cars.project;

import java.util.Scanner;
import java.util.ArrayList;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.control.ComboBox;

public class CarsApp extends Application {
    HBox titleBar = new HBox();
    HBox selectionsHBox = new HBox();
    HBox infoHBox = new HBox();
    ComboBox<Integer> yearComboBox = new ComboBox();
    ComboBox<String> makeComboBox = new ComboBox();
    ComboBox<String> modelComboBox = new ComboBox();
    int selectedYear;
    String selectedMake;

    public void start(Stage stage) {
        BorderPane bp = new BorderPane();
        VBox window = new VBox();
        WebParser webParser = new WebParser();
        DataCollector dataCollector = new DataCollector();
        ArrayList<Integer> modelYears = dataCollector.getModelYears();
        Image logo = new Image("file:car-project/resources/logo.png", 200, 200, true, true);
        ImageView logoView = new ImageView(logo);
        logoView.setX(75);
        for (int i = 0; i < modelYears.size(); i++) {
            yearComboBox.getItems().add(modelYears.get(i));
        }

        yearComboBox.setOnAction((event) -> {
            System.out.println(yearComboBox.getValue());
            selectedYear = yearComboBox.getValue();
            ArrayList<String> makes = DataCollector.getMakes(yearComboBox.getValue());
            makeComboBox.getItems().clear();
            for (int i = 0; i < makes.size(); i++) {
                System.out.println(i);
                makeComboBox.getItems().add(makes.get(i));
            }
            System.out.println("DONEEEEE");
        });

        makeComboBox.setOnAction((event) -> {
            selectedMake = makeComboBox.getValue();
            ArrayList<String> models = DataCollector.getModels(yearComboBox.getValue(), makeComboBox.getValue());
          //  String[] models = WebParser.getModels(yearComboBox.getValue(), makeComboBox.getValue());
            modelComboBox.getItems().clear();
            for (int i = 0; i < models.size(); i++) {
                System.out.println(i);
                modelComboBox.getItems().add(models.get(i));
            } // for
        });

        modelComboBox.setOnAction((event) -> {
            DataCollector.getImageLink(yearComboBox.getValue(), makeComboBox.getValue(), modelComboBox.getValue());
            displayInformation(yearComboBox.getValue(), makeComboBox.getValue(), modelComboBox.getValue());
        });


        titleBar.getChildren().add(logoView);

        titleBar.setBackground(
                new Background(new BackgroundFill(Color.rgb(22, 99, 19), CornerRadii.EMPTY, Insets.EMPTY)));

        selectionsHBox.setBackground(
            new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        infoHBox.setBackground(
            new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        Text placeholder = new Text(" ");
        infoHBox.getChildren().add(placeholder);

       // logoView.setPadding(new Insets(10));
        yearComboBox.setStyle("-fx-font: 12px \"Verdana\"; -fx-pref-width: 200;");
        makeComboBox.setStyle("-fx-font: 12px \"Verdana\"; -fx-pref-width: 200;");
        modelComboBox.setStyle("-fx-font: 12px \"Verdana\"; -fx-pref-width: 200;");
    //    yearComboBox.setStyle("-fx-pref-width: 150;");

        yearComboBox.setPromptText("Year");
        makeComboBox.setPromptText("Make");
        modelComboBox.setPromptText("Model");
        selectionsHBox.getChildren().addAll(yearComboBox, makeComboBox, modelComboBox);

        window.getChildren().addAll(titleBar, selectionsHBox);
        
        bp.setTop(titleBar);
     //   bp.getChildren().add(selectionsHBox);
        bp.setCenter(selectionsHBox);
        bp.setBottom(infoHBox);
       // bp.getChildren().addAll(titleBar, selectionsHBox);
       
        Scene title = new Scene(bp);
        getSelectedYear();
        stage.setScene(title);
        stage.setTitle("BetterCar");
        stage.sizeToScene();
        stage.show();

    } // start

    public void getSelectedYear() {
        /*
         * boolean yearSelected = false; while (true) { System.out.println("iterating");
         * System.out.println(yearComboBox.getValue());
         * 
         * }
         */
    } // getSelectedYear

    /**
     * Method that allows for the creation of a new {@code Thread} to operate
     * multiple parts of the code simultaneously.
     *
     * @param runnable an object that implements {@code Runnable} or is runnable
     */
    private static void runNow(Runnable runnable) throws InterruptedException {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
    } // runNow

    private void displayInformation(int year, String make, String model) {
        Text yearText = new Text(Integer.toString(year) + " " + make + " " + model);
        Text salesText = new Text(Integer.toString(DataCollector.printSales(Integer.toString(year), make, model)));
        yearText.setFont(Font.font("century gothic", FontWeight.BOLD, FontPosture.REGULAR, 20));
        salesText.setFont(Font.font("century gothic", FontWeight.BOLD, FontPosture.REGULAR, 20));
        infoHBox.getChildren().addAll(yearText, salesText);
    }

}
