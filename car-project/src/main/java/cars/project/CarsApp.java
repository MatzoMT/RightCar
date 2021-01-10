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
    public void start(Stage stage) {
        HBox habox = new HBox();
        ComboBox yearComboBox = new ComboBox();

        WebParser webParser = new WebParser();
        int[] modelYears = webParser.getModelYears();
        for (int i = 0; i < webParser.getModelYears().length; i++) {
            yearComboBox.getItems().add(modelYears[i]);
        } // for
        habox.getChildren().add(yearComboBox);
        Scene title = new Scene(habox, 300, 400);
        stage.setScene(title);
        stage.setTitle("BetterCar");
        stage.sizeToScene();
        stage.show();    
    } // start
}
