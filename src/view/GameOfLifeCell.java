package view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Polygon;
import java.util.Map;

public class GameOfLifeCell extends Cell{

    public GameOfLifeCell(Polygon shape){
        super(shape);
    }

    /*
    public GameOfLifeCell(Point rc, int state, Polygon shape, HashMap<String, ArrayList> attributes, int d){
        myRow = rc.x;
        myCol = rc.y;
        myState = state;
    }
*/

}
