package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

// New
import javafx.geometry.Pos;
//New

public class Visualization extends Application {
    private String Title = "Cell Automaton";
    private String SimulationButtonImage = "InitializeButton.png";
    private String ResetButtonImage = "ResetButton.png";
    private String GridSizeButtonImage = "SizeButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int ScreenWIDTH = 3000;
    private static final int ScreenHEIGHT = 3000;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Scene myScene;
    private Grid myGrid;
    private Timeline animation;

    //make button and set text and position
    private Button makeButton(String text, String file, int height, int width, int x, int y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(width);
        imageview.setFitHeight(height);
        Button button = new Button(text,imageview);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }


    //button to choose which file to upload
    private Button MakeFileUploadButton(Stage stage){
        Button openButton = new Button("Open a Picture...");
        openButton.setOnMouseClicked((event)->{
        });
        return openButton;
    }


    public void start (Stage stage) {
        myScene = setupVisualization(stage, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation(stage);
    }

    private void setAnimation(Stage stage){
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private void step(double elapsedtime) {
    }

    private Scene setupVisualization(Stage stage, Paint backgorund) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15, 20, 10, 10));

        Button FileUploadButton = MakeFileUploadButton(stage);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        Button GridSizeButton = makeButton("Choose Grid Size", GridSizeButtonImage, 100,100, 100, 100);
        //GridSizeButton.setOnMouseClicked(); // should return GridSize; but I'm unsure whether we have to read in certain grid size or user can choose.

        Button PlayButton = makeButton("Play", PlayButtonImage, 100,100, 100, 300);
        BorderPane.setAlignment(PlayButton, Pos.BOTTOM_LEFT);
        PlayButton.setOnMouseClicked((event)->{
            animation.play();
        });

        Button PauseButton = makeButton("Pause", PauseButtonImage, 100, 100, 100, 500);
        BorderPane.setAlignment(PauseButton, Pos.CENTER);
        PauseButton.setOnMouseClicked((event)->{
            animation.pause();
        });

        Button ResetButton = makeButton("Reset", ResetButtonImage, 100,100, 100, 700);
        BorderPane.setAlignment(ResetButton, Pos.BASELINE_LEFT);
        ResetButton.setOnMouseClicked((event)->{
            animation.stop();
        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 100, 100, 100, 900);
        BorderPane.setAlignment(InitializeButton, Pos.BASELINE_LEFT);
        InitializeButton.setOnMouseClicked((event)->{
            myGrid.getGridPane().setVisible(true);
        });

        // NEW STUFF

        myGrid = new Grid(750);
        myGrid.getGridPane().setVisible(true);
        BorderPane.setAlignment(myGrid.getGridPane(),Pos.CENTER_RIGHT);
        root.getChildren().add(myGrid.getGridPane());
        //myGridIV = myGrid.getGridPane();
        //myGrid.getGridPane().setVisible(false);
        // NEW STUFF

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(PauseButton);

        root.setTop(borderPane);
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);

        //root.getChildren().add(myGridIV);
        root.getChildren().add(FileUploadButton);
        root.getChildren().add(ResetButton);
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