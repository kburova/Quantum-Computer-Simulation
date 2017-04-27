/****************************************************
Grover Gate Controller class

 Communicates with Dialog window for 'Add Grover Gate'
 and reflects results back into main app

 Created by:

 Ksenia Burova

 Date: 04/10/2017
 ***************************************************/
package qcs.controller;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.GroverOperator;

public class GroverGateController {
    private Stage dialogStage;
    private  int addClicked = -1;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int svalue, operationStep;

    @FXML
    private TextField searchValue;

    @FXML
    private RadioButton x;

    @FXML
    private RadioButton y;

    @FXML
    private TextField step;

    //bind stage with controller
    public void setDialogStage(Stage dialogStage, String id){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
        this.id = id;
    }

    public int isAdd(){
        return addClicked;
    }

    public void setCircuit(Circuit circuit){
        this.circuit = circuit;
        step.setText( Integer.toString(circuit.getNumberOfOperators()) );
        if (circuit.getY().getNumberOfQubits() == 0){
            y.setDisable(true);
        }
    }

    @FXML
    private void handleAdd(){
        if ( isInputValid() ){
            System.out.println(id);
            addClicked = operationStep;
            circuit.addOperator(new GroverOperator(targetRegister,svalue, id), operationStep);
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
        try{
            svalue = Integer.parseInt(searchValue.getText());
            operationStep = Integer.parseInt(step.getText());
        }
        catch(Exception e){
            errorMessage = "Enter Integer values ";
        }

        if (svalue < 0 || svalue > (Math.pow(2,targetRegister.getNumberOfQubits())-1) ){
            errorMessage = "Enter valid state number, 0 trough (2^n - 1)";
        }

        if (operationStep < 0 || operationStep > circuit.getNumberOfOperators()){
            errorMessage = "Enter valid index for gate step, should be between 0 and "+ circuit.getNumberOfOperators();
        }

        if (errorMessage.length() == 0){
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }
}
