package view;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.Group;
import javafx.scene.layout.BackgroundImage;
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
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import java.util.ResourceBundle;

public class Visualization extends Application {
    private String Title = "Cell Automaton";
    private String ResetButtonImage = "ResetButton.png";
    private String FileUploadButtonImage = "FileUploadButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private String FastForwardButtonImage = "FastForwardButton.png";
    private String StepButtonImage = "step.png";
    private static final int fontsize2 = 50;
    private static final int fontsize1 = 25;
    private static final int GridDisplaySize = 900;
    private static final int ScreenWIDTH = 1300;
    private static final int ScreenHEIGHT = 1300;
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final static int BUTTON_SIZE = 100;
    private final static int BUTTON_POS_X = 50;
    private final static int SimulationTitle_POS_X = 400;
    private final static int SimulationTitle_POS_Y = 100;
    private final static int GRID_POS_X = 200;
    private final static int GRID_POS_Y = 150;
    private final String DEFAULT_FONT = "Times New Roman";
    private double AnimationSpeed;
    private Text showCount;
    private Scene myScene;

    //we need to make a class or a variable that can hold all grids.
    private Grid myGrid;
    private Text SimulationName;
    private String filepath = "";
    private Group root;
    private Timeline animation;
    private int count;
    private int simulationSize;
    //private ResourceBundle myResources = ResourceBundle.getBundle("view.textForGui");


   // private ResourceBundle myResources = ResourceBundle.getBundle("textForGui");
    private final String COUNT_TEXT = "Rounds: ";
    private final String UPLOAD_TEXT = "UploadFile";
    private final String STEP_TEXT = "Debug";
    private final String PLAY_TEXT = "Play";
    private final String FAST_FORWARD_TEXT = "FastForward";
    private final String PAUSE_TEXT = "Pause";
    private final String RESET_TEXT = "Reset";
    private final String INITIALIZE_TEXT = "Initialize";

    public void start (Stage stage) {
        myScene = setupVisualization(stage);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(false);
        stage.show();
        setAnimation();
    }

