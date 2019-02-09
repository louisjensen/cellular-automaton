
package Grid;

import javafx.scene.shape.Polygon;

import java.awt.*;

import javafx.scene.paint.Color;
import Cell.Cell;
import view.NeighborsMaker;
import view.ShapeMaker;


public class TriangleGrid extends Grid {

    private int myTriangleLength; // triangle has height of distance, base of 2 * length

    public TriangleGrid(String filePath, int displaySize){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();

        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        myNeighborsMaker = new NeighborsMaker("triangle", myXML.getSimulationType());
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());

        calculateTriangleLength();
        System.out.println(numRows);
        System.out.println(numCols);

    }

    public void initialize(){
        int pixelX = 500;
        int pixelY = 150;

        Polygon shape;
        boolean isPointy;
        ShapeMaker sm = new ShapeMaker();

        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                if (isOdd(i + j)) {
                    isPointy = true;
                }
                else {
                    isPointy = false;
                }
                shape = sm.makeTriangle(new Point(pixelX, pixelY), myTriangleLength, isPointy);
                initializeCurrentNext(shape, i, j);
                pixelX += myTriangleLength;
            }
            pixelX = 500;
            pixelY += myTriangleLength;
        }
    }

    private int calculateNumCols(int numRows){
        return numRows * 2 - 1;
    }

    private void calculateTriangleLength(){
        myTriangleLength = myDisplaySize/myCurrentState.length;
    }

    private boolean isOdd(int n){
        return (n % 2 == 1);
    }

    private void setBlank(Polygon shape){
        shape.setFill(Color.WHITE);
        shape.setStroke(Color.WHITE);
    }

}
