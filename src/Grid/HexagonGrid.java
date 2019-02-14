package Grid;

import javafx.scene.shape.Polygon;
import Cell.Cell;
import view.NeighborsMaker;
import view.ShapeMaker;
import java.awt.*;

public class HexagonGrid extends Grid {
    /**
     * @author Louis Lee
     */

    private int d;
    private int GridStartingPoint_X;
    private int GridStartingPoint_Y;

    public HexagonGrid(String filePath, int displaySize, int Starting_X, int Starting_Y, String boundaryType){
        super(filePath, displaySize);
        int numRows = myXML.getGridX();
        int numCols = myXML.getGridY();
        myCurrentState = new Cell[numRows][numCols];
        System.out.println(myCurrentState.length);
        myNextState = new Cell[numRows][numCols];
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        myNeighborsMaker = new NeighborsMaker("hexagon", myXML.getSimulationType(), boundaryType);
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        GridStartingPoint_X = Starting_X;
        GridStartingPoint_Y = Starting_Y;

        calculateD();
        System.out.println(d);
    }

    private void calculateD(){
        d = myDisplaySize/ (3 * myCurrentState[0].length);
    }

    /**
     * Initializes a set of hexagons on a grid
     */
    @Override
    public void initialize(){
        //Starting Points
        int pixelX = GridStartingPoint_X;
        int pixelY = GridStartingPoint_Y;
        Polygon shape;
        ShapeMaker sm = new ShapeMaker();
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                shape = sm.makeHexagon(new Point(pixelX, pixelY), d);

                initializeCurrentNext(shape, row, col);
                pixelX += 2*d;
            }
            if(row % 2 == 0){
                pixelX = GridStartingPoint_X + d;
            }
            else{
                pixelX = GridStartingPoint_X;
            }
            pixelY += 3*d;
        }
    }

}

