package view;

import java.util.*;

public class TriangleGrid extends Grid {

    public TriangleGrid(String filePath, int GridSize, String shape){
        super(filePath);
        int numRows = GridSize;
        int numCols =  GridSize * 2;
        myCurrentState = new Cell[numRows][numCols];
        myNextState = new Cell[numRows][numCols];
    }

    public void initialize(){
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<String> states = xml.getStates();
        List<Double> proportionStates = xml.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();

        setGridProportion();
        int numBottomCells;
        int numRows = numBottomCells / 2;
        Cell[numRows][numBottomCells] TrinagleCellGrid;
        int middleIndex = numRows / 2;
        for (int row = 0; row < myCell.; row++) {
            for(int col=0; col<T)

        }

    }


    }
}
