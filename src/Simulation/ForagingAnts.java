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
        ArrayList<Cell> forwardNeighbors;
        ArrayList<Ants> antsArrayList;
        Random random = new Random();
        //printCurrentStates();
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

                for (int a = 0; a < antsArrayList.size(); a++) {
                    nextCell = (ForagingAntsCell) myNextGrid[row][col];
                    //System.out.println(currentCell.getRow() + " " + currentCell.getCol()  + " has " + currentCell.getMyAntsList().size() + " ants");
                    myAnt = antsArrayList.get(a);
                    if (!myAnt.isDead()) {
                        //just got to food source
                        if (currentCell.getState() == 2) {
                            myAnt.pickupFood();
                            nextCell.setState(2);
                        }
                        //just got to the nest
                        if (currentCell.getState() == 1) {
                            myAnt.dropFood();
                            nextCell.setState(1);

                        }

                        Point direction = myAnt.getDirection();
                        //System.out.println("direction: " + direction.getX() + " " + direction.getY());
                        forwardNeighbors = myNeighborsMaker.getForwardNeighbors((Cell) nextCell, direction, myNextGrid);
                        if (forwardNeighbors.size() == 0){
                            forwardNeighbors = myNeighborsMaker.getNeighbors((Cell) currentCell, myCurrentGrid);
                        }
                        //for (Cell cell: forwardNeighbors){
                        //    System.out.println(cell.getRow() + " " + cell.getCol());
                        //}
                        //System.out.println("Forward: " + forwardNeighbors.size());
                        // has food so going back home
                        if (myAnt.getHasFoodState()) {
                            //System.out.println("Ant " + a + ": " + myAnt + " is going home");

                            double max = 0;
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                nextCell = (ForagingAntsCell) forwardNeighbors.get(b);
                                if (max > nextCell.getHomePheromone()) {
                                    max = nextCell.getHomePheromone();
                                    nextCell = (ForagingAntsCell) forwardNeighbors.get(b);
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

                            double max = 0;
                            for (int b = 0; b < forwardNeighbors.size(); b++) {
                                nextCell = (ForagingAntsCell) forwardNeighbors.get(b);
                                if (max > nextCell.getFoodPheromone()) {
                                    max = nextCell.getFoodPheromone();
                                    nextCell = (ForagingAntsCell) forwardNeighbors.get(b);
                                }
                            }
                            if (max == 0) {
                                int rand = random.nextInt(forwardNeighbors.size());
                                nextCell = (ForagingAntsCell) forwardNeighbors.get(rand);
                            }


                        }
                        nextCell.addAnt(myAnt);
                        myAnt.loseLife();
                        //currentCell.removeAnt(myAnt);

                        direction = new Point (nextCell.getRow() - currentCell.getRow(),nextCell.getCol() - currentCell.getCol());
                        myAnt.setDirection(direction);
                    }
                    else { // ant is dead
                        currentCell.removeAnt(myAnt);
                        myTotalAnts --;
                    }
                }
                //antsArrayList.clear();
            }
        }
        System.out.println("\nbefore: ");
        System.out.println("Nest: " + myNestCell.getRow() + " " + myNestCell.getCol() + " " + myNestCell.getState());
        System.out.println("Food: " + myFoodCell.getRow() + " " + myFoodCell.getCol() + " " + myFoodCell.getState());
        updateStatesAndEvaporate();
        updateColor();
        System.out.println("after: ");
        System.out.println("Nest: " + myNestCell.getRow() + " " + myNestCell.getCol() + " " + myNestCell.getState());
        System.out.println("Food: " + myFoodCell.getRow() + " " + myFoodCell.getCol() + " " + myFoodCell.getState());

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
                //next.setState(-1);

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
                updateState(next, current);

            }
        }
        System.out.println("done");
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
        System.out.println("row: " + current.getRow()  + " col: " + current.getCol() + " state: "+ current.getState() + " numAnts: " + current.getMyAntsList().size());
        System.out.println("row: " + next.getRow()     + " col: " + next.getCol()    + " state: "+ next.getState() +    " numAnts: " + next.getMyAntsList().size() + "\n");

        /*
        //System.out.println(next.getRow() + " " + next.getCol() + " " +next.getMyAntsList().size());
        if (current.getState() == 3 || current.getState() == 0) {
            //System.out.println(next.getRow() + " " + next.getCol() + " " +next.getMyAntsList().size());
            //System.out.println(next.getMyAntsList().size());
            if (next.getMyAntsList().size() == 0) { // does not contain ants
                next.setState(0);

                //current.setState(0);
            } else {
                next.setState(3); // does contain ants
                //current.setState(3);
            }
        }
        else {
            if (current.getState() == 1  || current.getState() == 4 && current.getMyAntsList().size() > 0){ // nest with ants
                next.setState(4);
            }
            else if (current.getState() == 1 || current.getState() == 4 && current.getMyAntsList().size() == 0) {
                next.setState(1);
            }
            else if (current.getState() == 2 || current.getState() == 5 && current.getMyAntsList().size() > 0){ // food with ants
                next.setState(5);
            }
            else if (current.getState() == 2 || current.getState() == 5 && current.getMyAntsList().size() == 0){
                next.setState(2);
            }
        }
        */
    }

    private void generateAntsAtNest(){
        ArrayList<Cell> neighbors;
        Cell randomCell;
        Random random = new Random();
        for (int i = 0; i < myAntsPerTick; i ++){
            if (myMaxAnts > myTotalAnts) {
                neighbors = getNeighbors(myNestCell);
                randomCell = neighbors.get(random.nextInt(neighbors.size()));
                Ants ant = new Ants(new Point(randomCell.getRow() - myNestCell.getRow(), randomCell.getCol() - myNestCell.getCol()), myAntsMaxLives);
                myNestCell.addAnt(ant);
                myTotalAnts ++;
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
