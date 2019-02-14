package view;

import javafx.scene.control.Alert;
import java.util.ResourceBundle;

/**
 * @author:  Louis Lee, Louis Jensen
 * This class handles all the alert messages
 */

public class MakeAlert {

    /** Load Strings from resource bundle, not working on Mac and Linux
     private ResourceBundle myResources = ResourceBundle.getBundle("textForGui");
     private final String INPUT_ERROR_TITLE = myResources.getString("InputError");
     private final String INPUT_ERROR_HEADER = myResources.getString("NoInputFile");
     private final String INPUT_ERROR_CONTENT = myResources.getString("SelectFile");
     private final String INVALID_FILE_TITLE = myResources.getString("InvalidError");
     private final String INVALID_FILE_HEADER = myResources.getString("NotValid");
     private final String INVALID_FILE_CONTENT = myResources.getString("UploadValid");
     private final String INITIALIZATOIN_ERROR_TITLE = myResources.getString("InitError");
     private final String INITIALIZATOIN_ERROR_HEADER = myResources.getString("NoGrid");
     private final String INITIALIZATOIN_ERROR_CONTENT = myResources.getString("PleaseInit");
     private final String SIMULATION_OVER_TITLE = myResources.getString("SimOver");
     private final String SIMULATION_OVER_HEADER = myResources.getString("FinState");
     private final String SIMULATION_OVER_CONTENT = myResources.getString("ItsOver");
     */

    private final String INPUT_ERROR_TITLE = "Input Error";
    private final String INPUT_ERROR_HEADER = "No Input File";
    private final String INPUT_ERROR_CONTENT = "Please Select Input XML file";
    private final String INVALID_FILE_TITLE = "Invalid File";
    private final String INVALID_FILE_HEADER = "This is not a valid file";
    private final String INVALID_FILE_CONTENT = "Please upload a valid XML configuration file";
    private final String INITIALIZATOIN_ERROR_TITLE = "Initialization Error";
    private final String INITIALIZATOIN_ERROR_HEADER = "No Grid on Screen";
    private final String INITIALIZATOIN_ERROR_CONTENT = "Please Initialize the Grid first";
    private final String SIMULATION_OVER_TITLE = "Simulation Over";
    private final String SIMULATION_OVER_HEADER = "Final State";
    private final String SIMULATION_OVER_CONTENT = "This is the final state. Press Reset Button";

    private void makeAnAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    /**
     * make alert for input error
     */
    public void makeAlert(){
        makeAnAlert(INPUT_ERROR_TITLE, INPUT_ERROR_HEADER, INPUT_ERROR_CONTENT);
    }
    /**
     * make alert for invalid file
     */
    public void makeInvalidFileError(){
        makeAnAlert(INVALID_FILE_TITLE, INVALID_FILE_HEADER, INVALID_FILE_CONTENT);
    }
    /**
     * make alert for initialization
     */
    public void makeInitialize(){
        makeAnAlert(INITIALIZATOIN_ERROR_TITLE, INITIALIZATOIN_ERROR_HEADER, INITIALIZATOIN_ERROR_CONTENT);
    }
    /**
     * make alert for game ending
     */
    public void makeGameEnding(){
        makeAnAlert(SIMULATION_OVER_TITLE, SIMULATION_OVER_HEADER, SIMULATION_OVER_CONTENT);
    }
}

