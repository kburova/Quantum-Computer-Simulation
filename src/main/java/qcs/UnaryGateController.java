/**************************************************
 UnaryGateController

 This file controls the output from dialog window
 that adds Unary gates

 Created by: Ksenia Burova
 Parker Diamond
 Nick Kelley
 Chris Martin

 Date: 03/22/2017
 ****************************************************/

package qcs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;

public class UnaryGateController {

    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    int rX;
    int rY;

    @FXML
    private TextField registerX;

    @FXML
    private TextField registerY;

    @FXML
    private void initialize(){

    }
    //bind stage with controller
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    //set registers here???

    public boolean isAdd(){
        return addClicked;
    }

    public void setCircuit(Circuit circuit){
        this.circuit = circuit;
    }

    @FXML
    private void handleAdd(){

        if ( isInputValid() ){
            addClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        return true;
    }
}
