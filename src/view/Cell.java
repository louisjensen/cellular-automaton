package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Cell{

    public int myState;
    public Polygon myShape;
    public int myRow;
    public int myCol;

    public Cell (Polygon shape){
        myShape = shape;
    }

    public int getRow(){
        return myRow;
    }
    public void setRow(int row){
        myRow = row;
    }

    public int getCol(){
        return myCol;
    }
    public void setCol(int col){
        myCol = col;
    }

    public int getState(){
        return myState;
    }
    public void setState(int state){
        myState = state;
    }

    public Polygon getShape(){
        return myShape;
    }

    public void setColor(Paint color){
        myShape.setFill(color);
    }


}
