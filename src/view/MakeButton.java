package view;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;

/**
 * @author:  Louis Lee, Louis Jensen
 */
public class MakeButton {

    /** Load Strings from resource bundle, not working on Mac and Linux
     private ResourceBundle myResources = ResourceBundle.getBundle("textForGui");
     private final String BOUNDARY_TEXT = myResources.getString("BOUNDARY_TEXT");
     private final String SHAPE_TEXT = myResources.getString("SHAPE_TEXT");;
     private final String NUMBEROFSIMULATION_TEXT = myResources.getString("NUMBEROFSIMULATION_TEXT");;
     */

    private final static int ScreenSIZE = 580;
    private final static int BUTTON_SIZE = ScreenSIZE / 25;
    private final static int BUTTON_POS_X = ScreenSIZE/26;
    private final String BOUNDARY_TEXT = "Choose Boundary Type";
    private final String SHAPE_TEXT = "Choose Shape";
    private final String NUMBEROFSIMULATION_TEXT ="# of Simulations";
    private int simulationNumber;
    private String shapetype = "";
    private String edgeType = "";

    /*
    returns number of simulations
     */
    public int getSimulationNumber(){
        return simulationNumber;
    }

    /*
    returns shape type as a string
     */
    public String getSHAPE_TEXT(){
        return shapetype;
    }

    /*
    returns edgetype as a string
     */
    public String getEdgeType(){
        return edgeType;
    }

    /*
    Creates a button based on image and text
     */
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

    /*
    creates imagaview for the menubutton
     */

    public void menubuttonimagaereader(String file, MenuButton menuButton, int x, int y){
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(file));
        ImageView iv = new ImageView(image);
        iv.setFitHeight(BUTTON_SIZE);
        iv.setFitWidth(BUTTON_SIZE);
        menuButton.setGraphic(iv);
        menuButton.setLayoutX(x);
        menuButton.setLayoutY(y);
    }

    /*
    dropdown menubutton for selecting edge type
     */
    public MenuButton selectEdgeTypes(String file, int x, int y){
        MenuButton menuButton = new MenuButton(BOUNDARY_TEXT);
        menubuttonimagaereader(file, menuButton, x, y);
        MenuItem regular = new MenuItem("regular");
        regular.setOnAction(event -> {
            edgeType = "regular";
        });
        MenuItem toroidal = new MenuItem("toroid");
        toroidal.setOnAction(event -> {
            edgeType = "toroid";
        });
        menuButton.getItems().addAll(regular, toroidal);
        return menuButton;
    }

    /*
        dropdown menubutton for selecting cell shape
    */

    public MenuButton selectCellShape(String file, int x, int y){
        MenuButton menuButton = new MenuButton(SHAPE_TEXT);
       menubuttonimagaereader(file, menuButton, x, y);
        MenuItem triangle = new MenuItem("triangle");
        triangle.setOnAction(event -> {
            shapetype = "triangle";
        });
        MenuItem rectangle = new MenuItem("rectangle");
        rectangle.setOnAction(event -> {
            shapetype = "rectangle";
        });
        MenuItem hexagon = new MenuItem("hexagon");
        hexagon.setOnAction(event -> {
            shapetype = "hexagon";
        });
        menuButton.getItems().addAll(triangle, rectangle, hexagon);
        return menuButton;
    }

    /*
    dropdown menubutton for selecting number of simulations
     */
    public MenuButton selectNumSimulations(String file, int x, int y){
        MenuButton menuButton = new MenuButton(NUMBEROFSIMULATION_TEXT);
        menubuttonimagaereader(file, menuButton, x, y);
        MenuItem onesimulation = new MenuItem("1");
        onesimulation.setOnAction(event -> {
            simulationNumber = 1;
        });
        MenuItem twosimulations = new MenuItem("2");
        twosimulations.setOnAction(event -> {
            simulationNumber = 2;
        });
        MenuItem foursimulations = new MenuItem("4");
        foursimulations.setOnAction(event -> {
            simulationNumber = 4;
        });

        menuButton.getItems().addAll(onesimulation, twosimulations, foursimulations);
        return menuButton;

    }

}
