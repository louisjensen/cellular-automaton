package view;

import Grid.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.chart.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Visualization{
    private String Title = "Cell Automaton";
    private String FileUploadButtonImage = "FileUploadButton.png";
    private String PlayButtonImage = "PlayButton.png";
    private String PauseButtonImage = "PauseButton.png";
    private String InitializeButtonImage = "InitializeButton.png";
    private String StepButtonImage = "step.png";
    private String ShapeButtonImage = "shapes.png";
    private final String BoundaryButtonImage = "boundaryButton.png";
    private final String MultipleSimulationsButtonImage = "MultipleButton.png";
    private final String cssfile = "default.css";
    private final String COUNT_TEXT = "Rounds: ";
    private final String UPLOAD_TEXT = "UploadFile";
    private final String STEP_TEXT = "Step";
    private final String PLAY_TEXT = "Play";
    private final String PAUSE_TEXT = "Pause";
    private final String INITIALIZE_TEXT = "Initialize";

    //Change this number to scale GUI
    private static final int ScreenSIZE = 580;
    private static final int GridDisplaySize = ScreenSIZE/2;
    private static final int ScreenWIDTH = ScreenSIZE*2;
    private static final int ScreenHEIGHT = ScreenSIZE*5/4;
    private static final int fontsize2 = ScreenSIZE/12;
    private static final int fontsize1 = ScreenSIZE/20;
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final static int SimulationTitle_POS_X = ScreenWIDTH *2/5;
    private final static int SimulationTitle_POS_Y = ScreenSIZE/12;
    private final static int Chart_Position_x = ScreenSIZE*19/100;
    private double AnimationSpeed;
    private Text showCount;
    private Scene myScene;
    private String SimulationTitle;
    private Text SimulationName;
    private String filepath = "";
    private Group root;
    private Timeline animation;
    private int count;
    private ArrayList<Grid> allGrids;
    private ArrayList<PieChart> allCharts;
    //private ResourceBundle myResources = ResourceBundle.getBundle("textForGui.properties");
    private MakeButton buttonmaker = new MakeButton();
    private MakeSlider slidermaker = new MakeSlider();
    private Slider ChangeSpeedOfGame = slidermaker.makeSlider();
    private FileChooser fileChooser = new FileChooser();
    private MakeText textmaker = new MakeText();
    private MakeAlert alertmaker = new MakeAlert();
    private ChooseGrid chooseGrid = new ChooseGrid();

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
            count ++;
            DisplayAllGrids(root);
            moveNexttoCurrentAllGrids();
            makeChartforEachGrid(allGrids);

        }
        else {
            removeAllCharts();
            count ++;
            DisplayAllGrids(root);
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
        myScene.getStylesheets().add(cssfile);
        return myScene;
    }

    private void makeTextsLabels(){
        MakeText textmaker = new MakeText();
        showCount = textmaker.MakeText(COUNT_TEXT + count, ScreenWIDTH*5/6,ScreenSIZE/16, fontsize1);
        showCount.setFill(Color.WHITE);
        root.getChildren().addAll(showCount);

    }

    private void removeAllCharts(){
        for(int a=0; a<allCharts.size(); a++){
            root.getChildren().remove(allCharts.get(a));
        }
    }

    private void makeChartforEachGrid(ArrayList<Grid> allGrids){
        for(int a=0; a<allGrids.size(); a++){
            MakeChart chartmaker = new MakeChart();
            PieChart myChart = chartmaker.setupChart(allGrids.get(a), a+1, (Chart_Position_x) *a);
            root.getChildren().add(myChart);
            allCharts.add(myChart);
        }
    }

    private void checkgameendingforAllGrids(){
        for(int a=0; a<allGrids.size(); a++){
            if(allGrids.get(a).checkGameEnding()) {
                animation.stop();
                alertmaker.makeGameEnding();
            }
        }
    }

    private void makeAllGrids(){
        int simulationNumber = buttonmaker.getSimulationNumber();
        String shapetype = buttonmaker.getSHAPE_TEXT();
        for(int a=0; a<simulationNumber; a++) {
            Grid newGrid = chooseGrid.setupGrid(filepath, shapetype, ScreenSIZE/2, 0, buttonmaker.getEdgeType());
            if(simulationNumber == 2) {
                newGrid = chooseGrid.setupGrid(filepath,  shapetype, GridDisplaySize * a + ScreenSIZE/2,0,  buttonmaker.getEdgeType());
            }
            if(simulationNumber ==4) {
                if(a<2){
                    newGrid = chooseGrid.setupGrid(filepath,shapetype, (GridDisplaySize + ScreenSIZE/26)* a + ScreenSIZE/2, 0,  buttonmaker.getEdgeType());
                }
                else{
                    newGrid = chooseGrid.setupGrid(filepath,shapetype, (GridDisplaySize + ScreenSIZE/26)* (a-2) + ScreenSIZE/2, ScreenSIZE/26 + GridDisplaySize, buttonmaker.getEdgeType());
                }
            }
            SimulationTitle = newGrid.getSimulationName();
            newGrid.initialize();
            newGrid.setInitialGridColors();
            allGrids.add(newGrid);
            DisplayAllGrids(root);
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

    private void allButtons(Stage stage){

        Button FileUploadButton = buttonmaker.makeButton(UPLOAD_TEXT, FileUploadButtonImage, ScreenSIZE/12);
        BorderPane.setAlignment(FileUploadButton, Pos.TOP_LEFT);
        FileUploadButton.setOnMouseClicked(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                filepath = selectedFile.toString();
            }
        });

        MenuButton chooseShape = buttonmaker.selectCellShape(ShapeButtonImage, ScreenSIZE/26, ScreenSIZE/6);

        Button StepButton = buttonmaker.makeButton(STEP_TEXT, StepButtonImage, ScreenSIZE/2);
        StepButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                alertmaker.makeAlert();
            }
            unDisplayAllGrids();
            UpdateAllGrids();
            checkgameendingforAllGrids();
            moveNexttoCurrentAllGrids();
            count ++;
            DisplayAllGrids(root);
        });

        Button PlayButton = buttonmaker.makeButton(PLAY_TEXT, PlayButtonImage,  ScreenSIZE * 3/5);
        PlayButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                alertmaker.makeAlert();
            }
            if(allGrids.size() == 0){
                alertmaker.makeInitialize();
            }
            else {
                animation.play();
            }

        });

        Button PauseButton =buttonmaker.makeButton(PAUSE_TEXT, PauseButtonImage,  ScreenSIZE*5/6);
        PauseButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                alertmaker.makeAlert();
            }
            if(allGrids.size() == 0){
                alertmaker.makeInitialize();
            }
            else {
                animation.pause();
            }
        });

        Button InitializeButton = buttonmaker.makeButton(INITIALIZE_TEXT, InitializeButtonImage, ScreenSIZE * 33 / 80);
        InitializeButton.setOnMouseClicked((event)->{
            if(filepath.equals("")){
                alertmaker.makeAlert();
            }
            else {
                if(allCharts != null) {
                    removeAllCharts();
                }
                root.getChildren().remove(SimulationName);
                animation.pause();
                if(allGrids.size() !=0){
                    unDisplayAllGrids();
                    allGrids = new ArrayList<Grid>(buttonmaker.getSimulationNumber());
                }
                AnimationSpeed = 1;
                animation.setRate(AnimationSpeed);
                count = 0;
                makeAllGrids();
                SimulationName = textmaker.MakeText(SimulationTitle,  SimulationTitle_POS_X, SimulationTitle_POS_Y, fontsize2);
                SimulationName.setFill(Color.WHITE);
                root.getChildren().add(SimulationName);
                makeChartforEachGrid(allGrids);
            }
        });

        ChangeSpeedOfGame.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                AnimationSpeed = new_val.doubleValue();
                animation.setRate(AnimationSpeed);
            }
        });

        MenuButton numberOfSimulationsButton = buttonmaker.selectNumSimulations(MultipleSimulationsButtonImage, ScreenSIZE / 26, ScreenSIZE/3);
        allGrids = new ArrayList<Grid>(buttonmaker.getSimulationNumber());
        allCharts = new ArrayList<>(buttonmaker.getSimulationNumber());
        MenuButton edgeTypeButton = buttonmaker.selectEdgeTypes(BoundaryButtonImage, ScreenSIZE / 26,ScreenSIZE/4);
        root.getChildren().addAll(chooseShape, ChangeSpeedOfGame, StepButton, FileUploadButton, PlayButton, PauseButton, InitializeButton,
                 numberOfSimulationsButton, edgeTypeButton);
    }

}
