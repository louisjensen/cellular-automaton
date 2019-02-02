package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Segregation extends Simulation {

    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("empty",  0);
        put("blue",   1);
        put("red",    2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.WHITE);
        put(1, Color.BLUE);
        put(2, Color.RED);
    }};

    final ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
        add(new Point( 1, 1));
        add(new Point( 1,-1));
        add(new Point(-1, 1));
        add(new Point(-1,-1));
    }};

    final HashMap<String, Integer> moreInfoLookupTable = new HashMap<String, Integer>(){{

    }};

    public Segregation(){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myMoreInfoLookupTable = moreInfoLookupTable;
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }


    @Override
    public Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        int numDead = 0;
        int numAlive = 0;
        Cell cellNextState;

        for (Cell neighbor: neighbors){
            if (neighbor.getState() == 0)
                numDead ++;
            else if (neighbor.getState() == 1)
                numAlive ++;
        }

        if (numAlive <= 1) //Any live cell with fewer than two live neighbors dies, as if by underpopulation.
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0); //cell dies
        else if (numAlive == 2 || numAlive == 3) //Any live cell with two or three live neighbors lives on to the next generation.
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 1); //cell stays alive
        else if (numAlive == 3 && cell.getState() == 1) //Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 1); //cell is born
        else
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0); //cell is born


        return cellNextState;
    }
}


}
