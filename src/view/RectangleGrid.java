package view;

import java.util.HashMap;
import javafx.scene.shape.Polygon;
import java.awt.Point;


public class RectangleGrid extends Grid {

    private int myRectangleHeight;
    private int myRectangleWidth;


    public RectangleGrid(String filePath, int displaySize){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();
        myCurrentState = new Cell[numCols][numRows];// for testing
        myNextState = new Cell[numCols][numRows];
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
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                shape = sm.makeRectangle(new Point(pixelX, pixelY), myRectangleWidth, myRectangleHeight) ;
                myCurrentState[i][j] = getSpecificCell(shape);
                current = myCurrentState[i][j];
                current.setState(-1);
                current.setRow(j);
                current.setCol(i);

                myNextState[i][j] = getSpecificCell(shape);
                next = myNextState[i][j];
                next.setState(-1);
                next.setRow(j);
                next.setCol(i);

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
