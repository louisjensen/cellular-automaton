package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

public class Grid {

    private Cell[][] myCurrentState;
    private Cell[][] myNextState;
    private Simulation mySimulation;
    private GridPane myGridPane;
    private String myFilePath;
    private int myDisplaySize;
    private int myGridWidth;
    private int myGridHeight;
    private int myCellSize;
    private String SimulationName;
    private Random rand = new Random();
    int randInt;
    private XMLParser xml;

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

    public Grid(String filePath, int displaySize){
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
        calculateCellSize();
        myGridPane = new GridPane();
        initialize();
        mySimulation.setCurrentGrid(myCurrentState);
        mySimulation.setNextGrid(myNextState);
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
                if (!myGridPane.getChildren().contains(currentCell.getImage())) {
                    myGridPane.add(currentCell.getImage(), row, col);
                }
            }
        }
        //moveNexttoCurrent();
    }

    private void moveNexttoCurrent() {
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
    private void calculateCellSize(){
        myCellSize = myDisplaySize/myGridWidth;
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
        moveNexttoCurrent();
    }

    public void initialize(){
        List<String> states = xml.getStates();
        List<Double> proportionStates = xml.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        int currentTotal = 0;
        int percentage;
        for (int i = 0; i < proportionStates.size(); i++){
            percentage = (currentTotal + (int) (proportionStates.get(i) * 100));
            percentageStates.add(percentage);
            currentTotal = percentage;
        }
        for (int i = 0; i < myGridWidth; i++){
            for (int j = 0; j < myGridHeight; j++){
                randInt = rand.nextInt(100) + 1;
                for (int k = 0; k < percentageStates.size(); k++){
                    if(randInt <= percentageStates.get(k)) {
                        myCurrentState[i][j] = new Cell(i, j, myCellSize, stateLookupTable.get(states.get(k)));
                        myNextState[i][j] = new Cell(i, j, myCellSize, -1);
                        break;
                    }
                }
            }
        }

    }

}
