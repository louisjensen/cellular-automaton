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
import java.io.File;
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
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int ScreenWIDTH = 3000;
    public static final int ScreenHEIGHT = 1500;
    private int Gridsize;
    private Grid myGrid;
    private int tileSize;
    public Simulation mySimulation;
    private Timeline animation;
    public File SimulationName;
    public static final Paint BACKGROUND = Color.WHITE;

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

        MenuButton SimulationButton = MakeSimulationMenuButton();

        Button FileUploadButton = makeButton("Upload File", FileUplodaBUttonImage, 0,0, 0,0);
        FileUploadButton.setOnMouseClicked((event)->{
            FileLoader fileLoader = new FileLoader();
            File fileChosen = fileLoader.chooseFile();
            SimulationName = fileChosen;
        });

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

        GridPane myGridPane = makeGrid(myGrid);
        myGridPane.setVisible(false);

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 0, 0, 0, 0);
        InitializeButton.setOnMouseClicked((event)->{
            myGridPane.setVisible(true);
        });

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
    public void chooseSimulation(String name, Stage s) {
        mySimulation = setSimulation(name);
        Visualization newgame = new Visualization();
        Scene newscene = newgame.setupVisualization(BACKGROUND,s);
        s.setScene(newscene);
        s.setTitle(Title);
        s.setResizable(false);
        s.show();

    }

    public Simulation setSimulation(String name) {
            return new Simulation;
        }

    public static void main (String[] args) {
        launch(args);
    }
}

}
