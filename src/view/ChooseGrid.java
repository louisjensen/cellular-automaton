package view;

import Grid.*;

public class ChooseGrid {

    private static final int ScreenSIZE = 580;
    private static final int GridDisplaySize = ScreenSIZE/2;
    private final static int GRID_POS_X = ScreenSIZE /4;
    private final static int GRID_POS_Y = ScreenSIZE/8;

    public Grid setupGrid(String filepath, String shapetype, int space_X, int space_Y, String edgeType){
        Grid newGrid;

        if(shapetype.equals("triangle")){
            newGrid = new TriangleGrid(filepath, GridDisplaySize, GRID_POS_X + space_X,  GRID_POS_Y + space_Y, edgeType);
        }
        else if(shapetype.equals("rectangle")){
            newGrid = new RectangleGrid(filepath, GridDisplaySize, GRID_POS_X +space_X,  GRID_POS_Y +space_Y, edgeType);
        }
        else{
            newGrid = new HexagonGrid(filepath, GridDisplaySize, GRID_POS_X + space_X, GRID_POS_Y +space_Y, edgeType);
        }

        return newGrid;
    }
}
