package Grid;

import Cell.*;
import Simulation.*;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import view.*;
import java.util.Collections;


import java.util.List;

public abstract class Grid {

    public Cell[][] myCurrentState;
    public Cell[][] myNextState;
    public Simulation mySimulation;
    public int myDisplaySize;
    public XMLParser myXML;
    public NeighborsMaker myNeighborsMaker;

    /**
     * initializes a grid object with a specific display size and other parameters based on the xml file given by the filepath
     * @param filePath
     * @param displaySize
     */
    public Grid(String filePath, int displaySize) {
        myXML = new XMLParser(filePath);
        if(myXML == null){
            System.out.print("xml is null");
        }
        System.out.println(filePath);
        myDisplaySize = displaySize;
    }

    /**
     * places all of the shapes for each cell on a 2D grid (different initialization method for all three shapes)
     */
    public abstract void initialize();

    public Map<String, Integer> getSimulationMap(){
        return mySimulation.myStateLookupTable;
    }

    /**
     * creates a new instance of a simulation based on the simulation string type
     * @param sim string code for simulation
     * @param map additional parameters for the state of the simulation
     * @return specific simulation instance
     */
    public Simulation getSimulation(String sim, Map<String, Double> map) {
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

    /**
     * creates a new instance of a cell based on the simulation type
     * @param shape
     * @return specific cell instance
     */
    public Cell getSpecificCell(Polygon shape) {
        if (myXML.getSimulationType().equals("GameOfLife")) {
            return new GameOfLifeCell(shape);
        }
        else if (myXML.getSimulationType().equals("SpreadingOfFire")){
            return new SpreadingOfFireCell(shape);
        }
        else if (myXML.getSimulationType().equals("Segregation")){
            return new SegregationCell(shape);
        }
        else if (myXML.getSimulationType().equals("Percolation")){
            return new PercolationCell(shape);
        }
        else if (myXML.getSimulationType().equals("PredatorPrey")){
            return new PredatorPreyCell(shape);
        }
        else if(myXML.getSimulationType().equals("RPS")){
            return new RPSCell(shape);
        }
        else{
            return new ForagingAntsCell(shape);
        }
    }

    /**
     * initializes the cell object for each grid index in myCurrentState and myNextState
     * @param shape Polygon object to be used for the cell
     * @param row row index in the grid
     * @param col col index in the grid
     */
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

    /**
     * calls the simulation's moveNextToCurrent method. Transfers information from the next grid state to the current state and resets the cells in next state
     */
    public void moveNexttoCurrent() {
        mySimulation.moveNextToCurrent();
    }

    /**
     * returns true if the information in myNextState is the same as the information in myCurrentState (this occurs before moveNexttoCurrent is called
     * @return
     */
    public boolean checkGameEnding() {
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                /*Cannot return boolean because shouldn't return
                  True unless it loops through every cell*/
                if (myCurrentState[i][j].getState() != myNextState[i][j].getState()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * returns myCurrentState
     * @return
     */
    public Cell[][] getMyCurrentState() {
        return myCurrentState;
    }

    /**
     * calls the simulation's update method
     */
    public void updateGrid() {
        mySimulation.update();
    }

    /**
     * gets the name of the simulation
     * @return
     */
    public String getSimulationName() {
        return myXML.getSimulationType();
    }

    /**
     * adds all of the Polygon objects in myCurrentState to root
     * @param root
     */
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

    /**
     * removes all of the Polygon objects in myCurrentState from root
     * @param root
     */
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

    /**
     * sets the initial grid colors
     */
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
        Map<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                current = myCurrentState[i][j];
                current.setState(proportionStates.get(cell).intValue());
                current.setColor(stateToColorMap.get(current.getState()));
                cell++;
            }
        }
    }

    // sets initial states of the grid. For example, in a 10x10 Spreading of Fire simulation, there should be X green, Y yellow, and Z red cells. Where X + Y + Z = 10x10
    private void setColorsBasedOnNumbers(){
        List<Double> proportionStates = myXML.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();

        Map<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<String> states = myXML.getStates();
        Map<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        Random rand = new Random();
        int randInt;
        int currentTotal = 0;
        int percentage;
        int totalNum = 0;
        for(int i = 0; i < proportionStates.size(); i++){
            totalNum += proportionStates.get(i);
        }

        for (int i = 0; i < proportionStates.size(); i++) {
            percentage = (currentTotal + (proportionStates.get(i)).intValue());
            percentageStates.add(percentage);
            //System.out.println(percentage + "percentages");
            currentTotal = percentage;
        }

        List<Cell> allCells = getAllCells();
        for (Cell current: allCells) {
            if (current.getState() != -2) {
                while (current.getState() < 0) {
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

    private List<Cell> getAllCells() {
        List<Cell> cells = new ArrayList<Cell>();
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                cells.add(myCurrentState[row][col]);
            }
        }
        Collections.shuffle(cells);
        return cells;
    }

}