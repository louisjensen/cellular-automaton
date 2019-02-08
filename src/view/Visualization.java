package view;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;

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
    //private String FastForwardButtonImage = "FastForwardButton.png";
    private String StepButtonImage = "step.png";
    private final String COUNT_TEXT = "Rounds: ";
    private final String UPLOAD_TEXT = "UploadFile";
    private final String STEP_TEXT = "Debug";
    private final String PLAY_TEXT = "Play";
   // private final String FAST_FORWARD_TEXT = "FastForward";
    private final String PAUSE_TEXT = "Pause";
    private final String RESET_TEXT = "Reset";
    private final String INITIALIZE_TEXT = "Initialize";
    private final String DEFAULT_FONT = "Times New Roman";
    private static final int fontsize2 = 50;
    private static final int fontsize1 = 25;
    private static final int GridDisplaySize = 500;
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
    private double AnimationSpeed;
    private Text showCount;
    private Scene myScene;
    private Grid myGrid;
    private Text SimulationName;
    private String filepath = "";
    private Group root;
    private Timeline animation;
    private int count;
    private int simulationSize;
    private String shapetype = "";
    private XMLParser parser;
    private TextField value;
    private PieChart chart;
    //private ResourceBundle myResources = ResourceBundle.getBundle("textForGui");

    public void start (Stage stage) {
        myScene = setupVisualization(stage);
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.setResizable(true);
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
            myGrid.unDisplay(root);
            root.getChildren().remove(chart);
            myGrid.updateGrid();
            if(myGrid.checkGameEnding()){
                animation.stop();
                makeGameEnding();
            }
            myGrid.moveNexttoCurrent();
            count ++;
            myGrid.display(root);
            setupChart(myGrid);
            root.getChildren().add(chart);

        }
        else {
            root.getChildren().remove(chart);
            myGrid.updateGrid();
            count ++;
            myGrid.display(root);
            setupChart(myGrid);
            root.getChildren().add(chart);
        }

        showCount.setText(COUNT_TEXT + count);
    }

    private Scene setupVisualization(Stage stage) {

        root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);
        allButtons(stage);
        makeTextsLabels();
        return myScene;

    }

    //Below here should all be move to a separate class
    //----------------------------------------------------------------------------------------------

    private MenuButton selectCellShape(String file, int x, int y){
        MenuButton menuButton = new MenuButton("Choose Shape");
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(BUTTON_SIZE);
        iv.setFitWidth(BUTTON_SIZE);
        menuButton.setGraphic(iv);
        menuButton.setLayoutX(x);
        menuButton.setLayoutY(y);
        MenuItem triangle = new MenuItem("triangle");
        triangle.setOnAction(event -> {
           shapetype = "triangle";
        });
        MenuItem rectangle = new MenuItem("rectangle");
        rectangle.setOnAction(event -> {
            shapetype = "rectangle";
        });
        MenuItem hexagon = new MenuItem("hexagon");
        hexagon.setOnAction(event -> {
           shapetype = "hexagon";
        });


        menuButton.getItems().addAll(triangle, rectangle, hexagon);
        return menuButton;
    }

    private PieChart setupChart(Grid myGrid) {

        //have to read in grid info as a map

        int white=0;
        int black=0;

        for(int row=0; row<myGrid.getMyCurrentState().length; row++){
            for(int col=0; col<myGrid.getMyCurrentState()[0].length; col++){
                if (myGrid.getMyCurrentState()[row][col].getState() ==0){
                    white++;
                }
                else{
                    black++;
                }
            }
        }
        ObservableList<PieChart.Data>  pieChartData = FXCollections.observableArrayList(
                //in here loop through myGrid state add do new Piechart.data("name", number)
                //to do this, we need a map inside the grid class
                new PieChart.Data("Dead", white),
                new PieChart.Data("Alive", black));

        chart = new PieChart(pieChartData);
        chart.setVisible(true);
        chart.setLayoutX(650);
        chart.setLayoutY(ScreenHEIGHT - 200);
        chart.setPrefSize(200,200);
        chart.setMinSize(100,100);
        chart.setTitle("Number of Variables");
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

    private void invalidFileError(){
        Alert fileError = new Alert(Alert.AlertType.CONFIRMATION);
        fileError.setTitle("Invalid File");
        fileError.setHeaderText("This is not a valid file");
        fileError.setContentText("Please upload a valid XML configuration file");
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
    private void setupGrid(String filepath,  Group root, String shapetype){
        if(shapetype.equals("triangle")){
            myGrid = new TriangleGrid(filepath, GridDisplaySize);
        }
        else if(shapetype.equals("rectangle")){
            myGrid = new RectangleGrid(filepath, GridDisplaySize);
        }
        else if(shapetype.equals("hexagon")){
            myGrid = new HexagonGrid(filepath, GridDisplaySize);
        }
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


    private void allButtons(Stage stage){
        FileChooser fileChooser = new FileChooser();


        TextField value = new TextField();
        value.setLayoutX(50);
        value.setLayoutY(1200);

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("submit.png"));
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(50);
        imageview.setFitHeight(30);
        Button numberOfSimulationsButton= new Button("Submit",imageview);
        numberOfSimulationsButton.setLayoutX(200);
        numberOfSimulationsButton.setLayoutY(1150);
        numberOfSimulationsButton.setOnMouseClicked(e -> {
            if(value.getText().equals("") || value.getText() == null){
                makeAlert();
            }
            simulationSize = Integer.valueOf(value.getText());
            System.out.println(simulationSize);
        });

        Button FileUploadButton = makeButton(UPLOAD_TEXT, FileUploadButtonImage, 250);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        FileUploadButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                filepath = selectedFile.toString();
            }
            parser = new XMLParser(filepath);
          //  if (parser.getSimulationType() == null){
            //    invalidFileError();
            //    filepath="";
          //  }
        });

        MenuButton chooseShape = selectCellShape("shapes.png", 50, 200 );

        Button StepButton = makeButton(STEP_TEXT, StepButtonImage, 350 );
        StepButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                makeAlert();
            }
            if(myGrid == null){
                makeInitialize();
            }
            myGrid.unDisplay(root);
            myGrid.updateGrid();
            count ++;
            myGrid.display(root);
        });

        Button PlayButton = makeButton(PLAY_TEXT, PlayButtonImage,  500);
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
                System.out.println("this is it");
            }
            else {
                // we will have to add for loop here to create multiple grids
                if(myGrid != null){
                    myGrid.unDisplay(root);
                }
                AnimationSpeed = 1;
                animation.setRate(AnimationSpeed);
                count = 0;
                //root.getChildren().remove(myGrid);
                root.getChildren().remove(SimulationName);
                setupGrid(filepath, root, shapetype);
                myGrid.initialize();
                myGrid.setInitialGridColors();
                myGrid.display(root);
                setupChart(myGrid);
                root.getChildren().add(chart);
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

        root.getChildren().addAll(chooseShape, ChangeSpeedOfGame, StepButton, FileUploadButton, ResetButton, PlayButton, PauseButton, InitializeButton,
                value, numberOfSimulationsButton);
    }

    private void makeTextsLabels(){
        Label numberOfSimulations = new Label("Number of simulations:");
        numberOfSimulations.setLayoutX(50);
        numberOfSimulations.setLayoutY(1150);
        showCount = MakeText(COUNT_TEXT + count, 850,975, fontsize1);
        root.getChildren().addAll(numberOfSimulations, showCount);

    }

    public static void main (String[] args) {
        launch(args);
    }
}
