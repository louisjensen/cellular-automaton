package view;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.io.File;

public abstract class Grid{

    Cell[][] myCurrentState;
    Cell[][] myNextState;
    Simulation mySimulation;
    //ImageView myImageView;
    GridPane myGridpane;
    File myFile;
    int myDisplaySize;
    int gridWidth;
    int gridHeight;

    public Grid(File file, int displaySize){
        //construct grid from XML File
        myGridpane = new GridPane();



    }

    public GridPane getIV(){
        return myGridpane;
    }; // will depend on what type of grid it is




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
