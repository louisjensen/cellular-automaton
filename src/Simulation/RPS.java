package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import view.NeighborsMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.List;

/**
 * @author:  Justin Kim
 */

public class RPS extends Simulation {

    private double myThreshold;

    final Map<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("rock",  0);
        put("paper", 1);
        put("scissors", 2);

    }};

    final Map<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.GREEN);
        put(1, Color.BLUE);
        put(2, Color.RED);
    }};

    public RPS(Map<String, Double> moreInfoLookupTable, Cell[][] current, Cell[][] next, NeighborsMaker nm){
        myMoreInfoLookupTable = moreInfoLookupTable;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;
        myThreshold = myMoreInfoLookupTable.get("threshold");
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }

    @Override
    /**
     * Differnt rule for updating the cell.
     */
    public int getNextStateOfCell(Cell cell, List<Cell> neighbors) {
        int opponentState = getOpponent(cell.getState());
        int numOpponents = countOpponents(cell, neighbors);
        if ((double)numOpponents/neighbors.size() >= myThreshold + generateRandomDouble()){
            return opponentState;
        }
        else {
            return cell.getState();
        }
    }

    private int countOpponents(Cell cell, List<Cell> neighbors){
        int count = 0;
        int opponentState = getOpponent(cell.getState());
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == opponentState){
                count ++;
            }
        }
        return count;
    }

    private int getOpponent(int state){
        int i = 0;
        if (state == 0)
            i = 2;
        else if (state == 1)
            i = 0;
        else if (state == 2)
            i = 1;
        return i;

    }

    private double generateRandomDouble(){
        Random random = new Random();
        return -.2 + random.nextDouble() * (.4);
    }

}
