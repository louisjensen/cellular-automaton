package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    private Random random = new Random();
    int randomInt;

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
            for(int j=0; j<myCurrentGrid.length; j++){
                neighbors = getNeighbors(myCurrentGrid[i][j], myCurrentGrid);
                if (!AreYouSatisfied(myCurrentGrid[i][j], neighbors)){
                    DissatisfiedCells.add(myCurrentGrid[i][j]);
                }
                if(myCurrentGrid[i][j].getState() == 0){
                    EmptyCells.add(myCurrentGrid[i][j]);
                }

            }
        }
        System.out.println(DissatisfiedCells.size());
        System.out.println(EmptyCells.size());
        Cell cellEmpty;
        Cell nextStateMove;
        Cell nextStateEmpty;


        if(!DissatisfiedCells.isEmpty()){
            for(Cell MovingCell: DissatisfiedCells){
                if(!EmptyCells.isEmpty()){
                    int emptynumber = EmptyCells.size();
                    randomInt = random.nextInt(emptynumber);
                    cellEmpty = EmptyCells.get(randomInt);
                    nextStateMove = myNextGrid[cellEmpty.getRow()][cellEmpty.getCol()];
                    nextStateEmpty = myNextGrid[MovingCell.getRow()][MovingCell.getCol()];

                    nextStateMove.setState(MovingCell.getState());
                    nextStateEmpty.setState(0);
                    EmptyCells.remove(cellEmpty);
                }
            }
        }

        /*if (!EmptyCells.isEmpty()){
            for (Cell emptyCell: EmptyCells){
                if(!DissatisfiedCells.isEmpty()){
                    int unsatisfiedNum = DissatisfiedCells.size();
                    randomInt = random.nextInt(unsatisfiedNum);
                    cellToMove = DissatisfiedCells.get(randomInt);
                    nextStateMove = myNextGrid[emptyCell.getRow()][emptyCell.getCol()];
                    nextStateEmpty = myNextGrid[cellToMove.getRow()][cellToMove.getCol()];

                    nextStateMove.setState(cellToMove.getState());
                    nextStateEmpty.setState(0);
                    DissatisfiedCells.remove(cellToMove);

                }


            }
        }*/
    }


    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        /*
        //satisfied cells
        if (percentage >= myTolerance) {
            //return new Cell(cell.getRow(), cell.getCol(), cell.getSize(), cell.getState());
        }
        //unsatisfied cells
        else{
        }
        return nextState;
        //return new Cell(1,1,1,1);

         */
        return 1;
    }


}
