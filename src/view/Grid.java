package view;

import javafx.scene.shape.Polygon;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.HashMap;
import javafx.scene.Group;

import java.util.Random;
import java.util.List;

public abstract class Grid {

    public Cell[][] myCurrentState;
    public Cell[][] myNextState;
    public Simulation mySimulation;
    public int myDisplaySize;
    public XMLParser myXML;
    public Cell myCell;
    //private Cell currentCell;
    //private GridPane myGridPane;
    //private String myFilePath;
    //private int myDisplaySize;
    //private int myGridWidth;
    //private int myGridHeight;
    //private int myCellSizeX;
    //private int myCellSizeY;
    //private String SimulationName;
    //private Random rand = new Random();
    //private int d;
    //int randInt;

    //List<String> states = xml.getStates();
    //List<Double> proportionStates = xml.getStateProportions();
    //List<Integer> percentageStates = new ArrayList<>();




    public Grid(String filePath, int displaySize){
        myXML = new XMLParser(filePath);
        //mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        myDisplaySize = displaySize;

        //construct grid from XML File
        //myDisplaySize = displaySize;
        //myGridWidth = xml.getGridX();
        //myGridHeight = xml.getGridY();
        //myCurrentState = new Cell[myGridWidth][myGridHeight];// for testing
        //myNextState = new Cell[myGridWidth][myGridHeight];

        //mySimulation.setCurrentGrid(myCurrentState);
        //mySimulation.setNextGrid(myNextState);

        //myCell = getSpecificCell(myXML.getSimulationType(), myXML.getRandomInfo());

        //calculateCellSizeX();
        //calculateCellSizeY();


        //how are we gonna choose specific cell type based on the simulation

    }

    public abstract void initialize();

    private void getCellBasedOnSimulation(){

    }


    public Simulation getSimulation(String sim, HashMap<String, Double> map){
        if (sim.equals("GameOfLife")){
            return new GameOfLife(myCurrentState, myNextState);
        }
        if (sim.equals("Percolation")){
            return new Percolation(myCurrentState, myNextState);
        }
        if (sim.equals("SpreadingOfFire")){
            return new SpreadingOfFire(map, myCurrentState, myNextState);
        }
        if(sim.equals("Segregation")){
            return new Segregation(map);
        }
        if(sim.equals("PredatorPrey")){
            //return new PredatorPrey(map);
        }
        return null;
    }

    public Cell getSpecificCell(Polygon shape){
        if (myXML.getSimulationType().equals("GameOfLife")) {
            //default constructor;
            return new GameOfLifeCell(shape);
        }
        return new GameOfLifeCell(shape);
    }


       /* if (simulationName.equals("Percolation")){
            return new Percolation(myCurrentState, myNextState);
        }
        if (simulationName.equals("SpreadingOfFire")){
            return new SpreadingOfFire(map, myCurrentState, myNextState);
        }
        if(simulationName.equals("Segregation")){
            return new Segregation(map);
        }
        if(simulationName.equals("PredatorPrey")){
            return new PredatorPrey(map);
        }*/


    /*
    public void initialize(String myShapeType){
        if(myShapeType.equals("triangle")){
            initializeTriangleGrid();
        }
        if(myShapeType.equals("Rectangle")){
            initializeRectangleGrid();
        }
        if(myShapeType.equals("hexagon")){
            initializeHexagonGrid();
        }
    }
    */


/*
    // configure myGridPane based on the cells in myCurrentState
    public void setGridPane(){
        Cell currentCell;
        Color color;
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        for (int row = 0; row < myCurrentState.length; row ++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                currentCell = myCurrentState[row][col];
                color = stateToColorMap.get(currentCell.getState());
                currentCell.setColor(color);
                currentCell.getImage().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        System.out.println("Mouse pressed");
                        //error
                        //currentCell
                    }
                });
                if (!myGridPane.getChildren().contains(currentCell.getImage())) {
                    myGridPane.add(currentCell.getImage(), row, col);

                }
            }
        }
        //myGridPane.setOnMouseClicked();
    }
    */

/*
    public void setGrid(){
        Cell currentCell;
        Color color;
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();
        for
    }
*/

    public void moveNexttoCurrent() {
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                myCurrentState[i][j].setState(myNextState[i][j].getState());
                myNextState[i][j].setState(-1);
            }
        }
    }

    public boolean checkGameEnding(){
        for(int i=0; i<myCurrentState.length; i++){
            for(int j=0; j<myCurrentState[0].length; j++){
                if(myCurrentState[i][j].getState() != myNextState[i][j].getState()){
                    return false;
                }
            }
        }
        return true;
    }


    public Cell[][] getMyCurrentState(){
        return myCurrentState;
    }

    public Cell[][] getMyNextState() {
        return myNextState;
    }



    public void updateGrid(){
        mySimulation.update();
    }

    public String getSimulationName(){
        return myXML.getSimulationType();
    }

    public void display( Group root){
        Cell current;
        for (int i = 0; i < myCurrentState.length; i ++){
            for (int j = 0; j < myCurrentState[0].length; j ++){
                current = myCurrentState[i][j];
                if (!root.getChildren().contains(current.getShape()) && current.getState() != -2){
                    root.getChildren().add(current.getShape());
                }
            }
        }
    }

    public void unDisplay(Group root){
        Cell current;
        for (int i = 0; i < myCurrentState.length; i ++){
            for (int j = 0; j < myCurrentState[0].length; j ++){
                current = myCurrentState[i][j];
                if (root.getChildren().contains(current.getShape())){
                    root.getChildren().remove(current.getShape());
                }
            }
        }
    }

/*
    public void initializeTriangleGrid() {
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        setGridProportion();
        int numBottomCells;
        int numRows = numBottomCells / 2;
        Cell[numRows][numBottomCells] TrinagleCellGrid;
        int middleIndex = numRows / 2;
        for (int row = 0; row < myCell.; row++) {
            for(int col=0; col<T)

        }

    }


    public void initializeHexagonGrid() {
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        setGridProportion();

    }

*/
    public void setInitialGridColors(){
        List<Double> proportionStates = myXML.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<String> states = myXML.getStates();
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        Random rand = new Random();
        Cell current;
        int randInt;
        int currentTotal = 0;
        int percentage;
        for (int i = 0; i < proportionStates.size(); i++) {
            percentage = (currentTotal + (int) (proportionStates.get(i) * 100));
            percentageStates.add(percentage);
            currentTotal = percentage;
        }



        for (int i = 0; i < myCurrentState.length; i++){
            for (int j = 0; j < myCurrentState[0].length; j++) {
                if (myCurrentState[i][j].getState() != -2){
                    randInt = rand.nextInt(100) + 1;
                    for (int k = 0; k < percentageStates.size(); k++) {
                        if (randInt <= percentageStates.get(k)) {
                            current = myCurrentState[i][j];
                            current.setState(stateLookupTable.get(states.get(k)));
                            current.setColor(stateToColorMap.get(current.getState()));
                            break;
                        }
                    }
                }
            }
        }

    }





}
