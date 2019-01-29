import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;


public class Visualization extends Application {
    private Scene myScene;
    private String Title = "Cell Automaton";
    private String SimulationButtonImage;
    private String FileUplodaBUttonImage;
    private String GridSizeButtonImage;
    private String PlayButtonImage;
    private String PauseButtonImage;
    private String InitializeButtonImage;
    public static final Paint BACKGROUND = Color.AZURE;
    private int Height = 1000;
    private int Width = 2000;

    //Wanna make a separate class?
    public static class MyNode extends StackPane {

        public MyNode(String name, double x, double y, double width, double height) {

            // create rectangle
            Rectangle rectangle = new Rectangle( width, height);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTBLUE);

            // create label
            Label label = new Label(name);

            // set position
            setTranslateX(x);
            setTranslateY(y);

            getChildren().addAll(rectangle, label);

        }

    }



    public Button makeButton(String text, String file, int height, int width) {
        Image myimage = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageView = new ImageView(myimage);

        Button myButton = new Button(text,imageView);
        myButton.setMaxSize(height, width);
        myButton.setWrapText(true);
        return myButton;
    }

    public void start (Stage stage) {

        myScene = setupVisualization();
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation();
    }
    public void setAnimation(){

    }

    public Scene setupVisualization(Paint backgorund) {
        Group root = new Group();
        Scene myScene = new Scene(root, Width, Height, backgorund);

        Button SimulationButton = makeButton();
        SimulationButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        Button FileUploadButton = makeButton();

        Button GridSizeButton = makeButton();

        Button PlayButton = makeButton();

        Button PauseButton = makeButton();

        Button InitializeButton = makeButton();

        AddGrid();

        root.getChildren().add(SimulationButton);
        root.getChildren().add(FileUploadButton);
        root.getChildren().add(GridSizeButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);

        return myScene;

    }


    public void  AddGrid() {
    }

    public void chooseSimulation() {

    }

    public Simulation setSimulation() {
        }

    private void handlemouseinput(){


    }

    public static void main (String[] args) {
        launch(args);
    }
}

}
