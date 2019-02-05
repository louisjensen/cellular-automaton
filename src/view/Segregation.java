package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Segregation extends Simulation {

    Cell cellEmpty;
    Cell cellMoving;
    Cell nextStateMove;
    Cell nextStateEmpty;
    int randomIntEmpty;
    int randomIntMoving;
    int emptyCellsSize;
    int dissatisfiedCellsSize;

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
    private Random random = new Random();

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


    public boolean AreYouSatisfied(Cell cell,  ArrayList<Cell> neighbors){
        int nextState = 0;
        int myState = cell.getState(); // get red or blue
        int numMyState = 0; // if red, count how many red neighbors.
        int numOtherState = 0;
        int emptySpace = 0;
        float percentage;
        //Cell cellNextState = new Cell(cell.getRow(), cell.getCol(), cell.getSize(), 0);

        //if cell is an empty space
        if(myState == 0){
            return true;
        }

        //if cell is not an empty space
        //else {
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
        //}
        if(neighbors.size()== emptySpace){
            return true;
        }
        percentage = (float) numMyState / (numMyState + numOtherState);

       return (percentage > myTolerance);
    }

    @Override
    public void update(){
        ArrayList<Cell> DissatisfiedCells = new ArrayList<Cell>();
        ArrayList<Cell> EmptyCells = new ArrayList<Cell>();
        ArrayList<Cell> neighbors;
        for(int i=0 ; i<myCurrentGrid.length ; i ++){
            for(int j=0; j<myCurrentGrid[0].length; j++){
                neighbors = getNeighbors(myCurrentGrid[i][j]);
                if (!AreYouSatisfied(myCurrentGrid[i][j], neighbors)){
                    DissatisfiedCells.add(myCurrentGrid[i][j]);
                }
                if(myCurrentGrid[i][j].getState() == 0){
                    EmptyCells.add(myCurrentGrid[i][j]);
                }

            }
        }

        if(!DissatisfiedCells.isEmpty()){
            //for(Cell MovingCell: DissatisfiedCells){
            for (int i = 0; i < DissatisfiedCells.size(); i++){
                dissatisfiedCellsSize = DissatisfiedCells.size();
                randomIntMoving = random.nextInt(dissatisfiedCellsSize);
                cellMoving = DissatisfiedCells.get(randomIntMoving);
                if(!EmptyCells.isEmpty()){
                    emptyCellsSize = EmptyCells.size();
                    randomIntEmpty = random.nextInt(emptyCellsSize);
                    cellEmpty = EmptyCells.get(randomIntEmpty);
                    nextStateMove = myNextGrid[cellEmpty.getRow()][cellEmpty.getCol()];
                    nextStateEmpty = myNextGrid[cellMoving.getRow()][cellMoving.getCol()];

                    nextStateMove.setState(cellMoving.getState());
                    nextStateEmpty.setState(0);
                    EmptyCells.remove(cellEmpty);
                }
                DissatisfiedCells.remove(cellMoving);
            }
        }
    }


    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        return 1;
    }


}
