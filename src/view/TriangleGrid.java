
package view;

import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.*;
import javafx.scene.paint.Color;


public class TriangleGrid extends Grid {

    private int myTriangleLength; // triangle has height of distance, base of 2 * length

    public TriangleGrid(String filePath, int displaySize){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = calculateNumCols(numRows);

        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        calculateTriangleLength();

    }

    public void initialize(){
        int pixelX = 200;
        int pixelY = 150;

        Polygon shape;
        Cell current;
        boolean isPointy;
        Cell next;
        ShapeMaker sm = new ShapeMaker();
        int middleIndex = (myCurrentState.length-1)/2;

        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                if (isOdd(row + col)) {
                    isPointy = true;
                }
                else {
                    isPointy = false;
                }
                shape = sm.makeTriangle(new Point(pixelX, pixelY), myTriangleLength, isPointy);

                myCurrentState[row][col] = getSpecificCell(shape);
                current = myCurrentState[row][col];
                current.setRow(row);
                current.setCol(col);

                myNextState[row][col] = getSpecificCell(shape);
                next = myNextState[row][col];
                next.setRow(row);
                next.setCol(col);
                if (col >= middleIndex - row && col <= middleIndex + row) { // if in appropriate place
                    current.setState(-1);
                    current.setState(-1);
                }
                else {
                    next.setState(-2);
                    next.setState(-2);
                    setBlank(shape);
                }

                pixelX += myTriangleLength * 2;
            }
            pixelX = 200;
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
