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
    private final String BOUNDARY_TEXT = "Choose Boundary Type";
    private final String SHAPE_TEXT = "Choose Shape";
    private final String NUMBEROFSIMULATION_TEXT ="# of Simulations";
    private int simulationNumber;
    private String shapetype = "";
    private String edgeType = "";


    public int getSimulationNumber(){
        return simulationNumber;
    }

    public String getSHAPE_TEXT(){
        return shapetype;
    }
    public String getEdgeType(){
        return edgeType;
    }

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
