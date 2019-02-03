package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Percolation extends Simulation {
    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("closed", 0);
        put("opened", 1);
        put("filled", 2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.BLACK);
        put(1, Color.WHITE);
        put(2, Color.BLUE);
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

    public Percolation(){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }

    @Override
    public Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {

        Cell cellNextState;

        if (cell.getState() == 0) // if closed, then always closed
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0); //cell is born

        else if (cell.getState() == 2) // if filled, then always filled
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 2); //cell is born

        else { // if open, check if any neighbors are filled, if (cell.getState() == 1)
            for(Cell neighbor: neighbors){
                if (neighbor.getState() == 2) { // if neighbor is full, next cell is filled
                    cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 2);
                    break;
                }
            }
            cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 1); // if no neighbors are filled, then just stay open
        }
        return cellNextState;

    }
}
