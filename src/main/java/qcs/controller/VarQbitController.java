package qcs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.VarQbitOperator;

/**
 * Created by apple on 4/10/17.
 */
public class VarQbitController {
    private Stage dialogStage;
    private  int addClicked = -1;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int operationStep;

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

    //set registers here???

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
            circuit.addOperator(new VarQbitOperator(targetRegister, id), operationStep);
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
           operationStep = Integer.parseInt(step.getText());
        }
        catch(Exception e){
            errorMessage = "Enter an Integer values";
        }
        if (operationStep < 0 || operationStep > circuit.getNumberOfOperators()){
            errorMessage = "Enter valid index for gate step, should be between 0 and "+ circuit.getNumberOfOperators();
        }
        if (errorMessage.length() == 0){
            return true;
        }else {
            circuit.showError(errorMessage);
            return false;
        }
    }
}
