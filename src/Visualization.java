import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.animation.Timeline;

public class Visualization extends Application {
    private Scene myScene;
    private String Title = "Cell Automaton";
    private String SimulationButtonImage = "InitializeButton.png";
    private String FileUplodaBUttonImage = "FileUploadButton.png";
    private String GridSizeButtonImage = "SizeButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int ScreenWIDTH = 3000;
    private static final int ScreenHEIGHT = 1500;
    private ImageView myGrid;
    private Timeline animation;

    //make button and set text and position
    public Button makeButton(String text, String file, int height, int width, int locationx, int locationy) {
        Image myimage = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageView = new ImageView(myimage);
        Button myButton = new Button(text,imageView);
        myButton.setMaxSize(height, width);
        myButton.setLayoutX(locationx);
        myButton.setLayoutY(locationy);
        myButton.setWrapText(true);
        return myButton;
    }


    //make simulationbutton(dropdown possible)
    private MenuButton MakeSimulationMenuButton() {
        MenuItem menuItem1 = new MenuItem("Option 1");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseSimulation("Option 1");
            }
        });

        MenuItem menuItem2 = new MenuItem("Option 2");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chooseSimulation("Option 2");
            }
        });
        MenuItem menuItem3 = new MenuItem("Option 3");
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvenstaget event) {
                chooseSimulation("Option 3");
            }
        });
        MenuButton SimulationButton = new MenuButton("Select Simulation", null, menuItem1, menuItem2, menuItem3);
        Image myimage = new Image(getClass().getClassLoader().getResourceAsStream(SimulationButtonImage));
        SimulationButton.setGraphic(new ImageView(myimage));


        return  SimulationButton;
        }

    public void start (Stage stage) {
        myScene = setupVisualization(stage, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation(stage);
    }

    public void setAnimation(Stage stage){ animation = new Timeline();


    }

    public Scene setupVisualization(Stage stage, Paint backgorund) {

        Group root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT, backgorund);

        MenuButton FileUploadButton = MakeSimulationMenuButton();

        Button GridSizeButton = makeButton("Choose Grid Size", GridSizeButtonImage, 0,0,0,0);
        GridSizeButton.setOnMouseClicked(); // should return GridSize; but I'm unsure whether we have to read in certain grid size or user can choose.

        Button PlayButton = makeButton("Play", PlayButtonImage, 0,0,0,0);
        PlayButton.setOnMouseClicked((event)->{
            animation.play();
        });

        Button PauseButton = makeButton("Pause", PauseButtonImage, 0, 0, 0, 0);
        PauseButton.setOnMouseClicked((event)->{
            animation.pause();
        });

        Button ResetButton = makeButton();

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 0, 0, 0, 0);
        InitializeButton.setOnMouseClicked((event)->{
            myGrid.setVisible(true);
        });

        myGrid.initialize();

        root.getChildren().add(myGrid);
        root.getChildren().add(FileUploadButton);
        root.getChildren().add(GridSizeButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);

        return myScene;

    }

    public static void main (String[] args) {
        launch(args);
    }
}

}
