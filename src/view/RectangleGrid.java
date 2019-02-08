package view;

import java.util.HashMap;
import javafx.scene.shape.Polygon;
import java.awt.Point;


public class RectangleGrid extends Grid {

    private int myRectangleHeight;
    private int myRectangleWidth;


    public RectangleGrid(String filePath, int displaySize){
        super(filePath);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();
        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        myDisplaySize = displaySize;
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        calculateMyRectangleHeight();
        calculateMyRectangleWidth();
    }

    @Override
    public void initialize(){
        int pixelX = 200;
        int pixelY = 150;
        Polygon shape;
        Cell current;
        Cell next;
        ShapeMaker sm = new ShapeMaker();
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                shape = sm.makeRectangle(new Point(pixelX, pixelY), myRectangleWidth, myRectangleHeight) ;
                myCurrentState[row][col] = getSpecificCell(shape);
                current = myCurrentState[row][col];
                current.setState(-1);
                current.setRow(row);
                current.setCol(col);

                myNextState[row][col] = getSpecificCell(shape);
                next = myNextState[row][col];
                next.setState(-1);
                next.setRow(row);
                next.setCol(col);

                pixelX += myRectangleWidth;
            }
            pixelX = 200;
            pixelY += myRectangleHeight;
        }
    }




    private void calculateMyRectangleHeight(){
        myRectangleHeight = myDisplaySize/myCurrentState.length;
    }

    private void calculateMyRectangleWidth(){
        myRectangleWidth = myDisplaySize/myCurrentState[0].length;

    }

}
