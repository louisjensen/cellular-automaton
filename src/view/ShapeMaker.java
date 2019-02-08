package view;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import java.util.Map;

public class ShapeMaker {
    private String RectangleAttributes[] ={"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};
    private String TriangleAttributes[] = {"row", "col", "pixel_x", "pixel_y", "sidelength", "isPointed", "state"};
    private String HexagonAttributes[] = {"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};


    public ShapeMaker(String shapetype, HashMap map){

    }


    public Polygon makeTriangle(Point point, int d, int isPointed){
        double row = point.x;
        double col = point.y;

        Polygon triangle = new Polygon();
        if(isPointed == 1) {
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

    public Polygon makeRectangle(Point point, int width, int height){
        double row = point.x;
        double col = point.y;

        Polygon rectangle = new Polygon();
        rectangle.getPoints().addAll(new Double[]{
                row, col,
                row + width, col,
                row + width, col + height,
                row, col + height,
            });
        return rectangle;
    }

    public Polygon makeHexagon(Point point, int d){

        double row = point.x;
        double col = point.y;
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
                    row -d, col -2*d,
                    row -d, col + 2 *d,
                    row +d, col - 2 *d,
                    row +d, col +2*d,
                    row +d, col,
                    row -d, col});
        return hexagon;
        }
}
