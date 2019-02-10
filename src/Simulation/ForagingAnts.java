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

        ForagingAntsCell current;
        ForagingAntsCell next;
        ForagingAntsCell randomCell;
        ArrayList<Cell> neighbors;
        ArrayList<Ants> antsArrayList;

        for (int row = 0; row < myCurrentGrid.length; row++) {
            for (int col = 0; col < myCurrentGrid[0].length; col++) {
                current = myCurrentGrid[row][col];
                antsArrayList = current.getMyAntsList();
                for (int a = 0; a < antsArrayList.size(); a++) {
                    Ants myAnt = antsArrayList.get(a);
                    if(myAnt.getPoint() == Nest.pos){
                        myAnt.dropfood();
                        neighbors = getNeighbors(current);




                    }
                }
            }
        }
    }
}
