package view;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Map;

public class ShapeMaker {
    private String RectangleAttributes[] ={"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};
    private String TriangleAttributes[] = {"row", "col", "pixel_x", "pixel_y", "sidelength", "isPointed", "state"};
    private String HexagonAttributes[] = {"row", "col", "pixel_x", "pixel_y", "size_x", "size_y", "state"};


    public ShapeMaker(){
    }


    public Polygon makeTriangle(Point point, int d, boolean isPointed){
        double x = point.x;
        double y = point.y;

        Polygon triangle = new Polygon();
        if(isPointed) {
            triangle.getPoints().addAll(new Double[]{
                    x, y,
                    x + d, y + d,
                    x - d, y + d});
        }
        else{
            triangle.getPoints().addAll(new Double[]{
                    x - d, y,
                    x + d, y,
                    x, y + d});
        }
        initialize(triangle);

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
        initialize(rectangle);
        return rectangle;
    }

    public Polygon makeHexagon(Point point, double d){

        double row = point.x;
        double col = point.y;
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
                    row -d, col -d,
                    row, col - 2 *d,
                    row +d, col - d,
                    row +d, col +d,
                    row, col +2*d,
                    row -d, col+d
        });
        initialize(hexagon);
        return hexagon;
    }

    private void initialize(Polygon shape){
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.BLACK);
    }
}
