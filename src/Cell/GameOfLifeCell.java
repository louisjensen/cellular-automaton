package Cell;

import Cell.Cell;
import javafx.scene.shape.Polygon;

public class GameOfLifeCell extends Cell {

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
