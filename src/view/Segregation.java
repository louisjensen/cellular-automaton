package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Segregation extends Simulation {
    public void update(){

    }

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
        int emptySpace = 0;
        float percentage;
        Cell cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0);

        //if cell is an empty space
        if(myState == 0){
            return cellNextState;
        }

        //if cell is not an empty space
        for (Cell neighbor: neighbors){
            if(neighbor.getState() == 0){
                emptySpace +=1;
            }
            if(neighbor.getState() != 0 && neighbor.getState() == myState){
                numMyState +=1;
            }
            if(neighbor.getState() != 0 && neighbor.getState() != myState){
                numOtherState +=1;
            }
        }

        percentage = myState / (neighbors.size() - emptySpace);
        //satisfied cells
        if (percentage >= myTolerance) {
            return new Cell(cell.getRow(), cell.getCol(), cell.getSize(), cell.getState());
        }
        //unsatisfied cells
        else{


        }

        return new Cell(1,1,1,1);
}


}
