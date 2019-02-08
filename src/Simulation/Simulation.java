package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import view.NeighborsMaker;

public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class
    public ArrayList<Point> myPossibleNeighbors;
    public HashMap<Integer, Color> myColorLookupTable;
    public HashMap<String, Integer> myStateLookupTable;
    public HashMap<String, Double> myMoreInfoLookupTable;
    public NeighborsMaker myNeighborsMaker;
    public Cell[][] myCurrentGrid;
    public Cell[][] myNextGrid;

    public Simulation (NeighborsMaker nm){
        myNeighborsMaker = nm;
    }

    /**
     * Returns the next state of cell based on the states of its neighbors.
     * @param cell to be updated
     * @param neighbors what are the "neighbors" of cell. Determined by the Simulation type.
     * @return next state of cell
     */
    public abstract int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors);


    /**
     * sets myCurrentGrid to myCurrentState from the Grid class
     * @param grid
     */
    public void setCurrentGrid(Cell[][] grid){
        myCurrentGrid = grid;
    }

    /**
     * sets myNextGrid to myNextState from the Grid class
     * @param grid
     */
    public void setNextGrid(Cell[][] grid){
        myNextGrid = grid;
    }

    /**
     * Returns an int that is mapped to a certain state
     * @param stateString
     * @return int (state)
     */
    public abstract int getState(String stateString);

    /**
     * Updates myNextGrid based on myCurrentGrid
     */
    public void update(){
        Cell cellToUpdate;
        Cell currentCell;
        ArrayList<Cell> neighbors;
        for (int row = 0; row < myCurrentGrid.length; row++){
            for (int col = 0; col < myCurrentGrid[0].length; col++){

                cellToUpdate = myNextGrid[row][col];
                currentCell = myCurrentGrid[row][col];

                neighbors = getNeighbors(currentCell);

                cellToUpdate.setState(getNextStateOfCell(currentCell, neighbors));
            }
        }

        updateColor();
    }

    public ArrayList<Cell> getNeighbors(Cell cell){
        System.out.println(myCurrentGrid);
        System.out.println(myNeighborsMaker);

        return myNeighborsMaker.getNeighbors(cell, myCurrentGrid);
    }

    public void updateColor(){
        Cell cellToUpdate;
        Cell currentCell;
        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                cellToUpdate = myNextGrid[row][col];
                currentCell = myCurrentGrid[row][col];
                cellToUpdate.setColor(myColorLookupTable.get(currentCell.getState()));
            }
        }
    }




    public HashMap<Integer, Color> getMyColorLookupTable(){
        return myColorLookupTable;
    }

    public HashMap<String, Integer> getMyStateLookupTable(){
        return myStateLookupTable;
    }
}
