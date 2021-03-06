package Grid;

import javafx.scene.shape.Polygon;
import java.awt.*;
import Cell.Cell;
import view.NeighborsMaker;
import view.ShapeMaker;

public class TriangleGrid extends Grid {

    /**
     * @author Justin Kim
     */
    private int myTriangleLength; // triangle has height of distance, base of 2 * length
    private int GridStartingPoint_X;
    private int GridStartingPoint_Y;

    public TriangleGrid(String filePath, int displaySize, int Starting_X, int Starting_Y, String boundaryType){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();

        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        myNeighborsMaker = new NeighborsMaker("triangle", myXML.getSimulationType(), boundaryType);
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        GridStartingPoint_X = Starting_X;
        GridStartingPoint_Y = Starting_Y;

        calculateTriangleLength();
        System.out.println(numRows);
        System.out.println(numCols);
    }

    /**
     * initializes a set of triangles on a grid
     */
    public void initialize(){
        int pixelX = GridStartingPoint_X;
        int pixelY = GridStartingPoint_Y;

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
            pixelX = GridStartingPoint_X;
            pixelY += myTriangleLength;
        }
    }

    private void calculateTriangleLength(){
        myTriangleLength = myDisplaySize/myCurrentState.length;
    }

    private boolean isOdd(int n){
        return (n % 2 == 1);
    }

}