    private void setAnimation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step() {
        if(root != null) {
            root.getChildren().remove(myGrid.getGridPane());
            myGrid.updateGrid();
            if(myGrid.checkGameEnding()){
                animation.stop();
                makeGameEnding();
            }
            myGrid.moveNexttoCurrent();

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

        showCount.setText(COUNT_TEXT + count);
    }

    private Scene setupVisualization(Stage stage) {
        root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);
        FileChooser fileChooser = new FileChooser();
        //root.setPadding(new Insets(15, 20, 10, 10));

        Label numberOfSimulations = new Label("Number of simulations:");
        numberOfSimulations.setLayoutX(50);
        numberOfSimulations.setLayoutY(1150);

        TextField value = new TextField();
        value.setLayoutX(50);
        value.setLayoutY(1200);

        Button numberOfSimulationsButton = makeButton("Set", "submit.png", 1000);
        numberOfSimulationsButton.setMaxSize(50, 50);
        numberOfSimulationsButton.setLayoutX(200);
        numberOfSimulationsButton.setLayoutY(1150);
        numberOfSimulationsButton.setOnMouseClicked(e -> {
            if(value.getText().equals("")){
                makeAlert();
            }
            simulationSize = Integer.valueOf(value.getText());
            System.out.println(simulationSize);
        });



        Button FileUploadButton = makeButton(UPLOAD_TEXT, FileUploadButtonImage, 50);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        FileUploadButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                filepath = selectedFile.toString();
            }
        });

        Button StepButton = makeButton(STEP_TEXT, StepButtonImage, 200);
        StepButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            root.getChildren().remove(myGrid.getGridPane());
            myGrid.updateGrid();
            count ++;
            myGrid.setGridPane();
            root.getChildren().add(myGrid.getGridPane());
        });

        Button PlayButton = makeButton(PLAY_TEXT, PlayButtonImage,  350);
        PlayButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                animation.play();
            }

        });

        Button FastForwardButton = makeButton(FAST_FORWARD_TEXT, FastForwardButtonImage,  500);
        FastForwardButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                AnimationSpeed += 1;
                animation.setRate(AnimationSpeed);
            }
        });

        Button PauseButton = makeButton(PAUSE_TEXT, PauseButtonImage,  650);
        PauseButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            else {
                animation.pause();
            }
        });

        Button ResetButton = makeButton(RESET_TEXT, ResetButtonImage,  950);
        ResetButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            else {
                AnimationSpeed =1;
                animation.setRate(AnimationSpeed);
            }

        });

        Button InitializeButton = makeButton(INITIALIZE_TEXT, InitializeButtonImage, 800);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            else {
                // we will have to add for loop here to create multiple grids
                if(myGrid != null){
                    root.getChildren().remove(myGrid.getGridPane());
                }
                AnimationSpeed = 1;
                animation.setRate(AnimationSpeed);
                count = 0;
                root.getChildren().remove(myGrid);
                root.getChildren().remove(SimulationName);
                setupGrid(filepath, root);
                setupChart(myGrid);
            }
        });

        Slider ChangeSpeedOfGame = makeSlider();
        ChangeSpeedOfGame.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                AnimationSpeed = new_val.doubleValue();
                System.out.println(new_val.doubleValue());
                animation.setRate(AnimationSpeed);
            }
        });


        showCount = MakeText(COUNT_TEXT + count, 850,975, fontsize1);

        root.getChildren().add(ChangeSpeedOfGame);
        root.getChildren().add(StepButton);
        root.getChildren().add(FileUploadButton);
        root.getChildren().add(ResetButton);
        root.getChildren().add(PlayButton);
        root.getChildren().add(FastForwardButton);
        root.getChildren().add(PauseButton);
        root.getChildren().add(InitializeButton);
        root.getChildren().add(showCount);
        root.getChildren().add((numberOfSimulations));
        root.getChildren().add(value);
        root.getChildren().add(numberOfSimulationsButton);

        return myScene;

    }

    private PieChart setupChart(Grid myGrid) {
        ObservableList<PieChart.Data>  pieChartData = FXCollections.observableArrayList(
                //in here loop through myGrid state add do new Piechart.data("name", number)
                //to do this, we need a map inside the grid class
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30));
        PieChart chart = new PieChart(pieChartData);
        chart.setVisible(true);
        chart.setPrefSize(200,200);
        chart.setMinSize(100,100);
        chart.setTitle("Imported Fruits");
        root.getChildren().add(chart);
        return chart;
    }


    private Slider makeSlider(){
        Slider mySlider = new Slider(0,20,1);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(5);
        mySlider.setMinorTickCount(1);
        mySlider.prefWidth(300);
        mySlider.setLayoutX(50);
        mySlider.setLayoutY(1100);
        return mySlider;
    }

    //make button and set text and position
    private Button makeButton(String text, String file, int y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(BUTTON_SIZE);
        imageview.setFitHeight(BUTTON_SIZE);
        Button button = new Button(text,imageview);
        button.setLayoutX(BUTTON_POS_X);
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
        alert.setContentText("This is the final state. Press Reset Button");
        alert.show();
    }
    private void setupGrid(String filepath,  Group root){
        myGrid = new Grid(filepath, GridDisplaySize);
        SimulationName = MakeText(myGrid.getSimulationName(),  SimulationTitle_POS_X, SimulationTitle_POS_Y, fontsize2);
        myGrid.getGridPane().setVisible(true);
        myGrid.getGridPane().setLayoutX(GRID_POS_X);
        myGrid.getGridPane().setLayoutY(GRID_POS_Y);
        BorderPane.setAlignment(myGrid.getGridPane(),Pos.CENTER_RIGHT);
        root.getChildren().add(SimulationName);
        root.getChildren().add(myGrid.getGridPane());
    }

    private Text MakeText(String message, int x, int y, int FontSize) {
        Text text = new Text();
        text.setX(x);
        text.setY(y);
        text.setFont(Font.font(DEFAULT_FONT, FontSize));
        text.setText(message);
        text.setFill(Color.BLACK);
        return text;
    }



    public static void main (String[] args) {
        launch(args);
    }
}
