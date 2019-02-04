
package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PredatorPrey extends Simulation {

    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("water",  0);
        put("fish",   1);
        put("sharks", 2);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.BLUE);
        put(1, Color.YELLOW);
        put(2, Color.GREEN);
    }};

    final ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    private int energyRequirement;
    // special state: unoccupied = -2. Where a shark died and no one can move into that space until the next turn. Turns into water after all fish and shark are done updating


    public PredatorPrey(HashMap<String, Double> moreInfoLookupTable){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myMoreInfoLookupTable = moreInfoLookupTable;
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }


    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {

        return 1;
    }

    @Override
    public void update(){
        Cell current;
        ArrayList<Cell> neighbors;
        for (int row = 0; row < myCurrentGrid.length; row ++){
            for (int col = 0; col < myCurrentGrid[0].length; col ++){
                current = myCurrentGrid[row][col];
                if (isShark(current)){
                    if (calculateEnergy(current) == 0){ // shark has no energy, dies
                        myNextGrid[row][col].setState(-2);
                    }
                    else {
                        neighbors = getNeighbors(current); // possible spaces to move to
                        if (doesContainFish(neighbors)){
                            removeNonFish(neighbors); // if there's a fish in neighbors, remove any non fish. Also if another shark is in that space in next state (aka the other shark ate the fish, then also remove that cell
                        }
                    }

                }

            }
        }
    }

    private boolean isShark(Cell cell){
        int state = cell.getState();
        return (!isEven(state) && state != 0); // odd is shark
    }
    private boolean isFish(Cell cell){
        int state = cell.getState();
        return (isEven(state) && state != 0); // even is fish
    }

    private boolean isEven(int n){
        return (n % 2 == 0);
    }

    private void removeNonFish(ArrayList<Cell> neighbors){
        for (Cell cell: neighbors){
            if (isFish(cell) && )
        }
    }

    private boolean checkIfOccupiedInNextState(Cell cell){
        int row = cell.getRow();
        int col = cell.getCol();
        Cell nextStateCell = myNextGrid[row][col];
        if (isShark(nextStateCell) ){ // if nextStateCell is a shark, then this shark is too late
            if ()
        }
    }

    private boolean doesContainFish(ArrayList<Cell> neighbors){
        for (Cell cell: neighbors){
            if (isFish(cell)){
                return true;
            }
        }
        return false;
    }

    private int calculateEnergy(Cell cell){
        int state = cell.getState();
        int energy;
        if (isShark(cell)){
            energy = state/2 + 1;
        }
        else if (isFish(cell)){
            energy = state/2;
        }
        else { //water
            energy = 0;
        }

        return energy;
    }


}

