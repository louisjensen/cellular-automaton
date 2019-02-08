package view;

import javafx.scene.shape.Polygon;

import java.awt.*;

public class HexagonGrid extends Grid{

    private int d;

    public HexagonGrid(String filePath, int displaySize){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();
        myCurrentState = new Cell[numRows][numCols];
        System.out.println(myCurrentState.length);
        myNextState = new Cell[numRows][numCols];
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        calculateD();
        System.out.println(d);
    }

    private void calculateD(){
        d = myDisplaySize/ (4 * myCurrentState.length);
    }

    @Override
    public void initialize(){

        //Starting Points
        int pixelX = 200;
        int pixelY = 150;
        Polygon shape;
        Cell current;
        Cell next;
        ShapeMaker sm = new ShapeMaker();
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                shape = sm.makeHexagon(new Point(pixelX, pixelY), d) ;
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

                pixelX += 6*d;
            }
            if(row % 2 == 0){
                pixelX = 200 + 4*d;
            }
            else{
                pixelX = 200;
            }
            pixelY += 4*d;
        }
    }

}

