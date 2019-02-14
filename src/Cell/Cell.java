package Cell;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Cell{

    /**
     * @author Justin Kim
     */

    public int myState;
    public Polygon myShape;
    public int myRow;
    public int myCol;

    public Cell (Polygon shape){
        myShape = shape;
    }

    /**
     * returns row index of cell
     * @return
     */
    public int getRow(){
        return myRow;
    }

    /**
     * sets row index of cell
     * @param row
     */
    public void setRow(int row){
        myRow = row;
    }

    /**
     * gets col index of cell
     * @return
     */
    public int getCol(){
        return myCol;
    }

    /**
     * set col index of cell
     * @param col
     */
    public void setCol(int col){
        myCol = col;
    }

    /**
     * gets state of cell
     * @return
     */
    public int getState(){
        return myState;
    }

    /**
     * sets state of cell
     * @param state
     */
    public void setState(int state){
        myState = state;
    }

    /**
     * returns the polygon object of the shape
     * @return
     */
    public Polygon getShape(){
        return myShape;
    }

    /**
     * sets the color of myShape
     * @param color
     */
    public void setColor(Paint color){
        myShape.setFill(color);
    }

}
