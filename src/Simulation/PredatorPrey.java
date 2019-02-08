/*
package Simulation;

import javafx.scene.paint.Color;
import Cell.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PredatorPrey extends Simulation {

    private HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("water",  0);
        put("fish",   1);
        put("shark", 10);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.BLUE);
        put(1, Color.YELLOW);
        for (int i=2; i<100; i+=2){
            put(i, Color.GREEN);
        }

    }};

    final ArrayList<Point> possibleNeighbors = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    private int myEnergyRequirement; //number of rounds needed to pass before reproduction;
    private int mySharkMaxLives; // shark will die if its lives reaches zero.
    // special state: unoccupied = -2. Where a shark died and no one can move into that space until the next turn. Turns into water after all fish and shark are done updating
    private int myRoundsPassed;
    private int energyPerFish;


    public PredatorPrey(HashMap<String, Double> moreInfoLookupTable){
        myPossibleNeighbors = possibleNeighbors;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myMoreInfoLookupTable = moreInfoLookupTable;
        mySharkMaxLives = 2 * (int) Math.round(moreInfoLookupTable.get("initSharkEnergy"));
        myEnergyRequirement = 2 * (int) Math.round(moreInfoLookupTable.get("ticksToReproduce"));
        //tateLookupTable.put("sharks", 2 * mySharkMaxLives);
        energyPerFish = 2 * (int) Math.round(moreInfoLookupTable.get("energyGainedPerFish"));

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
        Cell next;
        Cell randomCell;
        ArrayList<Cell> neighbors;
        Random random = new Random();
        int randomIntMoving;

        // FOR SHARKS
        for (int row = 0; row < myCurrentGrid.length; row ++){
            for (int col = 0; col < myCurrentGrid[0].length; col ++){
                current = myCurrentGrid[row][col];
                if (isShark(current)){
                    if (calculateSharkLives(current) == 0){ // shark has no energy, dies
                        myNextGrid[row][col].setState(0);
                    }
                    else {
                        neighbors = getNeighbors(current); // possible spaces to move to
                        removeOccupiedCells(neighbors);
                        if (doesContainFish(neighbors)){
                            removeNonFish(neighbors); // if there's a fish in neighbors, remove any non fish. Also if another shark is in that space in next state (aka the other shark ate the fish, then also remove that cell
                        }

                        if (!neighbors.isEmpty()) { // if movement occurs
                            randomIntMoving = random.nextInt(neighbors.size()); // now pick a random available neighbor to move to
                            randomCell = neighbors.get(randomIntMoving);
                            next = myNextGrid[randomCell.getRow()][randomCell.getCol()];
                            if (isFish(randomCell)) { // shark consumes fish
                                next.setState(livesToSharkState(mySharkMaxLives)); // shark energy resets
                               // next.setState(current.getState()+energyPerFish);
                                randomCell.setState(0); // make a fish a water
                            } else { // make shark lose one life for not consuming a fish
                                next.setState(current.getState() - 2); // lose one life (
                            }
                            if (isItTime()){ // reproduce
                                myNextGrid[current.getRow()][current.getCol()].setState(livesToSharkState(mySharkMaxLives)); // make this a shark with max lives
                            }
                        }
                        else {
                            myNextGrid[current.getRow()][current.getCol()].setState(current.getState() - 2);
                        }
                    }

                }

            }
        }

        //FISH
        for (int row = 0; row < myCurrentGrid.length; row ++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                current = myCurrentGrid[row][col];
                if (isFish(current)){
                    neighbors = getNeighbors(current); // possible spaces to move to
                    removeOccupiedCells(neighbors);
                    removeNonWater(neighbors);

                    if (!neighbors.isEmpty()) { // if movement occurs
                        randomIntMoving = random.nextInt(neighbors.size()); // now pick a random available neighbor to move to
                        randomCell = neighbors.get(randomIntMoving);
                        next = myNextGrid[randomCell.getRow()][randomCell.getCol()];
                        next.setState(1);
                        if (isItTime()){
                            myNextGrid[current.getRow()][current.getCol()].setState(1); // leave a fish behind
                        }
                    }
                    else {
                        myNextGrid[current.getRow()][current.getCol()].setState(1);
                    }
                }
            }
        }

        for (int row = 0; row < myCurrentGrid.length; row ++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                next = myNextGrid[row][col];
                if (next.getState() == -1){
                    next.setState(0);
                }
            }
        }

        myRoundsPassed ++;



    }



    private boolean isShark(Cell cell){
        int state = cell.getState();
        return (isEven(state) && state != 0); // even is shark
    }
    private boolean isFish(Cell cell){
        int state = cell.getState();
        return (state == 1); // 1 is fish
    }

    private boolean isEven(int n){
        return (n % 2 == 0);
    }

    private void removeOccupiedCells(ArrayList<Cell> neighbors){
        ArrayList<Cell> toRemove = new ArrayList<Cell>();
        for (Cell cell: neighbors){
            if (ifOccupiedInNextState(cell)){ // if the
                toRemove.add(cell);
            }
        }
        for (Cell cell: toRemove){
            neighbors.remove(cell);
        }
    }

    private void removeNonFish(ArrayList<Cell> neighbors){
        ArrayList<Cell> toRemove = new ArrayList<Cell>();

        for (Cell cell: neighbors){
            if (!isFish(cell)){
                toRemove.add(cell);
            }
        }
        for (Cell cell: toRemove){
            neighbors.remove(cell);
        }
    }

    private void removeNonWater(ArrayList<Cell> neighbors){
        ArrayList<Cell> toRemove = new ArrayList<Cell>();

        for (Cell cell: neighbors){
            if (cell.getState() != 0){ // if not water
                toRemove.add(cell);
            }
        }
        for (Cell cell: toRemove){
            neighbors.remove(cell);
        }
    }

    private boolean ifOccupiedInNextState(Cell cell){
        int row = cell.getRow();
        int col = cell.getCol();
        int nextStateCell = myNextGrid[row][col].getState();
        return (nextStateCell != -1); // if nextStateCell is a shark or a fish, then this shark is too late (another creature already is planning on taking that space)


    }

    private boolean doesContainFish(ArrayList<Cell> neighbors){
        for (Cell cell: neighbors){
            if (isFish(cell)){
                return true;
            }
        }
        return false;
    }

    private int calculateSharkLives(Cell cell){
        int state = cell.getState();
        return state/2 - 1;

    }

    private int livesToSharkState(int lives){
        return lives * 2 + 2;
    }

    private boolean isItTime(){
        return (myRoundsPassed % myEnergyRequirement == 0 && myRoundsPassed != 0);
    }


}

*/