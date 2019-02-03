package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
import java.io.File;
import javafx.scene.control.Alert;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Visualization extends Application {
    private String Title = "Cell Automaton";
    private String ResetButtonImage = "ResetButton.png";
    private String FileUploadButtonImage = "FileUploadButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private String FastForwardButtonImage = "FastForwardButton.png";
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int fontsize2 = 50;
    private static final int fontsize1 = 25;
    private static final int GridDisplaySize = 750;
    private static final int ScreenWIDTH = 1000;
    private static final int ScreenHEIGHT = 1000;
    private int FRAMES_PER_SECOND = 1;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Text SimulationTitle;
    private Text showCount;
    private Scene myScene;
    private Grid myGrid;
    private Text SimulationName;
    public String filepath = "";
    private BorderPane root;
    private Timeline animation;
    private int count;

    public void start (Stage stage) {
        myScene = setupVisualization(stage, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation(stage);
    }

    private void setAnimation(Stage stage){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step(double elapsedtime) {
        System.out.println("Debugging");
        if(root != null) {
            root.getChildren().remove(myGrid.getGridPane());
            myGrid.updateGrid();
            if(myGrid.checkGameEnding()){
                animation.stop();
                makeGameEnding();
            }
            count ++;
            myGrid.setGridPane();
            root.getChildren().add(myGrid.getGridPane());
        }
        else {
            myGrid.updateGrid();
            count ++;
            myGrid.setGridPane();
            root.getChildren().add(myGrid.getGridPane());
        }

        showCount.setText("Rounds: " + count);
    }

    private Scene setupVisualization(Stage stage, Paint backgorund) {
        root = new BorderPane();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);
        FileChooser fileChooser = new FileChooser();
        root.setPadding(new Insets(15, 20, 10, 10));

        Button FileUploadButton = makeButton("UploadFile", FileUploadButtonImage, 100, 100, 50, 150);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        FileUploadButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String filepath1 = selectedFile.toString();
                filepath = filepath1;
            }
        });


        Button PlayButton = makeButton("Play", PlayButtonImage, 100,100, 50, 300);
        BorderPane.setAlignment(PlayButton, Pos.BOTTOM_LEFT);
        PlayButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                animation.play();
            }

        });

        Button FastForwardButton = makeButton("FastForward", FastForwardButtonImage, 100, 100, 50, 450);
        FastForwardButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                FRAMES_PER_SECOND *= 50;
            }

        });

        Button PauseButton = makeButton("Pause", PauseButtonImage, 100, 100, 50, 600);
        BorderPane.setAlignment(PauseButton, Pos.CENTER);
        PauseButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                animation.pause();
            }
        });

        Button ResetButton = makeButton("Reset", ResetButtonImage, 100,100, 50, 750);
        BorderPane.setAlignment(ResetButton, Pos.BASELINE_LEFT);
        ResetButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlert();
            }
            else {
                FRAMES_PER_SECOND = 1;
            }

        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 100, 100, 50, 900);
        BorderPane.setAlignment(InitializeButton, Pos.BASELINE_LEFT);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlert();
            }
            else{
                root.getChildren().remove(myGrid);
                root.getChildren().remove(SimulationName);
                setupGrid(filepath, GridDisplaySize, root);}
        });

        showCount = MakeText(showCount, "Rounds: " + count, 850,975, fontsize1);

        root.getChildren().add(FileUploadButton);
        root.getChildren().add(ResetButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(FastForwardButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);
        root.getChildren().add(showCount);

        return myScene;

    }

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

    private void makeAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText("No Input File");
        alert.setContentText("Please Select Input XML file");
        alert.show();
    }

    private void makeInitialize(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Initialization Error");
        alert.setHeaderText("No Grid on Screen");
        alert.setContentText("Please Initialize the Grid first");
        alert.show();
    }

    private void makeGameEnding(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Simulation Over");
        alert.setHeaderText("Final State");
        alert.setContentText("This is the final state");
        alert.show();

    }
    private Grid setupGrid(String filepath, int displaysize, BorderPane root){
        myGrid = new Grid(filepath, displaysize);
        SimulationName = MakeText(SimulationTitle, myGrid.getSimulationName(),  400, 100, fontsize2);
        myGrid.getGridPane().setVisible(true);
        myGrid.getGridPane().setLayoutX(200);
        myGrid.getGridPane().setLayoutY(150);
        BorderPane.setAlignment(myGrid.getGridPane(),Pos.CENTER_RIGHT);
        root.getChildren().add(SimulationName);
        root.getChildren().add(myGrid.getGridPane());
        return myGrid;
    }

    private Text MakeText(Text text, String message, int x, int y, int FontSize) {
        text = new Text();
        text.setX(x);
        text.setY(y);
        text.setFont(Font.font("Times New Roman", FontSize));
        text.setText(message);
        text.setFill(Color.BLACK);
        return text;
    }


    public static void main (String[] args) {
        launch(args);
    }
}