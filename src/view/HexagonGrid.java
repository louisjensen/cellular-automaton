package view;

public class HexagonGrid extends Grid{

    public HexagonGrid(String filePath, int GridSize, String shape){
        super(filePath);
        int numRows = GridSize;
        int numCols = GridSize;
        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
    }

}
