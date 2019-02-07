package view;

public class RectangleGrid extends Grid {

    public RectangleGrid(String filePath, int numRows, int numCols, int pixelHeigh, int pixelWidth){
        super(filePath);
        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
    }

    public void initialize(){

    }
}
