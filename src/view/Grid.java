package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

public class Grid {

    private Cell[][] myCurrentState;
    private Cell[][] myNextState;
    private Simulation mySimulation;
    private Cell myCell;
    private Cell currentCell;
    private GridPane myGridPane;
    private String myFilePath;
    private int myDisplaySize;
    private int myGridWidth;
    private int myGridHeight;
    private int myCellSizeX;
    private int myCellSizeY;
    private String SimulationName;
    private Random rand = new Random();
    private int d;
    int randInt;
    private XMLParser xml;
    List<String> states = xml.getStates();
    List<Double> proportionStates = xml.getStateProportions();
    List<Integer> percentageStates = new ArrayList<>();

    private Simulation getSimulation(String sim, HashMap<String, Double> map){
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
            return new PredatorPrey(map);
        }
        return null;
    }

    private Cell getSpecificCell(String simulationName, HashMap<String, Double> map, String shape){
        if (simulationName.equals("GameOfLife")) {

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

    }

    public Grid(String filePath, int displaySize, String myShapeType){
        myFilePath = filePath;
        xml = new XMLParser(myFilePath);

        SimulationName = xml.getSimulationType();
        //construct grid from XML File
        myDisplaySize = displaySize;
        myGridWidth = xml.getGridX();
        myGridHeight = xml.getGridY();
        myCurrentState = new Cell[myGridWidth][myGridHeight];// for testing
        myNextState = new Cell[myGridWidth][myGridHeight];

        mySimulation = getSimulation(xml.getSimulationType(), xml.getRandomInfo());
        myCell = getSpecificCell(xml.getSimulationType(), xml.getRandomInfo(), myShapeType);


        initialize(myShapeType);
        calculateCellSizeX();
        calculateCellSizeY();
        mySimulation.setCurrentGrid(myCurrentState);
        mySimulation.setNextGrid(myNextState);

        //how are we gonna choose specific cell type based on the simulation

    }



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

    /**
     * returns myGridPane
     * @return GridPane that contains all of the cells
     */
    public GridPane getGridPane() {
        setGridPane();
        return myGridPane;
    }

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

    public void setGrid(){
        Cell currentCell;
        Color color;
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();
        for
    }

    public void moveNexttoCurrent() {
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                myCurrentState[row][col].setState(myNextState[row][col].getState());
                myNextState[row][col].setState(-1);
            }
        }
    }

    public boolean checkGameEnding(){
        for(int i=0; i<myGridWidth; i++){
            for(int j=0; j<myGridHeight; j++){
                if(myCurrentState[i][j].getState() != myNextState[i][j].getState()){
                    return false;
                }
            }
        }
        return true;
    }
    // calculates the size of a cell based on how big the display is and the number of cells to fit
    private void calculateCellSizeX(){
        float temp = (float) myDisplaySize/myGridWidth;
        myCellSizeX = (int) temp;
    }

    private void calculateCellSizeY(){
        float temp = (float) myDisplaySize/myGridHeight;
        myCellSizeY = (int) temp;
    }


    public Cell[][] getMyCurrentState(){
        return myCurrentState;
    }

    public Cell[][] getMyNextState() {
        return myNextState;
    }

    public String getSimulationName(){
        return SimulationName;
    }


    public void updateGrid(){
        mySimulation.update();
    }

    public void initializeRectangleGrid(){
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        setGridProportion();
        for (int i = 0; i < myGridWidth; i++){
            for (int j = 0; j < myGridHeight; j++){
                randInt = rand.nextInt(100) + 1;
                for (int k = 0; k < percentageStates.size(); k++){
                    if(randInt <= percentageStates.get(k)) {
                        myCurrentState[i][j] = new Cell(i, j, myCellSizeX, myCellSizeY, stateLookupTable.get(states.get(k)));
                        myNextState[i][j] = new Cell(i, j, myCellSizeX, myCellSizeY,-1);
                        break;
                    }
                }
            }
        }

    }

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


    private void setGridProportion(){
        int currentTotal = 0;
        int percentage;
        for (int i = 0; i < proportionStates.size(); i++) {
            percentage = (currentTotal + (int) (proportionStates.get(i) * 100));
            percentageStates.add(percentage);
            currentTotal = percentage;
        }
    }
}
