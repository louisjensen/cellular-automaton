package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunGame extends Application {

    Visualization GameDriver = new Visualization();

    public void start (Stage stage) {
        GameDriver.start(stage);
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
