package view;


import Grid.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.chart.*;
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
    private String ShapeButtonImage = "shapes.png";
    private final String COUNT_TEXT = "Rounds: ";
    private final String UPLOAD_TEXT = "UploadFile";
    private final String STEP_TEXT = "Debug";
    private final String PLAY_TEXT = "Play";
   // private final String FAST_FORWARD_TEXT = "FastForward";
    private final String PAUSE_TEXT = "Pause";
    //private final String RESET_TEXT = "Reset";
    private final String SubmitButtonImage = "submit.png";
    private final String MultipleSimulationsButtonImage = "MultipleButton.png";
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
    private int simulationNumber;
    private String shapetype = "";
    private XMLParser parser;
    private TextField value;
    private PieChart myChart;
    private ArrayList<Grid> allGrids;
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
            unDisplayAllGrids(root);
            //root.getChildren().remove(myChart);
            //myGrid.updateGrid();
            UpdateAllGrids();
            /*if(myGrid.checkGameEnding()){
                animation.stop();
                makeGameEnding();
            }*/
            //myGrid.moveNexttoCurrent();
            moveNexttoCurrentAllGrids();
            count ++;
            System.out.println("aha");
            DisplayAllGrids(root);

            //myChart = setupChart(myGrid);
            //root.getChildren().add(myChart);

        }
        else {
            //root.getChildren().remove(myChart);
            //myGrid.updateGrid();
            count ++;
            DisplayAllGrids(root);
            System.out.println("aha1");
            // myChart = setupChart(myGrid);
            //root.getChildren().add(myChart);
        }

        //showCount.setText(COUNT_TEXT + count);
    }

    private Scene setupVisualization(Stage stage) {
        root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT);
        allButtons(stage);
        return myScene;
    }

    //Below here should all be move to a separate class
    //----------------------------------------------------------------------------------------------
    private void moveNexttoCurrentAllGrids(){
        for(int a=0; a<allGrids.size(); a++){
            allGrids.get(a).moveNexttoCurrent();
        }
    }
    private void UpdateAllGrids(){
        for(int a=0; a<allGrids.size(); a++){
            allGrids.get(a).updateGrid();
        }
    }

    private void DisplayAllGrids(Group root){
        for(int a=0; a<allGrids.size(); a++){
            allGrids.get(a).display(root);
        }
    }

    private void unDisplayAllGrids(Group root){
        for(int a=0; a<allGrids.size(); a++){
            allGrids.get(a).unDisplay(root);
        }
    }
    
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

    private MenuButton selectNumSimulations(String file, int x, int y){
        MenuButton menuButton = new MenuButton("Number of Simulations");
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(BUTTON_SIZE);
        iv.setFitWidth(BUTTON_SIZE);
        menuButton.setGraphic(iv);
        menuButton.setLayoutX(x);
        menuButton.setLayoutY(y);
        MenuItem onesimulation = new MenuItem("1");
        onesimulation.setOnAction(event -> {
            simulationNumber = 1;
        });
        MenuItem twosimulations = new MenuItem("2");
        twosimulations.setOnAction(event -> {
            simulationNumber = 2;
        });
        MenuItem foursimulations = new MenuItem("4");
        foursimulations.setOnAction(event -> {
            simulationNumber = 4;
        });

        menuButton.getItems().addAll(onesimulation, twosimulations, foursimulations);
        allGrids = new ArrayList<Grid>(simulationNumber);
        return menuButton;

    }

    private PieChart setupChart(Grid myGrid) {

        PieChart myChart = new PieChart();
        //have to read in grid info as a map
        HashMap<String, Integer> SimulationStateMap;
        SimulationStateMap = myGrid.getSimulationMap();

        System.out.println(myGrid.getSimulationName());

        HashMap<Integer, String> myNewHashMap = new HashMap<>();
        for(HashMap.Entry<String, Integer> entry :SimulationStateMap.entrySet()){
            myNewHashMap.put(entry.getValue(), entry.getKey());
        }

        int count;
        for(int state: myNewHashMap.keySet()) {
            count =0;
            for (int row = 0; row < myGrid.getMyCurrentState().length; row++) {
                for (int col = 0; col < myGrid.getMyCurrentState()[0].length; col++) {
                    if (myGrid.getMyCurrentState()[row][col].getState() == state) {
                        count++;
                    }
                }
            }
            myChart.getData().add(new PieChart.Data(myNewHashMap.get(state), count));
        }

        myChart.setLegendSide(Side.RIGHT);
        myChart.setVisible(true);
        myChart.setLayoutX(650);
        myChart.setLayoutY(ScreenHEIGHT - 400);
        myChart.setPrefSize(400,400);
        myChart.setMinSize(400,400);
        //myChart.setTitle("INFO");
        return myChart;
    }


    private Slider makeSlider(){
        Slider mySlider = new Slider(0,20,1);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(5);
        mySlider.setMinorTickCount(1);
        mySlider.prefWidth(500);
        mySlider.setLayoutX(50);
        mySlider.setLayoutY(950);
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
    private Grid setupGrid(String filepath,  Group root, String shapetype, int space_X, int space_Y){
        Grid newGrid = new TriangleGrid(filepath, GridDisplaySize, GRID_POS_X, GRID_POS_Y);

        if(shapetype.equals("triangle")){
           newGrid = new TriangleGrid(filepath, GridDisplaySize, GRID_POS_X + space_X, GRID_POS_Y + space_Y);
        }
        else if(shapetype.equals("rectangle")){
            newGrid = new RectangleGrid(filepath, GridDisplaySize, GRID_POS_X +space_X, GRID_POS_Y + space_Y);
        }
        else{
            newGrid = new HexagonGrid(filepath, GridDisplaySize, GRID_POS_X + space_X, GRID_POS_Y + space_Y);
        }

        return newGrid;
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

        MenuButton chooseShape = selectCellShape(ShapeButtonImage, 50, 200 );

        Button StepButton = makeButton(STEP_TEXT, StepButtonImage, 350 );
        StepButton.setOnMouseClicked((event)->{
            if(filepath.equals("") || shapetype.equals("")){
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
            if(allGrids.size() == 0){
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
            if(allGrids.size() == 0){
                makeInitialize();
            }
            else {
                animation.pause();
            }
        });

        Button InitializeButton = makeButton(INITIALIZE_TEXT, InitializeButtonImage, 800);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath.equals("") || shapetype.equals("")){
                makeAlert();
                System.out.println("this is it");
            }
            else {
                animation.pause();
                // we will have to add for loop here to create multiple grids
                if(allGrids != null){
                    unDisplayAllGrids(root);
                }
                AnimationSpeed = 1;
                animation.setRate(AnimationSpeed);
                count = 0;
                root.getChildren().remove(SimulationName);
                makeallGrids();
                /*setupGrid(filepath, root, shapetype);
                myGrid.initialize();
                myGrid.setInitialGridColors();
                myGrid.display(root);*/
                //myChart = setupChart(myGrid);
                //root.getChildren().add(myChart);
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

        MenuButton numberOfSimulationsButton = selectNumSimulations(MultipleSimulationsButtonImage, 50, 900);

        root.getChildren().addAll(chooseShape, ChangeSpeedOfGame, StepButton, FileUploadButton, PlayButton, PauseButton, InitializeButton,
                 numberOfSimulationsButton);
    }

    private void makeallGrids(){
        for(int a=0; a<simulationNumber; a++) {
            Grid newGrid = setupGrid(filepath, root, shapetype, 400*a, 400*a);
            newGrid.initialize();
            newGrid.setInitialGridColors();
            allGrids.add(newGrid);
            System.out.println(allGrids.size());
            DisplayAllGrids(root);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
