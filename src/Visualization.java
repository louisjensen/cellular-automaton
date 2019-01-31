import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.io.File;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final int ScreenHEIGHT = 1500;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Scene myScene;
    private Grid myGrid;
    private ImageView myGridIV;
    private Timeline animation;

    //make button and set text and position
    private Button makeButton(String text, String file, int height, int width, int locationx, int locationy) {
        Image myimage = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageView = new ImageView(myimage);
        Button myButton = new Button(text,imageView);
        myButton.setMaxSize(height, width);
        myButton.setLayoutX(locationx);
        myButton.setLayoutY(locationy);
        myButton.setWrapText(true);
        return myButton;
    }


    //button to choose which file to upload
    private Button MakeFileUploadButton(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        Button openButton = new Button("Open a Picture...");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openFile(file);
                }
            }
        });
        return openButton;
    }

    private void openFile(File file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    FileLoader.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
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

    private void step(double elapsedtime){
        Grid.updateGrid(elapsedtime);
    }

    private Scene setupVisualization(Stage stage, Paint backgorund) {

        Group root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT, backgorund);

        Button FileUploadButton = MakeFileUploadButton(stage);

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

        Button ResetButton = makeButton("Reset", ResetButtonImage, 0,0,0,0);
        ResetButton.setOnMouseClicked(PauseButton.setOnMouseClicked((event)->{
            animation.stop();
            //how to reset
        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 0, 0, 0, 0);
        InitializeButton.setOnMouseClicked((event)->{
            myGridIV.setVisible(true);
        });

        myGrid = Grid();
        myGridIV = myGrid.getIV();
        myGridIV.setVisible(false);

        root.getChildren().add(myGridIV);
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