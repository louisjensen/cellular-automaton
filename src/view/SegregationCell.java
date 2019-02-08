package view;

import javafx.scene.shape.Polygon;

public class SegregationCell extends Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private String myShapeType;
    private Polygon myShape;

    public SegregationCell(Polygon shape){
        super(shape);

    }

}
