package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SpreadingOfFire extends Simulation {


    private double probCatch;
    private Random random = new Random();
    int randomInt;

    private final HashMap<String, Integer> stateLookupTable = new HashMap<>(){{
        put("dead",  1);
        put("alive", 0);
        put("burning", 2);
    }};

    private final HashMap<Integer, Color> colorLookupTable = new HashMap<>(){{
        put(0, Color.GREEN);
        put(1, Color.YELLOW);
        put(2, Color.RED);
    }};

    private final ArrayList<Point> possibleNeighbors = new ArrayList<>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    public SpreadingOfFire(HashMap<String, Double> map, Cell[][] current, Cell[][] next){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        probCatch = map.get("probCatch");
        myCurrentGrid = current;
        myNextGrid = next;

        // here, modify possibleNeighbors based on if the simulation is a regular grid, a triangle grid, or a hex grid
        // also modify possibleNeighbors based on if the grid is a toroid
    }

    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        int numFire = 0;
        int nextState;

        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == 2)
                numFire += 1;
        }

        if (cell.getState() == 0 && numFire == 0) {
            nextState = 0;
        }
        else if (cell.getState() == 0 && numFire > 0) {
            randomInt = random.nextInt(100) + 1;
            if (randomInt <= (int)(probCatch * 100)) {
                nextState = 2;
            } else {
                nextState = 0;
            }
        }
        else if(cell.getState() == 2) {
            nextState = 1;
        }
        else{
            nextState = 1;
        }

        return nextState;
    }

    public int getState(String stateString){return stateLookupTable.get(stateString);}


}
