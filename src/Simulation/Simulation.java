package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import view.NeighborsMaker;
import java.util.Map;
import java.util.List;

/**
 * @author:  Justin Kim, Louis Jensen, Louis Lee
 * This class is an abstract class for all simulation classes.
 * Other classes extend this class, so this class holds all algorithm methods
 * essential in running the game.
 */


public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class
    public Map<Integer, Color> myColorLookupTable;
    public Map<String, Integer> myStateLookupTable;
    public Map<String, Double> myMoreInfoLookupTable;
    public NeighborsMaker myNeighborsMaker;
    public Cell[][] myCurrentGrid;
    public Cell[][] myNextGrid;

    /**
     * Returns the next state of cell based on the states of its neighbors.
     * @param cell to be updated
     * @param neighbors what are the "neighbors" of cell. Determined by the Simulation type.
     * @return next state of cell
     */
    public abstract int getNextStateOfCell(Cell cell, List<Cell> neighbors);

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

    /**
     * Returns possible neighbors
     * @param cell
     * @return list of possible points(neighbors)
     */
    public ArrayList<Cell> getNeighbors(Cell cell){
        return myNeighborsMaker.getNeighbors(cell, myCurrentGrid);
    }

    /**
     * update the color in the actual grid based on the state of each cells
     */
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

    /**
     * move next grid to currrent after updating
     */
    public void moveNextToCurrent(){
        for (int i = 0; i < myCurrentGrid.length; i++) {
            for (int j = 0; j < myCurrentGrid[0].length; j++) {
                myCurrentGrid[i][j].setState(myNextGrid[i][j].getState());
                myNextGrid[i][j].setState(-1);
            }
        }
    }

    public Map<Integer, Color> getMyColorLookupTable(){
        return myColorLookupTable;
    }

    public Map<String, Integer> getMyStateLookupTable(){
        return myStateLookupTable;
    }

}
