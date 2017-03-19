/**************************************************
 Controller.java

 This file is a control part of the MVC model.
 It has all the methods to manipulate model

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 02/12/2017
 ****************************************************/
package qcs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainAppController implements Initializable{

    //reference to main application
    private MainApp mainApp;

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private void initializeRegisters(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: init canvas here?? Probably. Bind its view to data
        // TODO:set up listener if we want to select qubits and delete - changed() method
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @FXML
    private void handleInitRegisters() {
        boolean addClicked = mainApp.showAddRegistersDialog();

    }
}
