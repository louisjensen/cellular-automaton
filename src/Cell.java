import javafx.scene.shape.Rectangle;

public class Cell {

    private int myRow;
    private int myCol;
    private int myState;
    private Rectangle image;

    public Cell(int row, int col, int state){
        myRow = row;
        myCol = col;
        myState = state;
    }

    public int getRow(){
        return myRow;
    }
    public int getCol(){
        return myCol;
    }
    public int getState(){
        return myState;
    }
}
