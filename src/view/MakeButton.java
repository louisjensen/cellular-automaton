package view;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MakeButton {
    private final static int ScreenSIZE = 580;
    private final static int BUTTON_SIZE = ScreenSIZE / 25;
    private final static int BUTTON_POS_X = ScreenSIZE/26;


    public Button makeButton(String text, String file, int y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(BUTTON_SIZE);
        imageview.setFitHeight(BUTTON_SIZE);
        Button button = new Button(text,imageview);
        button.setLayoutX(BUTTON_POS_X);
        button.setLayoutY(y);
        return button;
    }

    public void menubuttonimagaereader(String file, MenuButton menuButton, int x, int y){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(BUTTON_SIZE);
        iv.setFitWidth(BUTTON_SIZE);
        menuButton.setGraphic(iv);
        menuButton.setLayoutX(x);
        menuButton.setLayoutY(y);
    }



}
