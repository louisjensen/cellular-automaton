package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Percolation extends Simulation {

    private HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("opened", 1);
        put("closed", 0);
        put("filled", 2);
    }};

    private HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.BLACK);
        put(1, Color.WHITE);
        put(2, Color.BLUE);
    }};

    private ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
        add(new Point( 1, 1));
        add(new Point( 1,-1));
        add(new Point(-1, 1));
        add(new Point(-1,-1));
    }};

    public Percolation(Cell[][] current, Cell[][] next){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myCurrentGrid = current;
        myNextGrid = next;
        // here, modify possibleNeighbors based on if the simulation is a regular grid, a triangle grid, or a hex grid
        // also modify possibleNeighbors based on if the grid is a toroid
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }

    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        int nextState;

        if (cell.getState() == 0) // if closed, then always closed
            nextState = 0; //cell is born

        else if (cell.getState() == 2) // if filled, then always filled
            nextState = 2; //cell is born

        else { // if open, check if any neighbors are filled, if (cell.getState() == 1)
            int state = 1;
            for(Cell neighbor: neighbors) {
                if (neighbor.getState() == 2) { // if neighbor is full, next cell is filled
                    state = 2;
                    break;
                }
            }
            nextState = state; // if no neighbors are filled, then just stay open
        }
        return nextState;
    }
}
