package Grid;

import Cell.*;
import Simulation.*;
import javafx.event.EventHandler;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import javafx.scene.Group;
import view.*;

import java.util.List;

public abstract class Grid {

    public Cell[][] myCurrentState;
    public Cell[][] myNextState;
    public Simulation mySimulation;
    public int myDisplaySize;
    public XMLParser myXML;
    public NeighborsMaker myNeighborsMaker;


    public Grid(String filePath, int displaySize) {
        myXML = new XMLParser(filePath);
        if(myXML == null){
            System.out.print("xml is null");
        }
        System.out.println(filePath);
        myDisplaySize = displaySize;
    }

    public abstract void initialize();

    public HashMap<String, Integer> getSimulationMap(){
        return mySimulation.myStateLookupTable;
    }


    public Simulation getSimulation(String sim, HashMap<String, Double> map) {
        if (sim.equals("GameOfLife")) {
            return new GameOfLife(myCurrentState, myNextState, myNeighborsMaker);
        }
        if (sim.equals("Percolation")) {
            return new Percolation(myCurrentState, myNextState, myNeighborsMaker);
        }
        if (sim.equals("SpreadingOfFire")) {
            return new SpreadingOfFire(map, myCurrentState, myNextState, myNeighborsMaker);
        }
        if (sim.equals("Segregation")) {
            return new Segregation(map, myCurrentState, myNextState, myNeighborsMaker);
        }
        if (sim.equals("PredatorPrey")) {
            return new PredatorPrey(map, myCurrentState, myNextState, myNeighborsMaker);
        }
        if (sim.equals("RPS")){
            return new RPS(map,myCurrentState, myNextState, myNeighborsMaker);
        }
        else{
            return new ForagingAnts(map, myCurrentState, myNextState, myNeighborsMaker);
        }
    }

    public Cell getSpecificCell(Polygon shape) {
        if (myXML.getSimulationType().equals("GameOfLife")) {
            return new GameOfLifeCell(shape);
        }
        else if (myXML.getSimulationType().equals("SpreadingOfFire")){
            return new SpreadingOfFireCell(shape);
        }
        else if (myXML.getSimulationType().equals("Segregation")){
            return new SegregationCell(shape);
        } else if (myXML.getSimulationType().equals("Percolation")){
            return new PercolationCell(shape);
        } else if (myXML.getSimulationType().equals("PredatorPrey")){
            return new PredatorPreyCell(shape);
        } else if(myXML.getSimulationType().equals("RPS")){
            return new RPSCell(shape);
        } else{
            return new ForagingAntsCell(shape);
        }
    }

    public void initializeCurrentNext(Polygon shape, int row, int col){
        myCurrentState[row][col] = getSpecificCell(shape);
        Cell current = myCurrentState[row][col];
        initializeCell(current, row, col);

        myNextState[row][col] = getSpecificCell(shape);
        Cell next = myNextState[row][col];
        initializeCell(next, row, col);
    }

    private void initializeCell(Cell cell, int row, int col){
        cell.setState(-1);
        cell.setRow(row);
        cell.setCol(col);
    }


    public void moveNexttoCurrent() {
        mySimulation.moveNextToCurrent();
    }

    public boolean checkGameEnding() {
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                //return Boolean instead of this
                if (myCurrentState[i][j].getState() != myNextState[i][j].getState()) {
                    return false;
                }
            }
        }
        return true;
    }


    public Cell[][] getMyCurrentState() {
        return myCurrentState;
    }

    public Cell[][] getMyNextState() {
        return myNextState;
    }


    public void updateGrid() {
        mySimulation.update();
    }

    public String getSimulationName() {
        return myXML.getSimulationType();
    }

    public void display(Group root) {
        Cell current;
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                current = myCurrentState[i][j];

                if (!root.getChildren().contains(current.getShape()) && current.getState() != -2) {
                    root.getChildren().add(current.getShape());
                }
            }
        }
    }

    public void unDisplay(Group root) {
        Cell current;
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                current = myCurrentState[i][j];
                if (root.getChildren().contains(current.getShape())) {
                    root.getChildren().remove(current.getShape());
                }
            }
        }
    }


    public void setInitialGridColors() {
        if (myXML.isItBasedOnStates()){
            setColorsBasedOnStates();
            return;
        }
        setColorsBasedOnNumbers();
    }

    private void setColorsBasedOnStates(){
        int cell = 0;
        Cell current;
        List<Double> proportionStates = myXML.getStateProportions();
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                current = myCurrentState[i][j];
                current.setState(proportionStates.get(cell).intValue());
                current.setColor(stateToColorMap.get(current.getState()));
                cell++;
            }
        }
    }

    private void setColorsBasedOnNumbers(){
        List<Double> proportionStates = myXML.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();

        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<String> states = myXML.getStates();
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        Random rand = new Random();
        Cell current;
        int randInt;
        int currentTotal = 0;
        int percentage;
        int totalNum = 0;
        for(int i = 0; i < proportionStates.size(); i++){
            totalNum += proportionStates.get(i);
        }

        for (int i = 0; i < proportionStates.size(); i++) {
           /* percentage = (currentTotal + (int) (proportionStates.get(i) / totalNum * 100));
            if (i == proportionStates.size()-1){
                percentage = 100;
            }
            percentageStates.add(percentage);
            System.out.println(percentage + "percentages");
            currentTotal = percentage; */
            percentage = (currentTotal + (proportionStates.get(i)).intValue());
            percentageStates.add(percentage);
            //System.out.println(percentage + "percentages");
            currentTotal = percentage;

        }

        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                if (myCurrentState[i][j].getState() != -2) {
                    current = myCurrentState[i][j];
                    while(current.getState()<0) {
                        randInt = rand.nextInt(currentTotal) + 1;
                        for (int k = 0; k < percentageStates.size(); k++) {
                            if (randInt <= percentageStates.get(k) && proportionStates.get(k) > 0) {
                                current.setState(stateLookupTable.get(states.get(k)));
                                current.setColor(stateToColorMap.get(current.getState()));
                                proportionStates.set(k, proportionStates.get(k) - 1);
                                break;
                            }

                        }
                    }

                }
            }
        }
    }
}