package view;

import javafx.scene.shape.Polygon;

public class SpreadingOfFireCell extends Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private String myShapeType;
    private Polygon myShape;

    public SpreadingOfFireCell(Polygon shape){
        super(shape);
    }

}
