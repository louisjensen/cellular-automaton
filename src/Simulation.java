import java.util.ArrayList;

public abstract class Simulation { // since this doesn't have any instance variables, perhaps make this an enum class

    /**
     * Returns the next state of cell based on the states of its neighbors.
     * @param cell to be updated
     * @param neighbors what are the "neighbors" of cell. Determined by the Simulation type.
     * @return next state of cell
     */
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors){
        //
        return 1;
    }

    /**
     * Returns a list of the neighboring cells of cell. Based on
     * @param cell cell to get neighbors of
     * @param grid entire grid of cells
     * @return list of the neighbors of cell in grid.
     */
    public ArrayList<Cell> getNeighbors(Cell cell, int[][] grid){

        return new ArrayList<Cell>();
    }




}
