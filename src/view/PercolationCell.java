package view;

import javafx.scene.shape.Polygon;

public class PercolationCell extends Cell {

    private int myRow;
    private int myCol;
    private int myState;
    private String myShapeType;
    private Polygon myShape;

    public PercolationCell(Polygon shape){
        super(shape);
    }

}
