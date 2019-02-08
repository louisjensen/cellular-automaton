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
