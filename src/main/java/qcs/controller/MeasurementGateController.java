package qcs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.Measurement;

/**
 * Created by kseniaburova on 4/10/17.
 */
public class MeasurementGateController {

    private Stage dialogStage;
    private  int addClicked = -1;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int qubitNum, operationStep;
    boolean All;

    @FXML
    private TextField qubitVal;

    @FXML
    private RadioButton x;

    @FXML
    private RadioButton y;

    @FXML
    private RadioButton qubit;

    @FXML
    private RadioButton all;

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
            if (All){
                circuit.addOperator(new Measurement(targetRegister, id), operationStep);
            }else{
                circuit.addOperator(new Measurement(targetRegister, id, qubitNum), operationStep);
            }
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

        if (all.isSelected()){
            All = true;
        }else{
            All = false;
            try{
                qubitNum = Integer.parseInt(qubitVal.getText());
                operationStep = Integer.parseInt(step.getText());
            }
            catch(Exception e){
                errorMessage = "Enter an Integer values";
            }
            if (qubitNum < 0 || qubitNum > targetRegister.getNumberOfQubits() ){
                errorMessage = "Enter valid qubit number";
            }
            if (operationStep < 0 || operationStep > circuit.getNumberOfOperators()){
                errorMessage = "Enter valid index for gate step, should be between 0 and "+ circuit.getNumberOfOperators();
            }
        }
        if (errorMessage.length() == 0){
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }

    public void ifOneSelected(ActionEvent event) {
        if (qubit.isSelected()) qubitVal.setDisable(false);
    }

    public void ifAllSelected(ActionEvent event) {
        if (all.isSelected()) qubitVal.setDisable(true);
    }
}
