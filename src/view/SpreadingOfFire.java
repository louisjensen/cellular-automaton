package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SpreadingOfFire extends Simulation {

    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("Empty",  0);
        put("Tree", 1);
        put("Burning", 2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.YELLOW);
        put(1, Color.GREEN);
        put(2, Color.RED);
    }};

    final ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};


    public Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors){

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
    public boolean isSafe(int row, int col, Cell[][] grid){
        int cellState = grid[row][col].getState();
        int gridRowMax = grid.length;
        int gridColMax = grid[0].length;
        return (cellState != -1 && (row >= 0 && row < gridRowMax) && (col >= 0 && col < gridColMax));
    }

}
