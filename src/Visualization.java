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
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;



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
    private int Gridsize;
    private Grid myGrid;
    private int tileSize;
    public Simulation mySimulation;
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

    //make grid
    private GridPane makeGrid(Grid myGrid) {

        GridPane gridPane = new GridPane();

        // for visualizing the different squares:
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setStyle("-fx-background-color: grey;");

        for (int y = 0 ; y < myGrid.length() ; y++) {
            for (int x = 0 ; x < myGrid[y].length ; x++) {
                ImageView imageView = new ImageView(myGrid[y][x]);
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                gridPane.add(imageView, x, y);
            }
        }
        gridPane.setLayoutX();
        gridPane.setLayoutY();
        return gridPane;
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
            public void handle(ActionEvent event) {
                chooseSimulation("Option 3");
            }
        });
        MenuButton SimulationButton = new MenuButton("Select Simulation", null, menuItem1, menuItem2, menuItem3);
        Image myimage = new Image(getClass().getClassLoader().getResourceAsStream(SimulationButtonImage));
        SimulationButton.setGraphic(new ImageView(myimage));


        return  SimulationButton;
        }


    public void start (Stage stage) {

        myScene = setupVisualization();
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation(stage);
    }

    public void setAnimation(Stage stage){
        animation = new Timeline();
    }

    public Scene setupVisualization(Paint backgorund) {
        Group root = new Group();
        Scene myScene = new Scene(root, Width, Height, backgorund);

        MenuButton SimulationButton = MakeSimulationMenuButton();

        Button FileUploadButton = makeButton("Upload File", FileUplodaBUttonImage, 0,0, 0,0);
        FileUploadButton.setOnMouseClicked();

        Button GridSizeButton = makeButton("Choose Grid Size", GridSizeButtonImage, 0,0,0,0);
        GridSizeButton.setOnMouseClicked(); // should return GridSize;

        Button PlayButton = makeButton("Play", PlayButtonImage, 0,0,0,0);
        PlayButton.setOnMouseClicked();

        Button PauseButton = makeButton();
        PauseButton.setOnMouseClicked();

        Button InitializeButton = makeButton();
        InitializeButton.setOnMouseClicked();

        GridPane myGridPane = makeGrid();

        root.getChildren().add(myGridPane);
        root.getChildren().add(SimulationButton);
        root.getChildren().add(FileUploadButton);
        root.getChildren().add(GridSizeButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);

        return myScene;

    }
    //
    public void chooseSimulation(String name) {
        mySimulation = setSimulation(name);
    }

    public Simulation setSimulation(String name) {
            return Simulation;
        }

    public static void main (String[] args) {
        launch(args);
    }
}

}
