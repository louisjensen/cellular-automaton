package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.List;

public class Grid { // make abstract later

    private Cell[][] myCurrentState;
    private Cell[][] myNextState;
    private Simulation mySimulation;
    private GridPane myGridPane;
    private String myFilePath;
    private int myDisplaySize;
    private int myGridWidth;
    private int myGridHeight;
    private int myCellSize;
    private String SimulationName;
    private Random rand = new Random();
    int randInt;
    private XMLParser xml;

  /*  final HashMap<String, Simulation> simulationLookupTable = new HashMap<String, Simulation>(){{
            put("GameOfLife", new GameOfLife());
            //put("segregation", new Simulation);
         //   put("SpreadingOfFire", new SpreadingOfFire(xml.getRandomInfo()));
            put("Percolation", new Percolation());
        }}; */

    private Simulation getSimulation(String sim, HashMap<String, Double> map){
        if (sim.equals("GameOfLife")){
            return new GameOfLife();
        }
        if (sim.equals("Percolation")){
            return new Percolation();
        }
        if (sim.equals("SpreadingOfFire")){
            return new SpreadingOfFire(map);
        }
        if(sim.equals("Segregation")){
            return new Segregation(map);
        }
       /* if(sim.equals("PredatorPrey")){
            return new PredatorPrey(map);
        }*/
        return null;
    }

    public Grid(String filePath, int displaySize){
        myFilePath = filePath;
        xml = new XMLParser(myFilePath);

        SimulationName = xml.getSimulationType();
        //construct grid from XML File
        myDisplaySize = displaySize;
       // mySimulation = simulationLookupTable.get(xml.getSimulationType());
        mySimulation = getSimulation(xml.getSimulationType(), xml.getRandomInfo());
        myGridWidth = xml.getGridX();
        //myGridWidth = 20;// for testing
        myGridHeight = xml.getGridY();
     //   myGridHeight = 20;// for testing
        myCurrentState = new Cell[myGridWidth][myGridHeight];// for testing
        myNextState = new Cell[myGridWidth][myGridHeight];
        calculateCellSize();
        myGridPane = new GridPane();
        initialize();
    }

    /**
     * returns myGridPane
     * @return GridPane that contains all of the cells
     */
    public GridPane getGridPane() { // will depend on what type of grid it is
        setGridPane();
        return myGridPane;
    }

    // configure myGridPane based on the cells in myCurrentState
    public void setGridPane(){
        Cell currentCell;
        Color color;
        HashMap<Integer, Color> stateToColorMap = mySimulation.getMyColorLookupTable();

        for (int row = 0; row < myCurrentState.length; row ++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                currentCell = myCurrentState[row][col];
                color = stateToColorMap.get(currentCell.getState());
                currentCell.setColor(color);
                if (!myGridPane.getChildren().contains(currentCell.getImage())) {
                    myGridPane.add(currentCell.getImage(), row, col);
                }
            }
        }
        moveNexttoCurrent();
    }

    private void moveNexttoCurrent() {
        for (int row = 0; row < myCurrentState.length; row++) {
            for (int col = 0; col < myCurrentState[0].length; col++) {
                myCurrentState[row][col] = myNextState[row][col];

            }
        }
    }
    private void initializeGrid(){ // FOR TESTING PURPOSES ONLY
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                myCurrentState[row][col] = new Cell(row, col, myCellSize, 1);
            }
        }
    }

    //because we make new cells each time this doesn't work -- need to find a way
    public boolean checkGameEnding(){
        if(myCurrentState == myNextState){
            return true;
        }
        return false;
    }

    // calculates the size of a cell based on how big the display is and the number of cells to fit
    private void calculateCellSize(){
        myCellSize = myDisplaySize/myGridWidth;
    }


    public String getSimulationName(){
        return SimulationName;
    }


    public void updateGrid(){
        Cell myCell;
        ArrayList<Cell> neighbors;
        for (int row = 0; row < myGridWidth; row++){
            for (int col = 0; col < myGridHeight; col++){
                //myCell = new Cell(i, j, myCellSize, myCurrentState[i][j].getState());
                myCell = myCurrentState[row][col];
                neighbors = mySimulation.getNeighbors(myCell, myCurrentState);
                myNextState[row][col] = mySimulation.getNextStateOfCell(myCell, neighbors);
            }
        } 
    }

    public void initialize(){
        //reread file and create a new grid
      //  HashMap<String, Double> map = xml.getMap();
      //  HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        List<String> states = xml.getStates();
        List<Double> proportionStates = xml.getStateProportions();
        List<Integer> percentageStates = new ArrayList<>();
        int currentTotal = 0;
        int percentage;
        for (int i = 0; i < proportionStates.size(); i++){
            percentage = (currentTotal + (int) (proportionStates.get(i) * 100));
            percentageStates.add(percentage);
            currentTotal = percentage;
        }
        for (int i = 0; i < myGridWidth; i++){
            for (int j = 0; j < myGridHeight; j++){
                randInt = rand.nextInt(100) + 1;
                for (int k = 0; k < percentageStates.size(); k++){
                    if(randInt <= percentageStates.get(k)) {
                        myCurrentState[i][j] = new Cell(i, j, myCellSize, k);
                        myNextState[i][j] = new Cell(i, j, myCellSize, k);
                        break;
                    }
                }
            }
        }

        /*
        double propState1 = 0.1;
        double propState2 = 0.1;
        double propState3 = 0.8;
        int numState1 = (int) (propState1 * 100);
        int numState2 = (int) (propState2 * 100) + numState1;
        int numState3 = (int) (propState3 * 100) + numState2;
        for (int row = 0; row < myCurrentState.length; row ++){
            for (int col = 0; col < myCurrentState[0].length; col ++){
                    randInt = rand.nextInt(100) + 1;
                    System.out.println(randInt);
                    if(randInt < numState1) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 1);
                    } else if(randInt < numState2) {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 2);
                    } else {
                        myCurrentState[row][col] = new Cell(row, col, myCellSize, 3);
                    }
            }
        }
        */
    }

}
