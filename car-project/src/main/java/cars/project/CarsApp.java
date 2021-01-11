package cars.project;

import java.util.Scanner;
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
    HBox habox = new HBox();
    ComboBox<Integer> yearComboBox = new ComboBox();
    ComboBox<String> makeComboBox = new ComboBox();
    ComboBox<String> modelComboBox = new ComboBox();
    int selectedYear;
    String selectedMake;

    public void start(Stage stage) {
        WebParser webParser = new WebParser();
        int[] modelYears = webParser.getModelYears();
        for (int i = 0; i < webParser.getModelYears().length; i++) {
            yearComboBox.getItems().add(modelYears[i]);
        } // for

        yearComboBox.setOnAction((event) -> {        
            System.out.println(yearComboBox.getValue());
            selectedYear = yearComboBox.getValue();
            String[] makes = WebParser.getMakes(yearComboBox.getValue());
            for (int i = 0; i < WebParser.getMakes(yearComboBox.getValue()).length; i++) {
                System.out.println(i);
                makeComboBox.getItems().add(makes[i]);
            }
            System.out.println("DONEEEEE");
        });

        makeComboBox.setOnAction((event) -> {
            selectedMake = makeComboBox.getValue();
            String[] models = WebParser.getModels(yearComboBox.getValue(), makeComboBox.getValue());
            for (int i = 0; i < models.length; i++) {
                System.out.println(i);
                modelComboBox.getItems().add(models[i]);
            } // for
        });

        habox.getChildren().addAll(yearComboBox, makeComboBox, modelComboBox);
        Scene title = new Scene(habox, 300, 400);
        getSelectedYear();
        stage.setScene(title);
        stage.setTitle("BetterCar");
        stage.sizeToScene();
        stage.show();

    } // start

    public void getSelectedYear() {
        /*
        boolean yearSelected = false;
        while (true) {
            System.out.println("iterating");
            System.out.println(yearComboBox.getValue());

        }
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

}
