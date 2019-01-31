import javafx.scene.image.ImageView;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import java.io.File;

public abstract class Grid{

    int[][] myCurrentState;
    int[][] myNextState;
    Simulation mySimulation;
    ImageView myImageView;
    File myFile;
    int myDisplaySize;
    int gridWidth;
    int gridHeight;

    public Grid(File file, int displaySize){
        //construct grid from XML File
    }

    public ImageView show(){
        return myImageView;
    }

    public void updateGrid(){
        Cell myCell;
        ArrayList<Cell> neighbors;
        for (int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                myCell = new Cell(i, j, myCurrentState[i][j]);
                neighbors = mySimulation.getNeighbors(myCell, myCurrentState);
                myNextState[i][j] = mySimulation.getNextStateOfCell(myCell, neighbors);
            }
        }
    }

    public void initialize(){
        //reread file and create a new grid
    }

}
