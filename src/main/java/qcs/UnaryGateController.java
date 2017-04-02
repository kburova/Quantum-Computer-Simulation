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
import qcs.model.operator.IdentityGate;

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

    //Nick added these when getting canvas fully implimented feel free to change
    //you understand this class best
    @FXML
    private TextField qbit;
    @FXML
    private TextField insertionStep;

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

            //Move this where you would like this is just minimalistic enough to test function drawing
            Integer qbit_index = Integer.parseInt(qbit.getText());
            Integer operator_index = Integer.parseInt(insertionStep.getText());
            //when you finish add dialog new ... will need to be the operator
            circuit.insertOperator(new IdentityGate(circuit.getX(),qbit_index,""), operator_index);

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
