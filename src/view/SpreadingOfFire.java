package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SpreadingOfFire extends Simulation {



    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("dead",  1);
        put("alive", 0);
        put("burning", 2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.GREEN);
        put(1, Color.YELLOW);
        put(2, Color.RED);
    }};

    final ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    public SpreadingOfFire(){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
    }

    public Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors){
        int numFire = 0;
        int numEmpty = 0;
        Cell cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0);

        for (Cell neighbor: neighbors){
            if (neighbor.getState() == 2)
                numFire +=1;
        }

        if(cell.getState() == 0 && numFire == 0) {
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0);
        }
        if(cell.getState() ==0 && numFire>0)
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 2);

        if(cell.getState() == 2) {
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 1);
        }
        if(cell.getState() ==1){
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 1);
        }

        return cellNextState;
    }
    /**
     * Returns an int that is mapped to a certain state
     * @param stateString
     * @return int (state)
     */
    public int getState(String stateString){return stateLookupTable.get(stateString);}

    /**
     * Checks if the row and col are "safe" in grid. Must be in bounds and the state of the Cell must not be -1
     * @param row
     * @param col
     * @param grid
     * @return true if the row and col are "safe"
     */


}
