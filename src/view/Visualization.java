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
    private String StepButtonImage = "step.png";
    private String ShapeButtonImage = "shapes.png";
    private final String COUNT_TEXT = "Rounds: ";
    private final String UPLOAD_TEXT = "UploadFile";
    private final String STEP_TEXT = "Step";
    private final String PLAY_TEXT = "Play";
    private final String PAUSE_TEXT = "Pause";
    private final String MultipleSimulationsButtonImage = "MultipleButton.png";
    private final String INITIALIZE_TEXT = "Initialize";
    private final String DEFAULT_FONT = "Times New Roman";
    private final String BoundaryButtonImage = "boundaryButton.png";

    //Change this number to scale GUI
    private static final int ScreenSIZE = 580;

    private static final int GridDisplaySize = ScreenSIZE/2;
    private static final int ScreenWIDTH = ScreenSIZE*2;
    private static final int ScreenHEIGHT = ScreenSIZE*5/4;
    private static final int fontsize2 = ScreenSIZE/12;
    private static final int fontsize1 = ScreenSIZE/20;
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final static int BUTTON_SIZE = ScreenSIZE / 25;
    private final static int BUTTON_POS_X = ScreenSIZE/26;
    private final static int SimulationTitle_POS_X = ScreenWIDTH *2/5;
    private final static int SimulationTitle_POS_Y = ScreenSIZE/12;
    private final static int GRID_POS_X = ScreenSIZE /4;
    private final static int GRID_POS_Y = ScreenSIZE/8;
    private final static int PieChartSize = ScreenSIZE/5;
    private final static int Chart_Position_x = ScreenSIZE*19/100;
    private double AnimationSpeed;
    private Text showCount;
    private Scene myScene;
    private Grid myGrid;
    private String SimulationTitle;
    private Text SimulationName;
    private String filepath = "";
    private Group root;
    private Timeline animation;
    private int count;
    private int simulationNumber;
    private String shapetype = "";
    private String edgeType = "";
    private XMLParser parser;
    private TextField value;
    private PieChart myChart;
    private ArrayList<Grid> allGrids;
    private ArrayList<PieChart> allCharts;
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
            unDisplayAllGrids();
            removeAllCharts();
            UpdateAllGrids();
            checkgameendingforAllGrids();
            moveNexttoCurrentAllGrids();
            count ++;
            System.out.println("aha");
            DisplayAllGrids(root);
            makeChartforEachGrid(allGrids);

        }
        else {
            removeAllCharts();
            count ++;
            DisplayAllGrids(root);
            System.out.println("aha1");
            makeChartforEachGrid(allGrids);
        }
        showCount.setText(COUNT_TEXT + count);
        showCount.setFill(Color.WHITE);
    }

    private Scene setupVisualization(Stage stage) {
        root = new Group();
        Scene myScene = new Scene(root,ScreenWIDTH, ScreenHEIGHT, Color.GRAY);
        allButtons(stage);
        makeTextsLabels();
        myScene.getStylesheets().add("default.css");
        return myScene;
    }

    //Below here should all be move to a separate class
    //----------------------------------------------------------------------------------------------

    private void removeAllCharts(){
        for(int a=0; a<allCharts.size(); a++){
            root.getChildren().remove(allCharts.get(a));
        }
    }

    private void makeChartforEachGrid(ArrayList<Grid> allGrids){
        for(int a=0; a<allGrids.size(); a++){
            PieChart myChart = setupChart(allGrids.get(a), a+1, (Chart_Position_x) *a);
            root.getChildren().add(myChart);
            allCharts.add(myChart);
            System.out.println("aa");
        }
    }

    private void checkgameendingforAllGrids(){
        for(int a=0; a<allGrids.size(); a++){
            if(allGrids.get(a).checkGameEnding()) {
                animation.stop();
                makeGameEnding();
            }
        }
    }
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

    private void unDisplayAllGrids(){
        for(int a=0; a<allGrids.size(); a++){
            allGrids.get(a).unDisplay(root);
            root.getChildren().remove(allGrids.get(a));
        }
    }

    private void menubuttonimagaereader(String file, MenuButton menuButton, int x, int y){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(BUTTON_SIZE);
        iv.setFitWidth(BUTTON_SIZE);
        menuButton.setGraphic(iv);
        menuButton.setLayoutX(x);
        menuButton.setLayoutY(y);
    }

    private MenuButton selectEdgeTypes(String file, int x, int y){
        MenuButton menuButton = new MenuButton("Choose Boundary Type");
        menubuttonimagaereader(file, menuButton, x, y);
        MenuItem regular = new MenuItem("regular");
        regular.setOnAction(event -> {
            edgeType = "regular";
        });
        MenuItem toroidal = new MenuItem("toroid");
        toroidal.setOnAction(event -> {
            edgeType = "toroid";
        });

        menuButton.getItems().addAll(regular, toroidal);
        return menuButton;
    }

    private MenuButton selectCellShape(String file, int x, int y){
        MenuButton menuButton = new MenuButton("Choose Shape");
        menubuttonimagaereader(file, menuButton, x, y);
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
        MenuButton menuButton = new MenuButton("Number of \n Simulations");
        menubuttonimagaereader(file, menuButton, x, y);
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
        allCharts = new ArrayList<>(simulationNumber);
        return menuButton;

    }

    private PieChart setupChart(Grid myGrid, int Gridnumber, int Position_X) {
        System.out.println("This line ran0");
        PieChart myChart = new PieChart();
        HashMap<String, Integer> SimulationStateMap;
        SimulationStateMap = myGrid.getSimulationMap();
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
        myChart.setVisible(true);
        myChart.setLayoutX(Position_X);
        myChart.setLayoutY(ScreenHEIGHT * 31 / 40);
        System.out.println("This line ran");
        myChart.setPrefSize(PieChartSize,PieChartSize);
        myChart.setMinSize(PieChartSize, PieChartSize);
        myChart.setTitle("Grid " + Gridnumber);
        myChart.setStyle("-fx-font-size: " + fontsize1/2 + "px;");
        return myChart;
    }


    private Slider makeSlider(){
        Slider mySlider = new Slider(0,20,1);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(5);
        mySlider.setMinorTickCount(1);
        mySlider.prefWidth(ScreenSIZE/2);
        mySlider.setLayoutX(ScreenSIZE / 26);
        mySlider.setLayoutY(ScreenSIZE - (ScreenSIZE/6));
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
        Grid newGrid;

        if(shapetype.equals("triangle")){
           newGrid = new TriangleGrid(filepath, GridDisplaySize, GRID_POS_X + space_X,  GRID_POS_Y + space_Y, edgeType);
        }
        else if(shapetype.equals("rectangle")){
            newGrid = new RectangleGrid(filepath, GridDisplaySize, GRID_POS_X +space_X,  GRID_POS_Y +space_Y, edgeType);
        }
        else{
            newGrid = new HexagonGrid(filepath, GridDisplaySize, GRID_POS_X + space_X, GRID_POS_Y +space_Y, edgeType);
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
        Button FileUploadButton = makeButton(UPLOAD_TEXT, FileUploadButtonImage, ScreenSIZE/3);
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

        MenuButton chooseShape = selectCellShape(ShapeButtonImage, ScreenSIZE/26, ScreenSIZE/12);

        Button StepButton = makeButton(STEP_TEXT, StepButtonImage, ScreenSIZE/4);
        StepButton.setOnMouseClicked((event)->{
            if(filepath.equals("") || shapetype.equals("")){
                makeAlert();
            }
            unDisplayAllGrids();
            UpdateAllGrids();
            checkgameendingforAllGrids();
            moveNexttoCurrentAllGrids();
            count ++;
            DisplayAllGrids(root);
        });

        Button PlayButton = makeButton(PLAY_TEXT, PlayButtonImage,  ScreenSIZE * 33 / 80);
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

        Button PauseButton = makeButton(PAUSE_TEXT, PauseButtonImage,  ScreenSIZE/2);
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

        Button InitializeButton = makeButton(INITIALIZE_TEXT, InitializeButtonImage, ScreenSIZE * 3 / 5);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath.equals("") || shapetype.equals("") || simulationNumber ==0){
                makeAlert();
            }
            else {
                if(allCharts != null) {
                    removeAllCharts();
                }
                root.getChildren().remove(SimulationName);
                animation.pause();
                if(allGrids.size() !=0){
                    unDisplayAllGrids();
                    allGrids = new ArrayList<Grid>(simulationNumber);
                }
                AnimationSpeed = 1;
                animation.setRate(AnimationSpeed);
                count = 0;
                makeallGrids();
                SimulationName = MakeText(SimulationTitle,  SimulationTitle_POS_X, SimulationTitle_POS_Y, fontsize2);
                SimulationName.setFill(Color.WHITE);
                root.getChildren().add(SimulationName);
                System.out.println(allGrids.size());
                makeChartforEachGrid(allGrids);
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

        MenuButton numberOfSimulationsButton = selectNumSimulations(MultipleSimulationsButtonImage, ScreenSIZE / 26, ScreenSIZE*7/10);
        MenuButton edgeTypeButton = selectEdgeTypes(BoundaryButtonImage, ScreenSIZE / 26,ScreenSIZE/6);
        root.getChildren().addAll(chooseShape, ChangeSpeedOfGame, StepButton, FileUploadButton, PlayButton, PauseButton, InitializeButton,
                 numberOfSimulationsButton, edgeTypeButton);
    }

    private void makeTextsLabels(){
        showCount = MakeText(COUNT_TEXT + count, ScreenWIDTH*5/6,ScreenSIZE/16, fontsize1);
        showCount.setFill(Color.WHITE);
        root.getChildren().addAll(showCount);

    }

    private void makeallGrids(){
        for(int a=0; a<simulationNumber; a++) {
            Grid newGrid = setupGrid(filepath, root, shapetype, ScreenSIZE/2, 0);
            if(simulationNumber == 2) {
                newGrid = setupGrid(filepath, root, shapetype, GridDisplaySize * a + ScreenSIZE/2,0);
            }
            if(simulationNumber ==4) {
                if(a<2){
                    newGrid = setupGrid(filepath, root, shapetype, (GridDisplaySize + ScreenSIZE/26)* a + ScreenSIZE/2, 0);
                    System.out.println("aa1");
                }
                else{
                    newGrid = setupGrid(filepath, root, shapetype, (GridDisplaySize + ScreenSIZE/26)* (a-2) + ScreenSIZE/2, ScreenSIZE/26 + GridDisplaySize);
                    System.out.println();
                }
            }
            SimulationTitle = newGrid.getSimulationName();
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
