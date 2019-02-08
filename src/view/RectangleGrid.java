package view;

import java.util.HashMap;
import javafx.scene.shape.Polygon;
import java.awt.Point;


public class RectangleGrid extends Grid {

    private int myRectangleHeight;
    private int myRectangleWidth;


    public RectangleGrid(String filePath, int numRows, int numCols, int displaySize){
        super(filePath);
        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        myDisplaySize = displaySize;
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        calculateMyRectangleHeight();
        calculateMyRectangleWidth();
    }

    public void initialize(){
        int pixelX = 0;
        int pixelY = 0;
        Polygon shape;
        ShapeMaker sm = new ShapeMaker();
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                shape = sm.makeRectangle(new Point(pixelX, pixelY), myRectangleWidth, myRectangleHeight) ;
                myCurrentState[row][col] = getSpecificCell(shape);
                myCurrentState[row][col].setState(-1);
                myNextState[row][col] = getSpecificCell(shape);
                myNextState[row][col].setState(-1);

                pixelX += myRectangleWidth;
            }
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
