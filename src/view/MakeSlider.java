package view;

import javafx.scene.control.Slider;

/**
 * @author:  Louis Lee
 */

public class MakeSlider {
    private final int ScreenSIZE = 580;

    /**
     * makes a slider
     * @return
     */
    public Slider makeSlider(){
        Slider mySlider = new Slider(0,20,1);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(5);
        mySlider.setMinorTickCount(1);
        mySlider.prefWidth(ScreenSIZE/2);
        mySlider.setLayoutX(ScreenSIZE / 26);
        mySlider.setLayoutY(ScreenSIZE*7/10);
        return mySlider;
    }
}
