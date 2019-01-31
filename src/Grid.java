import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import java.io.File;

public abstract class Grid{

    Cell[][] myCurrentState;
    Cell[][] myNextState;
    Simulation mySimulation;
    GridPane myGridpane;
    File myFile;
    int myDisplaySize;
    int gridWidth;
    int gridHeight;

    public Grid(File file, int displaySize){
        //construct grid from XML File
        myFile = file;
        myDisplaySize = displaySize;
        myGridpane = new GridPane();



    }

    public  GridPane getIV() { // will depend on what type of grid it is
        int cellRow;
        int cellCol;
        Rectangle cellRectangle;
        Cell currentCell;
        myGridpane.setMinSize(myDisplaySize, myDisplaySize);
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                currentCell = myCurrentState[row][col];
                
            }
        }
    }



    public void updateGrid(){
        Cell myCell;
        ArrayList<Cell> neighbors;
        for (int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                myCell = new Cell(i, j, myCurrentState[i][j].getState());
                neighbors = mySimulation.getNeighbors(myCell, myCurrentState);
                myNextState[i][j] = mySimulation.getNextStateOfCell(myCell, neighbors);
            }
        }
    }

    public void initialize(){
        //reread file and create a new grid
    }

}
