
package view;

import java.awt.*;
import java.util.ArrayList;
import Cell.*;

public class NeighborsMaker {

    private String myGridType;
    private String mySimulationType;
    private String myBoundaryType;

    final ArrayList<Point> rectangleTouch = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
        add(new Point( 1, 1));
        add(new Point( 1,-1));
        add(new Point(-1, 1));
        add(new Point(-1,-1));
    }};

    final ArrayList<Point> rectangleSide = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    final ArrayList<Point> triangleTouchPointy = new ArrayList<Point>(){{
        add(new Point( 1, 0));
        add(new Point( 2, 0));
        add(new Point( 2, 1));
        add(new Point( 1, 1));
        add(new Point( 0, 1));
        add(new Point( -1, 1));
        add(new Point( -2, 1));
        add(new Point( -1, 1));
        add(new Point( -2, 0));
        add(new Point( -1, 0));
        add(new Point( -1, -1));
        add(new Point( 0, -1));
        add(new Point( 1, -1));
    }};

    final ArrayList<Point> triangleTouchFlat = new ArrayList<Point>(){{
        add(new Point( -1, 0));
        add(new Point( -1, 1));
        add(new Point( -1, 2));
        add(new Point( 0, 1));
        add(new Point( 0, 2));
        add(new Point( 1, 1));
        add(new Point( 1, 0));
        add(new Point( 1, -1));
        add(new Point( 0, -1));
        add(new Point( 0, -2));
        add(new Point( -1, -2));
        add(new Point( -1, -1));
    }};

    final ArrayList<Point> triangleSideFlat = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0, -1));
        add(new Point( -1, 0));
    }};

    final ArrayList<Point> hexagon = new ArrayList<Point>(){{
        add(new Point(-1, -1));
        add(new Point(-1, 0));
        add(new Point(-1, 1));
        add(new Point(0, -1));
        add(new Point(1, 0));
        add(new Point(0,1));
    }};

    final ArrayList<Point> triangleSidePointy = new ArrayList<Point>(){{
        add(new Point(0, -1));
        add(new Point(0, 1));
        add(new Point(1, 0));
    }};
    

    private ArrayList<Point> myPossibleNeighbors;

    public NeighborsMaker(String gridType, String simulationType, String boundaryType){
        myGridType = gridType;
        mySimulationType = simulationType;
        myBoundaryType = boundaryType;
    }

    public ArrayList<Cell> getNeighbors(Cell cell, Cell[][] currentGrid) {
        getPossibleNeighbors(cell);
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        int cellRow = cell.getRow();
        int cellCol = cell.getCol();

        int cellNeighborRow;
        int cellNeighborCol;

        for (Point rc: myPossibleNeighbors){
            cellNeighborRow = cellRow + (int) rc.getX();
            cellNeighborCol = cellCol + (int) rc.getY();
            if (isSafe(cellNeighborRow, cellNeighborCol, currentGrid)) {
                neighbors.add((Cell) currentGrid[cellNeighborRow][cellNeighborCol]);
            }
            if (!isSafe(cellNeighborRow, cellNeighborCol, currentGrid) && isToroid()){
                neighbors.add((Cell) getToroidNeighbor(cellNeighborRow, cellNeighborCol, currentGrid));
            }

        }
        return neighbors;
    }

    public ArrayList<Cell> getForwardNeighbors(Cell currentCell, Point direction, Cell[][] currentGrid){
        ArrayList<Cell> forwardNeighbors = new ArrayList<Cell>();
        int rowDir = (int) direction.getX();
        int colDir = (int) direction.getY();

        int row = currentCell.getRow();
        int col = currentCell.getCol();
        if (isSafe(row + rowDir, col + colDir, currentGrid)) {
            forwardNeighbors.add(currentGrid[row + rowDir][col + colDir]);
        }

        if (Math.abs(rowDir) == Math.abs(colDir)){
            if (isSafe(row, col + colDir, currentGrid)) {
                forwardNeighbors.add(currentGrid[row][col + colDir]);
            }
            if (isSafe(row + rowDir, col, currentGrid)) {
                forwardNeighbors.add(currentGrid[row + rowDir][col]);
            }
        }

        if (rowDir == 0 || colDir == 0){
            if (rowDir == 0) {
                if (isSafe(row + rowDir + 1, col + colDir, currentGrid)) {
                    forwardNeighbors.add(currentGrid[row + rowDir + 1][col + colDir]);
                }
                if (isSafe(row + rowDir - 1, col + colDir, currentGrid)) {
                    forwardNeighbors.add(currentGrid[row + rowDir - 1][col + colDir]);
                }
            }
            if (colDir == 0){
                if (isSafe(row + rowDir, col + colDir + 1, currentGrid)){
                    forwardNeighbors.add(currentGrid[row + rowDir][col + colDir + 1]);
                }
                if (isSafe(row + rowDir, col + colDir - 1, currentGrid)){
                    forwardNeighbors.add(currentGrid[row + rowDir][col + colDir - 1]);
                }
            }
        }
        return forwardNeighbors;
    }

    private boolean isSafe(int row, int col, Cell[][] currentGrid){
        int gridRowMax = currentGrid.length;
        int gridColMax = currentGrid[0].length;
        if (!((row >= 0 && row < gridRowMax) && (col >= 0 && col < gridColMax))){
            return false;
        }

        int cellState = currentGrid[row][col].getState();

        return (cellState != -2);
    }

    private Cell getToroidNeighbor(int row, int col, Cell[][] currentGrid){
        int newRow = row;
        int newCol = col;
        if (row < 0)
            newRow = currentGrid.length + row;
        else if (row >= currentGrid.length)
            newRow = row - currentGrid.length;
        if (col < 0)
            newCol = currentGrid[0].length + col;
        else if (col >= currentGrid[0].length)
            newCol = col - currentGrid[0].length;
        return currentGrid[newRow][newCol];
    }

    private void getPossibleNeighbors(Cell cell){
        if (myGridType.equals("hexagon")){
            myPossibleNeighbors = hexagon;
        }
        else if (myGridType.equals("rectangle")){
            if (mySimulationType.equals("SpreadingOfFire") || mySimulationType.equals("PredatorPrey")) {
                myPossibleNeighbors = rectangleSide;
            } else {
                myPossibleNeighbors = rectangleTouch;
            }
        }
        else { // triangle
            if (isPointy(cell)){
                if (mySimulationType.equals("SpreadingOfFire") || mySimulationType.equals("PredatorPrey")){
                    myPossibleNeighbors = triangleSidePointy;
                } else {
                    myPossibleNeighbors = triangleTouchPointy;
                }
            } else {
                if (mySimulationType.equals("SpreadingOfFire") || mySimulationType.equals("PredatorPrey")){
                    myPossibleNeighbors = triangleSideFlat;
                } else {
                    myPossibleNeighbors = triangleTouchFlat;
                }
            }
        }
    }

    private boolean isPointy(Cell cell){
        return ((cell.getCol() + cell.getRow()) % 2 == 1);
    }

    private boolean isToroid(){
        return (myBoundaryType.equals("toroid"));
    }



}
