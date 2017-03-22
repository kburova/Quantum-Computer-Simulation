/**************************************************
 RegistersController.java

 This file controls the output from dialog window
 that asks for number of qubits for each register

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 03/18/2017
 ****************************************************/
package qcs;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import qcs.model.Circuit;

public class RegistersController {

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
    }

    //set registers here???

    public boolean isAdd(){ return addClicked; }

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

            //alert if information was entered wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public Circuit getCircuit() {
        return circuit;
    }

    @FXML
    private void handleAdd(){

        if ( isInputValid() ){
            // set registers in circuit
            //System.out.println("Register x: " + rX);
            circuit = new Circuit();
            circuit.initilizeRegisters(rX,rY);

            addClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
