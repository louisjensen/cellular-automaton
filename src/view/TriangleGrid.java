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

    }
}
