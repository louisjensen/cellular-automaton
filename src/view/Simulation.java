package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class
    public ArrayList<Point> myPossibleNeighbors;
    public HashMap<Integer, Color> myColorLookupTable;
    public HashMap<String, Integer> myStateLookupTable;

    /**
     * Returns the next state of cell based on the states of its neighbors.
     * @param cell to be updated
     * @param neighbors what are the "neighbors" of cell. Determined by the Simulation type.
     * @return next state of cell
     */
    public abstract Cell getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors);

    /**
     * Returns a list of the neighboring cells of cell. Based on
     * @param cell cell to get neighbors of
     * @param grid entire grid of cells
     * @return list of the neighbors of cell in grid.
     */
    public ArrayList<Cell> getNeighbors(Cell cell, Cell[][] grid) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        int cellRow = cell.getRow();
        int cellCol = cell.getCol();

        int cellNeighborRow;
        int cellNeighborCol;

        for (Point rc: myPossibleNeighbors){
            cellNeighborRow = cellRow + (int) rc.getX();
            cellNeighborCol = cellCol + (int) rc.getY();
            if (isSafe(cellNeighborRow, cellNeighborCol, grid)){
                neighbors.add(grid[cellNeighborRow][cellNeighborCol]);
            }
        }
        return neighbors;
    }


    /**
     * Returns an int that is mapped to a certain state
     * @param stateString
     * @return int (state)
     */
    public abstract int getState(String stateString);

    /**
     * Checks if the row and col are "safe" in grid. Must be in bounds and the state of the Cell must not be -1
     * @param row
     * @param col
     * @param grid
     * @return true if the row and col are "safe"
     */
    public boolean isSafe(int row, int col, Cell[][] grid){
        int cellState = grid[row][col].getState();
        int gridRowMax = grid.length;
        int gridColMax = grid[0].length;
        return (cellState != -1 && (row >= 0 && row < gridRowMax) && (col >= 0 && col < gridColMax));
    }

    public HashMap<Integer, Color> getMyColorLookupTable(){
        return myColorLookupTable;
    }

    public HashMap<String, Integer> getMyStateLookupTable(){
        return myStateLookupTable;
    }
}
