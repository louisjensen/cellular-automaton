package Simulation;

import Cell.Cell;
import javafx.scene.paint.Color;
import view.NeighborsMaker;
import Cell.Ants;
import Cell.ForagingAntsCell;
import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.*;

public class ForagingAnts extends Simulation {

    final HashMap<String, Integer> stateLookupTable = new HashMap<String, Integer>() {{
        put("empty", 0);
        put("nest", 1);
        put("food", 2);
        put("has ants", 3);
    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>() {{
        put(0, Color.WHITE);
        put(1, Color.BROWN);
        put(2, Color.GREEN);
        put(3, Color.GRAY);
    }};

    public ForagingAnts(HashMap<String, Double> moreInfoLookupTable, Cell[][] current, Cell[][] next, NeighborsMaker nm) {
        myMoreInfoLookupTable = moreInfoLookupTable;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;
    }

    public int getState(String stateString) {
        return stateLookupTable.get(stateString);
    }

    public Point getNest(String Nest){
        return myMoreInfoLookupTable.get(Nest);

    }
    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        return 1;

    }


    @Override
    public void update() {

        ForagingAntsCell currentCell;
        ForagingAntsCell nextCell;
        ForagingAntsCell randomCell;
        ArrayList<ForagingAntsCell> neighbors;
        ArrayList<Ants> antsArrayList;

        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                currentCell = (ForagingAntsCell) myCurrentGrid[row][col];
                nextCell = (ForagingAntsCell) myNextGrid[row][col];
                antsArrayList = currentCell.getMyAntsList();
                Ants myAnt;
                for (int a = 0; a < antsArrayList.size(); a++) {
                    myAnt = antsArrayList.get(a);
                    //just got to food source
                    if(currentCell.getMyLocation() == food){
                        myAnt.pickupfood();
                    }
                    //just got to the nest
                    if(currentCell.getMyLocation() == nest){
                        myAnt.dropfood();
                    }
                    // has food so going back home
                    if(myAnt.gethasfoodstate()){
                        Point direction = myAnt.getDirection();
                        neighbors = getForwardNeighbors(direction);
                        double max =0;
                        for(int b=0; b<neighbors.size(); b++ ){
                            if(max>neighbors.get(b).getHomePheromone()){
                                max = neighbors.get(b).getHomePheromone();
                                nextCell = neighbors.get(b);
                            }
                        }
                        (nextCell).addAnt(myAnt);
                        currentCell.removeAnt(myAnt);
                    }
                    //does not have food, so looking for food source
                    else{
                        Point direction = myAnt.getDirection();
                        neighbors = getForwardNeighbors(currentCell, direction);
                        double max =0;
                        for(int b=0; b<neighbors.size(); b++ ){
                            if(max>neighbors.get(b).getFoodPheromone()){
                                max = neighbors.get(b).getFoodPheromone();
                                nextCell = neighbors.get(b);
                            }
                        }
                        (nextCell).addAnt(myAnt);
                        currentCell.removeAnt(myAnt);

                    }
                }
            }
        }
    }


    private ArrayList<ForagingAntsCell> getForwardNeighbors(ForagingAntsCell currentCell, Point direction){
        ArrayList<ForagingAntsCell> forwardNeighbors = new ArrayList<ForagingAntsCell>();
        int rowDir = (int) direction.getX();
        int colDir = (int) direction.getY();

        int row = currentCell.getRow();
        int col = currentCell.getCol();
        forwardNeighbors.add((ForagingAntsCell) myNextGrid[row][col]);

        if (Math.abs(rowDir) == Math.abs(colDir)){
            forwardNeighbors.add((ForagingAntsCell) myNextGrid[row][col + colDir]);
            forwardNeighbors.add((ForagingAntsCell) myNextGrid[row + rowDir][col]);
        }

        if (rowDir == 0 || colDir == 0){
            if (rowDir == 0) {
                forwardNeighbors.add((ForagingAntsCell) myNextGrid[row + rowDir + 1][col + colDir]);
                forwardNeighbors.add((ForagingAntsCell) myNextGrid[row + rowDir - 1][col + colDir]);
            }
            if (colDir == 0){
                forwardNeighbors.add((ForagingAntsCell) myNextGrid[row + rowDir][col + colDir + 1]);
                forwardNeighbors.add((ForagingAntsCell) myNextGrid[row + rowDir][col + colDir - 1]);
            }
        }
        return forwardNeighbors;

    }

}
