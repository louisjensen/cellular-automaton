package view;

import java.util.HashMap;

public class RectangleGrid extends Grid {

    public RectangleGrid(String filePath, int numRows, int numCols, int displaySize){
        super(filePath);
        myCurrentState = new Cell[numRows][numCols];// for testing
        myNextState = new Cell[numRows][numCols];
        myDisplaySize = displaySize;
        mySimulation = getSimulation(myXML.getSimulationType(), myXML.getRandomInfo());
    }

    public void initialize(){

    }


    public void initializeRectangleGrid(){
        HashMap<String, Integer> stateLookupTable = mySimulation.getMyStateLookupTable();
        setGridProportion();
        for (int i = 0; i < myGridWidth; i++){
            for (int j = 0; j < myGridHeight; j++){
                randInt = rand.nextInt(100) + 1;
                for (int k = 0; k < percentageStates.size(); k++){
                    if(randInt <= percentageStates.get(k)) {
                        myCurrentState[i][j] = new Cell(i, j, myCellSizeX, myCellSizeY, stateLookupTable.get(states.get(k)));
                        myNextState[i][j] = new Cell(i, j, myCellSizeX, myCellSizeY,-1);
                        break;
                    }
                }
            }
        }

    }
}
