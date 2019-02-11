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
        put("nest with ants", 4);
        put("food with ants", 5);


    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>() {{
        put(0, Color.WHITE);
        put(1, Color.BROWN);
        put(2, Color.GREEN);
        put(3, Color.GRAY);
        put(4, Color.BROWN);
        put(5, Color.GREEN);
    }};

    private ForagingAntsCell myFoodCell;
    private ForagingAntsCell myNestCell;
    private int myMaxAnts;
    private int myTotalAnts;
    private int myAntsPerTick;
    private int myAntsMaxLives;

    public ForagingAnts(HashMap<String, Double> moreInfoLookupTable, Cell[][] current, Cell[][] next, NeighborsMaker nm) {
        myMoreInfoLookupTable = moreInfoLookupTable;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;

        myMaxAnts = 500;
        myTotalAnts = 0;
        myAntsPerTick = 2;
        myAntsMaxLives = 100;
    }

    public int getState(String stateString) {
        return stateLookupTable.get(stateString);
    }

    @Override
    public int getNextStateOfCell(Cell cell, ArrayList<Cell> neighbors) {
        return 1;

    }


    @Override
    public void update() {

        ForagingAntsCell currentCell;
        ForagingAntsCell nextCell;
        ArrayList<ForagingAntsCell> forwardNeighbors;
        ArrayList<Ants> antsArrayList;

        if (myFoodCell == null || myNestCell == null){
            findNestAndFood();
        }

        generateAntsAtNest();


        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                currentCell = (ForagingAntsCell) myCurrentGrid[row][col];
                nextCell = (ForagingAntsCell) myNextGrid[row][col];
                antsArrayList = currentCell.getMyAntsList();
                Ants myAnt;
                for (int a = 0; a < antsArrayList.size(); a++) {
                    myAnt = antsArrayList.get(a);
                    if (!myAnt.isDead()) {
                        //just got to food source
                        if (currentCell.getState() == 2) {
                            myAnt.pickupfood();
                        }
                        //just got to the nest
                        if (currentCell.getState() == 1) {
                            myAnt.dropfood();
                        }
                        // has food so going back home
                        if (myAnt.gethasfoodstate()) {
                            Point direction = myAnt.getDirection();
                            forwardNeighbors = getForwardNeighbors(currentCell, direction);
                            double max = 0;
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                nextCell = forwardNeighbors.get(b);
                                if (max > forwardNeighbors.get(b).getHomePheromone()) {
                                    max = forwardNeighbors.get(b).getHomePheromone();
                                    nextCell = forwardNeighbors.get(b);
                                }
                            }
                        }
                        //does not have food, so looking for food source
                        else {
                            Point direction = myAnt.getDirection();
                            forwardNeighbors = getForwardNeighbors(currentCell, direction);
                            double max = 0;
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                nextCell = forwardNeighbors.get(b);
                                if (max > forwardNeighbors.get(b).getFoodPheromone()) {
                                    max = forwardNeighbors.get(b).getFoodPheromone();
                                    nextCell = forwardNeighbors.get(b);
                                }
                            }
                        }
                        (nextCell).addAnt(myAnt);
                        myAnt.loseLife();
                        currentCell.removeAnt(myAnt);

                        Point direction = new Point (nextCell.getRow() - currentCell.getRow(),nextCell.getCol() - currentCell.getCol());
                        myAnt.setDirection(direction);
                    }
                    else { // ant is dead
                        currentCell.removeAnt(myAnt);
                        myTotalAnts --;
                    }
                }
            }
        }
        checkGridForAntsAndEvaporate();
    }

    @Override
    public void moveNextToCurrent(){
        ForagingAntsCell current;
        ForagingAntsCell next;
        for (int row = 0; row < myNextGrid.length; row++) {
            for (int col = 0; col < myNextGrid[0].length; col++) {
                current = (ForagingAntsCell) myCurrentGrid[row][col];
                next = (ForagingAntsCell) myNextGrid[row][col];

                current.setFoodPheromone(next.getFoodPheromone());
                current.setFoodPheromone(next.getFoodPheromone());
                copyAnts(current.getMyAntsList(), next.getMyAntsList());
            }
        }

    }

    private void copyAnts(ArrayList<Ants> current, ArrayList<Ants> next){
        current.clear();
        for (Ants ant: next){
            current.add(new Ants(ant));
        }
    }


    private void checkGridForAntsAndEvaporate(){
        ForagingAntsCell next;
        for (int row = 0; row < myNextGrid.length; row++) {
            for (int col = 0; col < myNextGrid[0].length; col++) {
                next = (ForagingAntsCell) myNextGrid[row][col];
                next.evaporate();
                if (next.getState() != 1 && next.getState() != 2) {
                    System.out.println(next.getMyAntsList());

                    if (next.getMyAntsList().size() == 0) { // does not contain ants
                        next.setState(0);
                    } else {
                        next.setState(3); // does contain ants
                    }
                }
            }
        }
        System.out.println("done");
    }

    private void generateAntsAtNest(){
        ArrayList<Cell> neighbors;
        Cell randomCell;
        Random random = new Random();
        for (int i = 0; i < myAntsPerTick; i ++){
            if (myMaxAnts > myTotalAnts) {
                neighbors = getNeighbors(myNestCell);
                randomCell = neighbors.get(random.nextInt(neighbors.size()));
                Ants ant = new Ants(new Point(randomCell.getRow(), randomCell.getCol()), myAntsMaxLives);
                myNestCell.addAnt(ant);
                myTotalAnts ++;
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


    private void findNestAndFood(){
        ForagingAntsCell currentCell;
        for (int row = 0; row < myCurrentGrid.length; row ++){
            for (int col = 0; col < myCurrentGrid[0].length; col ++){
                currentCell = (ForagingAntsCell) myCurrentGrid[row][col];
                if (currentCell.getState() == 1){
                    myNestCell = currentCell;
                }
                if (currentCell.getState() == 2){
                    myFoodCell = currentCell;
                }
            }
        }
    }

}
