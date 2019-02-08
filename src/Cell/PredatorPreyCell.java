package Cell;

import Cell.Cell;
import javafx.scene.shape.Polygon;

public class PredatorPreyCell extends Cell {

    private int myRow;
    private int myCol;
    private int myState;
    private String myShapeType;
    private Polygon myShape;

    public PredatorPreyCell (Polygon shape){
        super(shape);
    }

}
