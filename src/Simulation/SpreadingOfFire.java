package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import view.NeighborsMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SpreadingOfFire extends Simulation {

    private double probCatch;
    private Random random = new Random();
    int randomInt;

    final HashMap<String, Integer> stateLookupTable = new HashMap<>() {{
        put("alive", 0);
        put("dead", 1);
        put("burning", 2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<>() {{
        put(0, Color.GREEN);
        put(1, Color.YELLOW);
        put(2, Color.RED);
    }};

    public SpreadingOfFire(HashMap<String, Double> map, Cell[][] current, Cell[][] next, NeighborsMaker nm) {
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        probCatch = map.get("probCatch");
        myCurrentGrid = current;
        myNextGrid = next;
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
        } else if (cell.getState() == 0 && numFire > 0) {
            randomInt = random.nextInt(100) + 1;
            if (randomInt <= (int) (probCatch * 100)) {
                nextState = 2;
            } else {
                nextState = 0;
            }
        } else if (cell.getState() == 2) {
            nextState = 1;
        } else {
            nextState = 1;
        }

        return nextState;
    }

    public int getState(String stateString) {
        return stateLookupTable.get(stateString);
    }

}
