package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

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

    final HashMap<String, Simulation> simulationLookupTable = new HashMap<String, Simulation>(){{
        //put("gameOfLife", new Simulation;
        //put("segregation", new Simulation);
    }};

    public Grid(String filePath, int displaySize){
        myFilePath = filePath;
        XMLParser xml = new XMLParser(myFilePath);

        //construct grid from XML File
        myDisplaySize = displaySize;
        //mySimulation = simulationLookupTable.get( xml.getSimulationType());
        myGridWidth = xml.getGridX(); // for testing
        myGridHeight = xml.getGridY(); // for testing
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
        initialize(); // for testing
        //myGridPane.setMinSize(myDisplaySize, myDisplaySize);

        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                currentCell = myCurrentState[row][col];
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
                //myCell = new Cell(i, j, myCurrentState[i][j].getState());
                //neighbors = mySimulation.getNeighbors(myCell, myCurrentState);
                //myNextState[i][j] = mySimulation.getNextStateOfCell(myCell, neighbors);
            }
        }
    }

    public void initialize(){
        //reread file and create a new grid

        double propState0 = 0.4;
        double propState1 = 0.3;
        double propState2 = 0.3;
        int mapArea = myGridHeight * myGridWidth;
        int numState0 = (int) (mapArea * propState0);
        int numState1 = (int) (mapArea * propState1);
        int numState2 = (int) (mapArea * propState2);
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                while(myCurrentState[row][col] != null){ //while cell hasnt been filled
                    int n = rand.nextInt(2);
                    if(n==0 && numState0>0) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 0);
                        numState0--;
                    }
                    if(n==1 && numState1>0) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 1);
                        numState1--;
                    }
                    if(n==2 && numState2>0) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 2);
                        numState2--;
                    }
                }
            }
        }

    }

}
