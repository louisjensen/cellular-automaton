package view;

import Grid.Grid;
import javafx.scene.chart.PieChart;

import java.util.HashMap;

public class MakeChart {
    private final int ScreenSIZE = 580;
    private final int ScreenHEIGHT = ScreenSIZE*5/4;
    private final int fontsize1 = ScreenSIZE/20;
    private final int PieChartSize = ScreenSIZE/5;

    public PieChart setupChart(Grid myGrid, int Gridnumber, int Position_X) {
        PieChart myChart = new PieChart();
        HashMap<String, Integer> SimulationStateMap;
        SimulationStateMap = myGrid.getSimulationMap();
        HashMap<Integer, String> myNewHashMap = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry : SimulationStateMap.entrySet()) {
            myNewHashMap.put(entry.getValue(), entry.getKey());
        }

        int count;
        for (int state : myNewHashMap.keySet()) {
            count = 0;
            for (int row = 0; row < myGrid.getMyCurrentState().length; row++) {
                for (int col = 0; col < myGrid.getMyCurrentState()[0].length; col++) {
                    if (myGrid.getMyCurrentState()[row][col].getState() == state) {
                        count++;
                    }
                }
            }
            myChart.getData().add(new PieChart.Data(myNewHashMap.get(state), count));
        }
        myChart.setVisible(true);
        myChart.setLayoutX(Position_X);
        myChart.setLayoutY(ScreenHEIGHT * 31 / 40);
        myChart.setPrefSize(PieChartSize, PieChartSize);
        myChart.setMinSize(PieChartSize, PieChartSize);
        myChart.setTitle("Grid " + Gridnumber);
        myChart.setStyle("-fx-font-size: " + fontsize1 / 2 + "px;");
        return myChart;
    }

}
