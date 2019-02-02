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
        //testing
        if(state == 1){
            color = Color.RED;
        } else if (state == 2){
            color = Color.BLUE;
        } else if (state == 3) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        //testing
        myImage = new Rectangle(mySize, mySize, color);
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
}
