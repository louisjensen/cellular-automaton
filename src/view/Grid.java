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

public class Grid { // make abstract later

    private Cell[][] myCurrentState;
    private Cell[][] myNextState;
    private Simulation mySimulation;
    private GridPane myGridPane;
    private String myFilePath;
    private int myDisplaySize;
    private int myGridWidth;
    private int myGridHeight;
    private int myCellSize;
    private Random rand = new Random();
    int randInt;
    private XMLParser xml;

    final HashMap<String, Simulation> simulationLookupTable = new HashMap<String, Simulation>(){{
            put("GameOfLife", new GameOfLife());
            //put("segregation", new Simulation);
            put("SpreadingOfFire", new SpreadingOfFire());
        }};

    public Grid(String filePath, int displaySize){
        myFilePath = filePath;
        xml = new XMLParser(myFilePath);

        //construct grid from XML File
        myDisplaySize = displaySize;
        mySimulation = simulationLookupTable.get( xml.getSimulationType());
        myGridWidth = xml.getGridX();
        //myGridWidth = 20;// for testing
        myGridHeight = xml.getGridY();
     //   myGridHeight = 20;// for testing
        myCurrentState = new Cell[myGridWidth][myGridHeight];// for testing
        calculateCellSize();
        myGridPane = new GridPane();
    }
    public Grid(int displaySize){ // FOR TESTING PURPOSES ONLY. DOES NOT TAKE IN A FILE PARAMETER BC XMLParser IS WIP
        //construct grid from XML File
        myDisplaySize = displaySize;
        myGridWidth = 50; // for testing
        myGridHeight = 50; // for testing
        myCurrentState = new Cell[myGridWidth][myGridHeight];// for testing
        calculateCellSize();
        myGridPane = new GridPane();
    }

    /**
     * returns myGridPane
     * @return GridPane that contains all of the cells
     */
    public GridPane getGridPane() { // will depend on what type of grid it is
        setGridPane();
        return myGridPane;
    }

    // configure myGridPane based on the cells in myCurrentState
    private void setGridPane(){
        Cell currentCell;
        Color color;
        initialize(); // for testing
        //myGridPane.setMinSize(myDisplaySize, myDisplaySize);
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                currentCell = myCurrentState[row][col];
                color = stateToColorMap.get(currentCell.getState());
                currentCell.setColor(color);
                myGridPane.add(currentCell.getImage(), row, col);
            }
        }
    }

    private void initializeGrid(){ // FOR TESTING PURPOSES ONLY
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                myCurrentState[row][col] = new Cell(row, col, myCellSize, 1);
            }
        }
    }

    // calculates the size of a cell based on how big the display is and the number of cells to fit
    private void calculateCellSize(){
        myCellSize = myDisplaySize/myGridWidth;
    }





    public void updateGrid(){
        Cell myCell;
        ArrayList<Cell> neighbors;
        for (int i = 0; i < myGridWidth; i++){
            for (int j = 0; j < myGridHeight; j++){
                myCell = new Cell(i, j, myCellSize, myCurrentState[i][j].getState());
                neighbors = mySimulation.getNeighbors(myCell, myCurrentState);
                myNextState[i][j] = mySimulation.getNextStateOfCell(myCell, neighbors);
            }
        }
    }

    public void initialize(){
        //reread file and create a new grid
        HashMap<String, Double> map = xml.getMap();
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<Double> proportionStates = new ArrayList<>();
        List<Integer> percentageStates = new ArrayList<>();
        int currentTotal = 0;
        int percentage;
        for (String key : map.keySet()){
            proportionStates.add(map.get(key));
        }
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
                        myCurrentState[i][j] = new Cell(i, j, myCellSize, k);
                        break;
                    }
                }
            }
        }

        /*
        double propState1 = 0.1;
        double propState2 = 0.1;
        double propState3 = 0.8;
        int numState1 = (int) (propState1 * 100);
        int numState2 = (int) (propState2 * 100) + numState1;
        int numState3 = (int) (propState3 * 100) + numState2;
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                    randInt = rand.nextInt(100) + 1;
                    System.out.println(randInt);
                    if(randInt < numState1) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 1);
                    } else if(randInt < numState2) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 2);
                    } else {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 3);
                    }
            }
        }
        */
    }

}
