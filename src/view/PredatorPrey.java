
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
        for (int row = 0; row < myCurrentGrid.length; row ++){
            for (int col = 0; col < myCurrentGrid[0].length; col ++){
                current = myCurrentGrid[row][col];
                if (isShark(current)){
                    if (calculateEnergy(current) == 0){ // shark has no energy, dies
                        myNextGrid[row][col].setState(-2);
                    }
                    else {

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

