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

    private double myTolerance;

    public Segregation(HashMap<String, Double> moreInfoLookupTable){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myMoreInfoLookupTable = moreInfoLookupTable;

        myTolerance = myMoreInfoLookupTable.get("tolerance");
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }


    @Override
    public Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        int myState = cell.getState(); // get red or blue
        int numMyState = 0; // if red, count how many red neighbors.
        int numOtherState = 0;
        int neighborState;

        for (Cell neighbor: neighbors){
            neighborState = neighbor.getState();
            if (neighborState == myState){
                numMyState ++;
            }
            else if (neighborState != 0 && neighborState != myState){
                numOtherState ++;
            }
        }

        return new Cell(1,1,1,1);
}


}
