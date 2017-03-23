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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainAppController implements Initializable{

    //reference to main application
    private MainApp mainApp;

    //reference used by save / load to remember where to save to
    private QIO qio = new QIO();

    @FXML
    private Label label;

    @FXML
    private Label label1;

    @FXML
    private void initializeRegisters(ActionEvent event) {

    }

    @FXML
    private void open() {
        new QIO().load(new Stage());
    }

    @FXML
    private void save_as() {
        //when there is a dialogStage for the visualization to be loaded
        //from (first q function) expects an arraylist
        qio.save_as(new Stage(), new ArrayList<>());
    }

    @FXML
    private void save() {
        qio.save(new ArrayList<>());
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
    private void handleInitCircuitDialog() {
        boolean addClicked = mainApp.showAddRegistersDialog();

        //if clicked draw registers...
    }

    @FXML
    private void handleInitQubitsDialog(){

        if (mainApp.getCircuit().getX() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Circuit was not initialized with registers!!!");
            alert.showAndWait();
        }else {
            boolean OkClicked = mainApp.showAddQubitValuesDialog();
        }
    }
}
