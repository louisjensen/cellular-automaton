package view;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private int mySize; // length/width (it is a square)
    private Rectangle myImage;
    private Paint color;

    public Cell(int row, int col, int size, int state){
        myRow = row;
        myCol = col;
        mySize = size;
        myState = state;
        myImage = new Rectangle(mySize, mySize, Color.WHITE);
        myImage.setStroke(Color.BLACK);
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

    public Rectangle getImage(){
        return myImage;
    }

    public int getSize(){
        return mySize;
    }

    public void setColor(Paint color){
        myImage.setFill(color);
    }
}
