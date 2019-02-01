package view;

import javafx.application.Application;
import javafx.scene.Group;
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
    private ImageView myGridIV;
    private Timeline animation;

    //make button and set text and position
    private Button makeButton(String text, String file, int height, int width, int locationx, int locationy) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        Button button = new Button(text,new ImageView(image));
        button.setMaxSize(height, width);
        button.setLayoutX(locationx);
        button.setLayoutY(locationy);
        button.setWrapText(true);
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

        Group root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT, backgorund);

        Button FileUploadButton = MakeFileUploadButton(stage);

        Button GridSizeButton = makeButton("Choose Grid Size", GridSizeButtonImage, 0,0,0,0);
        //GridSizeButton.setOnMouseClicked(); // should return GridSize; but I'm unsure whether we have to read in certain grid size or user can choose.

        Button PlayButton = makeButton("Play", PlayButtonImage, 0,0,0,0);
        PlayButton.setOnMouseClicked((event)->{
            animation.play();
        });

        Button PauseButton = makeButton("Pause", PauseButtonImage, 0, 0, 0, 0);
        PauseButton.setOnMouseClicked((event)->{
            animation.pause();
        });

        Button ResetButton = makeButton("Reset", ResetButtonImage, 0,0,0,0);
        ResetButton.setOnMouseClicked((event)->{
            animation.stop();
        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 0, 0, 0, 0);
        InitializeButton.setOnMouseClicked((event)->{
            myGridIV.setVisible(true);
        });

       /* myGrid = Grid();
        myGridIV = myGrid.getIV();
        myGridIV.setVisible(false);*/

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