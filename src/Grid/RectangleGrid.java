package Grid;

import javafx.scene.shape.Polygon;
import Cell.Cell;
import view.NeighborsMaker;
import view.ShapeMaker;

import java.awt.Point;


public class RectangleGrid extends Grid {

    private int myRectangleHeight;
    private int myRectangleWidth;
    private int GridStartingPoint_X;
    private int GridStartingPoint_Y;

    public RectangleGrid(String filePath, int displaySize, int Starting_X, int Starting_Y){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();
        myCurrentState = new Cell[numCols][numRows];// for testing
        myNextState = new Cell[numCols][numRows];
        myNeighborsMaker = new NeighborsMaker("rectangle", myXML.getSimulationType());
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        GridStartingPoint_X = Starting_X;
        GridStartingPoint_Y = Starting_Y;
        calculateMyRectangleHeight();
        calculateMyRectangleWidth();
    }

    @Override
    public void initialize(){
        int pixelX = GridStartingPoint_X;
        int pixelY = GridStartingPoint_Y;
        Polygon shape;
        ShapeMaker sm = new ShapeMaker();
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                shape = sm.makeRectangle(new Point(pixelX, pixelY), myRectangleWidth, myRectangleHeight);

                initializeCurrentNext(shape, i, j);

                pixelX += myRectangleWidth;
            }
            pixelX = GridStartingPoint_X;
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
