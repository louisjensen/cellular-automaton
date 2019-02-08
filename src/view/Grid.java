package view;

import javafx.scene.shape.Polygon;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.io.File;
import java.util.Random;
import java.util.HashMap;
import javafx.scene.Group;

import java.util.Random;
import java.util.List;

public abstract class Grid {

    public Cell[][] myCurrentState;
    public Cell[][] myNextState;
    public Simulation mySimulation;
    public int myDisplaySize;
    public XMLParser myXML;
    public Cell myCell;

    public Grid(String filePath, int displaySize) {
        myXML = new XMLParser(filePath);
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
        myDisplaySize = displaySize;

    }

    public abstract void initialize();

    private void getCellBasedOnSimulation() {

    }


    public Simulation getSimulation(String sim, HashMap<String, Double> map) {
        if (sim.equals("GameOfLife")) {
            return new GameOfLife(myCurrentState, myNextState);
        }
        if (sim.equals("Percolation")) {
            return new Percolation(myCurrentState, myNextState);
        }
        if (sim.equals("SpreadingOfFire")) {
            return new SpreadingOfFire(map, myCurrentState, myNextState);
        }
        if (sim.equals("Segregation")) {
            return new Segregation(map);
        }
        if (sim.equals("PredatorPrey")) {
            //return new PredatorPrey(map);
        }
        return null;
    }

    public Cell getSpecificCell(Polygon shape) {
        if (myXML.getSimulationType().equals("GameOfLife")) {
            //default constructor;
            return new GameOfLifeCell(shape);
        }
        if (myXML.getSimulationType().equals("SpreadingOfFire")){
            System.out.println("dddddddddddddddd");
            return new SpreadingOfFireCell(shape);
        }
        System.out.println("adfhalkdufhakj");
        return new GameOfLifeCell(shape);
    }


    public void moveNexttoCurrent() {
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                myCurrentState[i][j].setState(myNextState[i][j].getState());
                myNextState[i][j].setState(-1);
            }
        }
    }

    public boolean checkGameEnding() {
        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
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
        for (int i = 0; i < proportionStates.size(); i++) {
            percentage = (currentTotal + (int) (proportionStates.get(i) * 100));
            percentageStates.add(percentage);
            currentTotal = percentage;
        }

        for (int i = 0; i < myCurrentState.length; i++) {
            for (int j = 0; j < myCurrentState[0].length; j++) {
                if (myCurrentState[i][j].getState() != -2) {
                    randInt = rand.nextInt(100) + 1;
                    for (int k = 0; k < percentageStates.size(); k++) {
                        if (randInt <= percentageStates.get(k)) {
                            current = myCurrentState[i][j];
                            current.setState(stateLookupTable.get(states.get(k)));
                            current.setColor(stateToColorMap.get(current.getState()));
                            break;
                        }

                    }

                }
            }
        }
    }
}