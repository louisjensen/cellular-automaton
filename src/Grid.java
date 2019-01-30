import java.util.ArrayList;

public class Grid{

    private int defaultGridSize = 10;
    private Cell[][] currentGrid;
    private ArrayList<Cell>[][] nextGrid;
    private int gridSize;
    Simulation simulation = new Simulation();

    //default constuctor
    public Grid(){
        currentGrid = new Cell[defaultGridSize][defaultGridSize];
        nextGrid = new ArrayList<Cell>[defaultGridSize][defaultGridSize];
    }

    //constructor
    public Grid(int size){
        currentGrid = new Cell[size][size];
        nextGrid = new ArrayList<Cell>[size][size];
        gridSize = size;
    }
    public Cell[][] getCurrentGrid(){
        return this.currentGrid;
    }

    public ArrayList<Cell>[][] getNextGrid(){
        return this.nextGrid;
    }

    private void runSimulation(Cell[][] grid){
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                nextGrid[row][col].add(simulation.updateGrid(currentGrid[row][col]));
            }
        }
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                currentGrid[row][col] = simulation.checkConflicts(nextGrid[row][col]);
                nextGrid[row][col].clear();
            }
        }
    }

}
