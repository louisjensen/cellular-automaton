package view;

import Grid.*;

public class ChooseGrid {

    /**
     * @author:  Louis Lee
     */

    private static final int ScreenSIZE = 580;
    private static final int GridDisplaySize = ScreenSIZE/2;
    private final static int GRID_POS_X = ScreenSIZE /4;
    private final static int GRID_POS_Y = ScreenSIZE/8;

    /**
     * Sets up new Grid based on the shapetype and edgetype
     * @param filepath
     * @param shapetype
     * @param space_X
     * @param space_Y
     * @param edgeType
     * @return a new grid with specific shapetype and edgetype
     */
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
