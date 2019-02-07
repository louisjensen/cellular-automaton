package view;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import java.util.Map;

public class ShapeMaker {
    int myRow;
    int myCol;
    int myPixel_x;
    int myPixel_y;
    int myState;
    int mySize;
    private String RectangleAttributes[] ={"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};
    private String TriangleAttributes[] = {"row", "col", "pixel_x", "pixel_y", "sidelength", "isPointed", "state"};
    private String HexagonAttributes[] = {"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};

    private final HashMap<String, String[]> myAttributes = new HashMap<>(){{
        put("dead", RectangleAttributes);
        put("alive", TriangleAttributes);
        put("burning", HexagonAttributes);
    }};

    public ShapeMaker(int row, int col, int state, int d){

    }


    public Shape getShape(String type){
        Shape myShape;
        if(type.equals("rectangle")){
            myShape = makeRectangle(myAttributes.get(type), d);
        }
        if(type.equals("triangle")){
            myShape = makeTriangle(myAttributes.get(type), d);
        }
        else{
            myShape =  makeHexagon(myAttributes.get(type), d);
        }

        return myShape;
    }


    public Polygon makeTriangle(String[] list, int d){

        int d = gridsize/ # of levels; // could just be 1, but the width of the grid = height of the grid * 2;

        Polygon triangle = new Polygon();
        if(isPointed == "1") {
            triangle.getPoints().addAll(new Double[]{
                    row, col + d,
                    row - d, col,
                    row + d, col});
        }
        else{
            triangle.getPoints().addAll(new Double[]{
                    row, col - d,
                    row - d, col,
                    row + d, col});
        }

        return triangle;
    }

    public Rectangle makeRectangle(String[] list, int d){
        Rectangle rectangle = new Rectangle(mySizeX, mySizeY, Color.WHITE);

        return rectangle;
    }

    public Polygon makeHexagon(String[] list, int d){

        int d = gridsize / 4* # number of hexagons;

        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
                    row -1, col -2d,
                    row -1, col + 2d,
                    row +1, col - 2d,
                    row +1, col +2d,
                    row +2, col,
                    row -2, col});
        return hexagon;
        }
}
