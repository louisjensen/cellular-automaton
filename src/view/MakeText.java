package view;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ResourceBundle;

/**
 * @author:  Louis Lee, Louis Jensen
 */

public class MakeText {

    /** Load Strings from resource bundle, not working on Mac and Linux
     private ResourceBundle myResources = ResourceBundle.getBundle("textForGui");
     private final String DEFAULT_FONT = myResources.getString("DEFAULT_FONT");
     */

    private final String DEFAULT_FONT = "Times New Roman";

    /**
     * Makes text object with given string
     * @param message
     * @param x
     * @param y
     * @param FontSize
     * @return
     */
    public Text MakeText(String message, int x, int y, int FontSize) {
        Text text = new Text();
        text.setX(x);
        text.setY(y);
        text.setFont(Font.font(DEFAULT_FONT, FontSize));
        text.setText(message);
        text.setFill(Color.BLACK);
        return text;
    }
}
