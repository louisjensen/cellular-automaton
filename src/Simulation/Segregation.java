package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import view.NeighborsMaker;
import java.util.Map;
import java.util.List;

/**
 * @author:  Justin Kim, Louis Jensen, Louis Lee
 */

public class Segregation extends Simulation {


    final Map<String, Integer> stateLookupTable = new HashMap<String, Integer>(){{
        put("empty",  0);
        put("blue",   1);
        put("red",    2);
    }};

    final Map<Integer, Color> colorLookupTable = new HashMap<Integer, Color>(){{
        put(0, Color.WHITE);
        put(1, Color.BLUE);
        put(2, Color.RED);
    }};

    private double myTolerance;
    private Random random = new Random();

    public Segregation(Map<String, Double> moreInfoLookupTable, Cell[][] current, Cell[][] next, NeighborsMaker nm){
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myMoreInfoLookupTable = moreInfoLookupTable;
        myTolerance = myMoreInfoLookupTable.get("tolerance");
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;
    }

    public int getState(String stateString){
        return stateLookupTable.get(stateString);
    }

    private boolean checkIfSatisfied(Cell cell,  List<Cell> neighbors){
        int myState = cell.getState(); // get red or blue
        int numMyState = 0; // if red, count how many red neighbors.
        int numOtherState = 0;
        int emptySpace = 0;
        float percentage;
        if(myState == 0){
            return true;
        }
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == 0) {
                emptySpace += 1;
            }
            else if (neighbor.getState() == myState) {
                numMyState += 1;
            }
            else if (neighbor.getState() != myState) {
                numOtherState += 1;
            }
        }
        if(neighbors.size()== emptySpace){
            return true;
        }
        percentage = (float) numMyState / (numMyState + numOtherState);

       return (percentage > myTolerance);
    }

    @Override
    /**
     * Holds all algorithm for updating the grid
     */
    public void update(){
        copyCurrentToNext();
        ArrayList<Cell> dissatisfiedCells = new ArrayList<Cell>();
        ArrayList<Cell> emptyCells = new ArrayList<Cell>();

        getDissatisfiedAndEmpty(dissatisfiedCells, emptyCells);
        moveDissatisfiedToEmpty(dissatisfiedCells, emptyCells);
        updateColor();

    }

    private void getDissatisfiedAndEmpty(List<Cell> dissatisfiedCells, List<Cell> emptyCells){
        List<Cell> neighbors;
        Cell current;
        for(int i=0 ; i<myCurrentGrid.length ; i ++){
            for(int j=0; j<myCurrentGrid[0].length; j++){
                current = myCurrentGrid[i][j];
                neighbors = getNeighbors(current);
                if (!checkIfSatisfied(current, neighbors)){
                    dissatisfiedCells.add(current);
                }
                else if (myCurrentGrid[i][j].getState() == 0){
                    emptyCells.add(myCurrentGrid[i][j]);
                }
            }
        }
    }

    private void moveDissatisfiedToEmpty(List<Cell> dissatisfiedCells, List<Cell> emptyCells){
        Cell cellEmpty;
        Cell cellMoving;
        Cell nextStateMove;
        Cell nextStateEmpty;
        int randomIntEmpty;
        int randomIntMoving;
        int emptyCellsSize;
        int dissatisfiedCellsSize;

        if(!dissatisfiedCells.isEmpty()){
            for (int i = 0; i < dissatisfiedCells.size(); i++){
                dissatisfiedCellsSize = dissatisfiedCells.size();
                randomIntMoving = random.nextInt(dissatisfiedCellsSize);
                cellMoving = dissatisfiedCells.get(randomIntMoving);
                if(!emptyCells.isEmpty()){
                    emptyCellsSize = emptyCells.size();
                    randomIntEmpty = random.nextInt(emptyCellsSize);
                    cellEmpty = emptyCells.get(randomIntEmpty);
                    nextStateMove = myNextGrid[cellEmpty.getRow()][cellEmpty.getCol()];
                    nextStateEmpty = myNextGrid[cellMoving.getRow()][cellMoving.getCol()];

                    nextStateMove.setState(cellMoving.getState());

                    nextStateEmpty.setState(0);
                    emptyCells.remove(cellEmpty);
                }
                dissatisfiedCells.remove(cellMoving);
            }
        }
    }

    private void copyCurrentToNext(){
        Cell current;
        Cell next;
        for (int i=0 ; i<myCurrentGrid.length ; i ++) {
            for (int j = 0; j < myCurrentGrid[0].length; j++) {
                current = myCurrentGrid[i][j];
                next = myNextGrid[i][j];
                next.setState(current.getState());
            }
        }
    }

    @Override
    public int getNextStateOfCell(Cell cell, List<Cell> neighbors) {
        return 1;
    }

}
