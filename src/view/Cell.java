package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private Rectangle myImage;
    private int myEnergy;
    private String myShape;

   /* public Cell(Point coordinates, int state, String shape, HashMap<String, ArrayList> map){
        myRow = coordinates.x;
        myCol = coordinates.y;
        myState = state;
        myShape = shape;
    }*/


    public int getRow(){
        return myRow;
    }
    public int getCol(){
        return myCol;
    }
    public int getState(){
        return myState;
    }

    public String getShape(){
        return myShape;
    }

    public void setColor(Paint color){
        myImage.setFill(color);
    }

    public void setState(int state){
        myState = state;
    }
}
