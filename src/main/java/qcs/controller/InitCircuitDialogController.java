/**************************************************
 InitCircuitDialog Controller class

 Controls the output From dialog window
 that asks for number of qubits for each register

 Created by:

 Ksenia Burova

 Date: 03/18/2017
 ****************************************************/
package qcs.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import qcs.model.Circuit;

public class InitCircuitDialogController {

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
            circuit.initializeRegisters(rX,rY);
            addClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){

        String x, y;
        String errorMessage = "";
        x = registerX.getText();
        y = registerY.getText();

        if ( x == null || x.length() == 0 ){
            errorMessage = "Enter number of qubits for X";
        }else{

            try {
                rX = Integer.parseInt(x);
            } catch (NumberFormatException e) {
                errorMessage = "Enter an Integer value for X";
            }

            if (y == null || y.length() == 0) rY = 0;
            else{
                try {
                    rY = Integer.parseInt(y);
                } catch (NumberFormatException e) {
                    errorMessage = "Enter an Integer value for Y";
                }
            }
            if ( rX < 0 || rY < 0){
                errorMessage = "Enter positive values";
            }
        }
        if ( errorMessage.length() == 0 ){
            return true;
        }else{
            circuit.showError(errorMessage);

            return false;
        }
    }
}
