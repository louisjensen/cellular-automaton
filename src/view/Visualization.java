package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
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
import java.io.File;

public class Visualization extends Application {
    private String Title = "Cell Automaton";
    private String ResetButtonImage = "ResetButton.png";
    private String FileUploadButtonImage = "FileUploadButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int GridDisplaySize = 750;
    private static final int ScreenWIDTH = 1100;
    private static final int ScreenHEIGHT = 1100;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Scene myScene;
    private Grid myGrid;
    private Timeline animation;
    public String filepath = " ";

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
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double elapsedtime) {
        System.out.println("printed");
    }

    private Scene setupVisualization(Stage stage, Paint backgorund) {
        BorderPane root = new BorderPane();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);
        FileChooser fileChooser = new FileChooser();
        root.setPadding(new Insets(15, 20, 10, 10));

        Button FileUploadButton = makeButton("UploadFile", FileUploadButtonImage, 100, 100, 100, 100);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        FileUploadButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String filepath1 = selectedFile.toString();
                filepath = filepath1;
            }
        });


        Button PlayButton = makeButton("Play", PlayButtonImage, 100,100, 100, 300);
        BorderPane.setAlignment(PlayButton, Pos.BOTTOM_LEFT);
        PlayButton.setOnMouseClicked((event)->{
            root.getChildren().remove(myGrid.getGridPane());
            myGrid.updateGrid();
            myGrid.setGridPane();
            root.getChildren().add(myGrid.getGridPane());

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
            root.getChildren().remove(myGrid);
            setupGrid(filepath, GridDisplaySize, root);

        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 100, 100, 100, 900);
        BorderPane.setAlignment(InitializeButton, Pos.BASELINE_LEFT);
        InitializeButton.setOnMouseClicked((event)->{
            setupGrid(filepath, GridDisplaySize, root);
        });

        root.getChildren().add(FileUploadButton);
        root.getChildren().add(ResetButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);

        return myScene;

    }

    private Grid setupGrid(String filepath, int displaysize, BorderPane root){
        myGrid = new Grid(filepath, displaysize);
        myGrid.getGridPane().setVisible(true);
        myGrid.getGridPane().setLayoutX(300);
        myGrid.getGridPane().setLayoutY(100);
        BorderPane.setAlignment(myGrid.getGridPane(),Pos.CENTER_RIGHT);
        root.getChildren().add(myGrid.getGridPane());
        return myGrid;
    }

    public static void main (String[] args) {
        launch(args);
    }
}