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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.*;

public class UnaryGateController {

    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int qubit;

    @FXML
    private TextField target;

    @FXML
    private RadioButton x;

    @FXML
    private RadioButton y;

    //bind stage with controller
    public void setDialogStage(Stage dialogStage, String id){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
        this.id = id;
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
            System.out.println(id);
            addClicked = true;
            circuit.addOperator(new UnaryOperator(targetRegister,qubit, id));
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if ( x.isSelected() )
            targetRegister = circuit.getX();
        else if (y.isSelected()){
            targetRegister = circuit.getY();
        }

        String value = target.getText();
        System.out.println(value);

        try{
            qubit = Integer.parseInt(value);
        }
        catch(Exception e){
            errorMessage = "Enter an Integer value ";
        }

        if (qubit < 0 || qubit >= targetRegister.getNumberOfQubits()){
            errorMessage = "Enter valid qubit index";
        }

        if (errorMessage.length() == 0){
            return true;
        }else{
            //alert if information was entered wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.setTitle("Error Dialog");
            alert.showAndWait();
            return false;
        }
    }
}
