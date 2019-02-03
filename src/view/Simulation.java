package view;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class
    public ArrayList<Point> myPossibleNeighbors;
    public HashMap<Integer, Color> myColorLookupTable;
    public HashMap<String, Integer> myStateLookupTable;
    public HashMap<String, Double> myMoreInfoLookupTable;

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
                if (isSafe(cellNeighborRow, cellNeighborCol, grid)) {
                    neighbors.add(grid[cellNeighborRow][cellNeighborCol]);
                }
        }
        System.out.println(neighbors.get(0).getRow() + ", " + neighbors.get(0).getCol());

        System.out.println(neighbors.get(1).getRow() + ", " + neighbors.get(1).getCol());
        System.out.println(neighbors.get(2).getRow() + ", " + neighbors.get(2).getCol());
        System.out.println(neighbors.get(0).getRow() + ", " + neighbors.get(0).getState());


        //System.out.println(neighbors.size());
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
        int gridRowMax = grid.length;
        int gridColMax = grid[0].length;
        if (!((row >= 0 && row < gridRowMax) && (col >= 0 && col < gridColMax))){
            return false;
        }

        int cellState = grid[row][col].getState();

        return (cellState != -1);
    }

    public HashMap<Integer, Color> getMyColorLookupTable(){
        return myColorLookupTable;
    }

    public HashMap<String, Integer> getMyStateLookupTable(){
        return myStateLookupTable;
    }
}
