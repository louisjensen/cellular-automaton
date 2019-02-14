package Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import view.NeighborsMaker;
import Cell.Cell;
import javafx.scene.paint.Color;
import java.util.Map;
import java.util.List;

/**
 * @author:  Justin Kim, Louis Jensen, Louis Lee
 */

public class GameOfLife extends Simulation{

    final Map<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("dead",  0);
        put("alive", 1);
    }};

    final Map<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.WHITE);
        put(1, Color.BLACK);
    }};

    public GameOfLife(Cell[][] current, Cell[][] next, NeighborsMaker nm){
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
        int numAlive = 0;
        int nextState;

        for (Cell neighbor: neighbors){
            if (neighbor.getState() == 1)
                numAlive ++;
        }
        if (numAlive <= 1) {//Any live cell with fewer than two live neighbors dies, as if by underpopulation.
            nextState = 0;//cell dies
        }
        else if (numAlive == 2 || numAlive == 3) {//Any live cell with two or three live neighbors lives on to the next generation.
            nextState = 1; //cell stays alive
        }
        else if (numAlive == 3 && cell.getState() == 1) {//Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
            nextState = 1; //cell is born
        }
        else {
            nextState = 0; //cell is born
        }
        return nextState;
    }

}