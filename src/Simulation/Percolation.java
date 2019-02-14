package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import view.NeighborsMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Percolation extends Simulation {
    /**
     * @author:  Justin Kim
     */

    final Map<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("opened", 1);
        put("closed", 0);
        put("filled", 2);
    }};

    final Map<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.BLACK);
        put(1, Color.WHITE);
        put(2, Color.BLUE);
    }};

    public Percolation(Cell[][] current, Cell[][] next, NeighborsMaker nm){
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }

    @Override
    /**
     * Rules for updating the grid
     */
    public int getNextStateOfCell(Cell cell, List<Cell> neighbors) {
        int nextState;

        if (cell.getState() == 0) {// if closed, then always closed
            nextState = 0; //cell is born
        }
        else if (cell.getState() == 2) {// if filled, then always filled
            nextState = 2; //cell is born
        }
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
