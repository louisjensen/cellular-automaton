package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.io.File;

public class Grid{ // make abstract later

    private Cell[][] myCurrentState;
    private Cell[][] myNextState;
    private Simulation mySimulation;
    private GridPane myGridPane;
    private File myFile;
    private int myDisplaySize;
    private int myGridWidth;
    private int myGridHeight;
    private int myCellSize;

    public Grid(File file, int displaySize){
        //construct grid from XML File
        myFile = file;
        myDisplaySize = displaySize;
        myGridWidth = 50; // for testing
        myGridHeight = 50; // for testing
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
        initializeGrid(); // for testing
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
    }

}
