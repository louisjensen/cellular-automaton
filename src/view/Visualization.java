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
import javafx.scene.control.TextField;
import javafx.animation.Animation;

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
    private static final int GridDisplaySize = 750;
    private static final int ScreenWIDTH = 1000;
    private static final int ScreenHEIGHT = 1000;
    private static final int FRAMES_PER_SECOND = 60;
    private Text SimulationTitle;
    private Scene myScene;
    private Grid myGrid;
    private Text SimulationName;
    //private Animation animation;
    public String filepath = "";

    public void start (Stage stage) {
        myScene = setupVisualization(stage, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        //setAnimation(stage);
    }

/*    private void setAnimation(Stage stage){
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step(double elapsedtime) {}*/

    private Scene setupVisualization(Stage stage, Paint backgorund) {
        BorderPane root = new BorderPane();
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
                makeAlertButton();
            }
            else {
                //animation.play();
                root.getChildren().remove(myGrid.getGridPane());
                myGrid.updateGrid();
                myGrid.setGridPane();
                root.getChildren().add(myGrid.getGridPane());
            }

        });

        Button FastForwardButton = makeButton("FastForward", FastForwardButtonImage, 100, 100, 50, 450);
        FastForwardButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlertButton();
            }
            else {
                root.getChildren().remove(myGrid.getGridPane());
                for(int i=0; i<25; i++) {
                    myGrid.updateGrid();
                    myGrid.setGridPane();
                }
                root.getChildren().add(myGrid.getGridPane());
            }

        });

        Button PauseButton = makeButton("Pause", PauseButtonImage, 100, 100, 50, 600);
        BorderPane.setAlignment(PauseButton, Pos.CENTER);
        PauseButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlertButton();
            }
            else {
                //animation.pause();
            }
        });

        Button ResetButton = makeButton("Reset", ResetButtonImage, 100,100, 50, 750);
        BorderPane.setAlignment(ResetButton, Pos.BASELINE_LEFT);
        ResetButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlertButton();
            }
            else {
                root.getChildren().remove(myGrid);
                setupGrid(filepath, GridDisplaySize, root);
            }

        });

        Button InitializeButton = makeButton("Initialize", InitializeButtonImage, 100, 100, 50, 900);
        BorderPane.setAlignment(InitializeButton, Pos.BASELINE_LEFT);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath == ""){
                makeAlertButton();
            }
            else{setupGrid(filepath, GridDisplaySize, root);}
        });

        root.getChildren().add(FileUploadButton);
        root.getChildren().add(ResetButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(FastForwardButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);

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

    private void makeAlertButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText("No Input File");
        alert.setContentText("Please Select Input XML file");
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