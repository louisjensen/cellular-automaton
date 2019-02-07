
package view;


import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;
import javafx.scene.paint.Color;


public class GameOfLife extends Simulation{

    private HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("dead",  0);
        put("alive", 1);
    }};

    private HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.WHITE);
        put(1, Color.BLACK);
    }};

    private ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{ // maybe we could move possible neighbors to the Cell class so the when grid asks for possibleNeighbors, itll return possible neighbors from the type of cell it is.
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
        add(new Point( 1, 1));
        add(new Point( 1,-1));
        add(new Point(-1, 1));
        add(new Point(-1,-1));
    }};

    public GameOfLife(Cell[][] current, Cell[][] next){
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
        int numAlive = 0;
        int nextState;

        for (Cell neighbor: neighbors){
            if (neighbor.getState() == 1)
                numAlive ++;
        }
        if (numAlive <= 1) //Any live cell with fewer than two live neighbors dies, as if by underpopulation.
            nextState = 0; //cell dies
        else if (numAlive == 2 || numAlive == 3) //Any live cell with two or three live neighbors lives on to the next generation.
            nextState = 1; //cell stays alive
        else if (numAlive == 3 && cell.getState() == 1) //Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
            nextState = 1; //cell is born
        else
            nextState = 0; //cell is born

        return nextState;
    }

}

