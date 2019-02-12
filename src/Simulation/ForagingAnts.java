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
        put("hasAnts", 3);
        put("nestWAnts", 4);
        put("foodWAnts", 5);


    }};

    final HashMap<Integer, Color> colorLookupTable = new HashMap<Integer, Color>() {{
        put(0, Color.WHITE);
        put(1, Color.BROWN);
        put(2, Color.GREEN);
        put(3, Color.PINK);
        put(4, Color.BROWN);
        put(5, Color.GREEN);
    }};

    private ForagingAntsCell myFoodCell;
    private ForagingAntsCell myNestCell;
    private int myMaxAnts;
    private int myTotalAnts;
    private int myAntsPerTick;
    private int myAntsMaxLives;
    private int myMaxAntsPerCell;

    public ForagingAnts(HashMap<String, Double> moreInfoLookupTable, Cell[][] current, Cell[][] next, NeighborsMaker nm) {
        myMoreInfoLookupTable = moreInfoLookupTable;
        myStateLookupTable = stateLookupTable;
        myColorLookupTable = colorLookupTable;
        myNeighborsMaker = nm;
        myCurrentGrid = current;
        myNextGrid = next;

        myTotalAnts = 0;
        myMaxAnts = myMoreInfoLookupTable.get("maxAnts").intValue();
        myAntsPerTick = myMoreInfoLookupTable.get("antsBornPerStep").intValue();
        myAntsMaxLives = myMoreInfoLookupTable.get("antLifetime").intValue();
        myMaxAntsPerCell = myMoreInfoLookupTable.get("maxAntsPerLocation").intValue();
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
        ArrayList<Cell> forwardNeighbors;
        ArrayList<Ants> antsArrayList;
        Random random = new Random();
        if (myFoodCell == null || myNestCell == null){
            findNestAndFood();
        }

        generateAntsAtNest();


        //System.out.println(myNestCell.getRow() + " " + myNestCell.getCol() + " " + myNestCell.getMyAntsList().size());
        System.out.println("Total ants: " + myTotalAnts);


        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                currentCell = (ForagingAntsCell) myCurrentGrid[row][col];
                antsArrayList = currentCell.getMyAntsList();
                Ants myAnt;
                ArrayList<Ants> antsToRemove = new ArrayList<Ants>();
                for (int a = 0; a < antsArrayList.size(); a++) {
                    nextCell = (ForagingAntsCell) myNextGrid[row][col];
                    //System.out.println(currentCell.getRow() + " " + currentCell.getCol()  + " has " + currentCell.getMyAntsList().size() + " ants");
                    myAnt = antsArrayList.get(a);
                    if (!myAnt.isDead()) {
                        //just got to food source
                        if (currentCell.getRow() == myFoodCell.getRow() && currentCell.getCol() == myFoodCell.getCol()) {
                            myAnt.pickupFood();
                            //nextCell.setState(2);
                        }
                        //just got to the nest
                        if (currentCell.getRow() == myNestCell.getRow() && currentCell.getCol() == myNestCell.getCol()) {
                            myAnt.dropFood();
                            //nextCell.setState(1);

                        }

                        Point direction = myAnt.getDirection();
                        //System.out.println("direction: " + direction.getX() + " " + direction.getY());
                        forwardNeighbors = myNeighborsMaker.getForwardNeighbors((Cell) nextCell, direction, myNextGrid);
                        if (forwardNeighbors.size() == 0){
                            forwardNeighbors = myNeighborsMaker.getNeighbors((Cell) currentCell, myNextGrid);
                        }


                        if (myAnt.getHasFoodState()) {
                            ForagingAntsCell temp;
                            nextCell = (ForagingAntsCell) forwardNeighbors.get(0);
                            double max = nextCell.getHomePheromone();
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                temp = (ForagingAntsCell) forwardNeighbors.get(b);
                                if (max < temp.getHomePheromone() && temp.getMyAntsList().size() < myMaxAntsPerCell) {
                                    max = temp.getHomePheromone();
                                    nextCell = temp;
                                }
                            }
                            if (max == 0){
                                int rand = random.nextInt(forwardNeighbors.size());
                                nextCell = (ForagingAntsCell) forwardNeighbors.get(rand);
                            }
                        }
                        //does not have food, so looking for food source
                        else {
                            //System.out.println("Ant " + a + ": " + myAnt + " is looking for food");
                            ForagingAntsCell temp;
                            nextCell = (ForagingAntsCell) forwardNeighbors.get(0);
                            double max = nextCell.getFoodPheromone();
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                temp = (ForagingAntsCell) forwardNeighbors.get(b);
                                if (max < temp.getFoodPheromone() && temp.getMyAntsList().size() < myMaxAntsPerCell) {
                                    max = temp.getFoodPheromone();
                                    nextCell = temp;
                                }
                            }
                            if (max == 0) {
                                int rand = random.nextInt(forwardNeighbors.size());
                                nextCell = (ForagingAntsCell) forwardNeighbors.get(rand);
                            }


                        }
                        boolean canMove = false;
                        for (Cell cell: forwardNeighbors){
                            if (((ForagingAntsCell) cell).getMyAntsList().size() < myMaxAntsPerCell){
                                canMove = true;
                                break;
                            }
                        }
                        if (canMove) {
                            nextCell.addAnt(myAnt);
                        }
                        myAnt.loseLife();

                        //currentCell.removeAnt(myAnt);

                        direction = new Point (nextCell.getRow() - currentCell.getRow(),nextCell.getCol() - currentCell.getCol());
                        myAnt.setDirection(direction);
                    }
                    else { // ant is dead
                        antsToRemove.add(myAnt);
                        //currentCell.removeAnt(myAnt);
                        //myTotalAnts --;
                    }
                }
                removeAntsFromCell(currentCell, antsToRemove);
                //antsArrayList.clear();
            }
        }

        updateStatesAndEvaporate();

        updateColor();
        updateTotalNumAnts();

    }

    private void removeAntsFromCell(ForagingAntsCell cell, ArrayList<Ants> antsToRemove){
        for (Ants ant: antsToRemove){
            cell.removeAnt(ant);
            myTotalAnts--;
        }
        antsToRemove.clear();
    }

    private void printNextStates(){
        ForagingAntsCell next;

        for (int row = 0; row < myNextGrid.length; row++) {
            for (int col = 0; col < myNextGrid[0].length; col++) {
                next = (ForagingAntsCell) myNextGrid[row][col];
                System.out.println("row: " + row  + " col: " + col + " state: "+ next.getState());
            }
        }
    }

    private void printCurrentStates(){
        ForagingAntsCell current;
        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                current = (ForagingAntsCell) myCurrentGrid[row][col];
                System.out.println("row: " + row  + " col: " + col + " state: "+ current.getState() + " numAnts: " + current.getMyAntsList().size());
            }
        }
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
                current.setHomePheromone(next.getHomePheromone());
                copyAnts(current.getMyAntsList(), next.getMyAntsList());
                current.setState(next.getState());
                next.setState(-1);

            }
        }

    }

    private void copyAnts(ArrayList<Ants> current, ArrayList<Ants> next){

        current.clear();
        for (Ants ant: next) {
            current.add(ant);
        }
        next.clear();
    }


    private void updateStatesAndEvaporate(){
        ForagingAntsCell next;
        ForagingAntsCell current;
        for (int row = 0; row < myNextGrid.length; row++) {
            for (int col = 0; col < myNextGrid[0].length; col++) {
                next = (ForagingAntsCell) myNextGrid[row][col];
                current = (ForagingAntsCell) myCurrentGrid[row][col];
                next.increaseHomePheromone();
                next.increaseFoodPheromone();
                next.evaporate();
                updateState(current, next);

            }
        }
    }

    private void updateState(ForagingAntsCell current, ForagingAntsCell next){
        int currentState = current.getState();
        int numAnts = current.getMyAntsList().size();

        if (currentState == 0){
            if (numAnts == 0)
                next.setState(0);
            else
                next.setState(3);
        }
        if (currentState == 3){
            if (numAnts == 0)
                next.setState(0);
            else
                next.setState(3);
        }
        if (currentState == 1){
            if (numAnts == 0)
                next.setState(1);
            else
                next.setState(4);
        }
        if (currentState == 2){
            if (numAnts == 0)
                next.setState(2);
            else
                next.setState(5);
        }
        if (currentState == 4){
            if (numAnts == 0)
                next.setState(1);
            else
                next.setState(4);
        }
        if (currentState == 5){
            if (numAnts == 0)
                next.setState(2);
            else
                next.setState(5);
        }


    }

    private void updateTotalNumAnts() {
        int total = 0;
        ForagingAntsCell current;
        for (int row = 0; row < myNextGrid.length; row++) {
            for (int col = 0; col < myNextGrid[0].length; col++) {
                current = (ForagingAntsCell) myNextGrid[row][col];
                total += current.getMyAntsList().size();
            }
        }
        myTotalAnts = total;
        System.out.println("Actual tot: " + total);
    }

    private void generateAntsAtNest(){
        ArrayList<Cell> neighbors;
        ForagingAntsCell randomCell;
        Random random = new Random();
        for (int i = 0; i < myAntsPerTick; i ++){
            neighbors = getNeighbors(myNestCell);
            randomCell = (ForagingAntsCell) neighbors.get(random.nextInt(neighbors.size()));
            if (myMaxAnts > myTotalAnts && randomCell.getMyAntsList().size() < myMaxAntsPerCell) {
                Ants ant = new Ants(new Point(randomCell.getRow() - myNestCell.getRow(), randomCell.getCol() - myNestCell.getCol()), myAntsMaxLives);
                myNestCell.addAnt(ant);
                //myTotalAnts ++;
            }
        }
    }




    private void findNestAndFood(){
        Cell currentCell;
        for (int row = 0; row < myCurrentGrid.length; row ++){
            for (int col = 0; col < myCurrentGrid[0].length; col ++){
                currentCell = myCurrentGrid[row][col];
                if (currentCell.getState() == 1){
                    myNestCell = (ForagingAntsCell) currentCell;
                    System.out.println("nest: " + myNestCell.getRow() + " " + myNestCell.getCol() + " " + myNestCell);
                }
                if (currentCell.getState() == 2){
                    myFoodCell = (ForagingAntsCell) currentCell;
                    System.out.println("food: " + myFoodCell.getRow() + " " + myFoodCell.getCol() + " " + myFoodCell);

                }
            }
        }
    }

}
