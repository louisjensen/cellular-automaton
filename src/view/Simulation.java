package view;

import java.util.ArrayList;

public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class

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
    public abstract ArrayList<Cell> getNeighbors(Cell cell, Cell[][] grid);

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
}
