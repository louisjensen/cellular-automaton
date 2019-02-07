package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameOfLifeCell extends Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private String myShapeType;
    private Shape myShape;

    public GameOfLifeCell(String shape){
        myShapeType = shape;
    }

    public GameOfLifeCell(Point rc, int state, String shape, HashMap<String, ArrayList> attributes, int d){
        myRow = rc.x;
        myCol = rc.y;
        myState = state;
        ShapeMaker sm = new ShapeMaker(myRow, myCol, myState, d);
        myShape = sm.getShape(shape);
    }

}
