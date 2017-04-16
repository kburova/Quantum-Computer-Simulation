package qcs.controller;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.BinaryOperator;
import qcs.model.operator.RotateGate;

import java.util.Observable;

/**
 * Created by kseniaburova on 4/15/17.
 */
public class RotateGateController {
    private Stage dialogStage;
    private  int addClicked = -1;
    private Circuit circuit;
    String id, phaseValue;
    Register targetRegister;
    int targetQubit, controlQubit, operationStep;
    double Phase;
    @FXML
    private TextField target;

    @FXML
    private TextField control;

    @FXML
    private RadioButton x;

    @FXML
    private RadioButton y;

    @FXML
    private TextField step;

    @FXML
    private ChoiceBox phase;

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
            circuit.addOperator(new RotateGate(targetRegister, targetQubit, controlQubit, Phase, id), operationStep);
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
            targetQubit = Integer.parseInt(target.getText());
            controlQubit = Integer.parseInt(control.getText());
            operationStep = Integer.parseInt(step.getText());
        }
        catch(Exception e){
            errorMessage = "Enter Integer values";
        }

        if ( targetQubit == controlQubit ){
            errorMessage = "Control and target qubits must have different values";
        }

        if (targetQubit < 0 || controlQubit < 0 || targetQubit >= targetRegister.getNumberOfQubits() || controlQubit >= targetRegister.getNumberOfQubits()){
            errorMessage = "Enter valid qubit index";
        }

        phaseValue = String.valueOf(phase.getValue());
        System.out.println(phaseValue);

        if (phaseValue.equals("π")){
            Phase = Math.PI;
        }else if (phaseValue.equals("π/2")){
            Phase = Math.PI / 2;
        }else if (phaseValue.equals("π/3")){
            Phase = Math.PI / 3;
        }else if (phaseValue.equals("π/4")){
            Phase = Math.PI / 4;
        }else if (phaseValue.equals("π/5")){
            Phase = Math.PI / 5;
        }else if (phaseValue.equals("π/6")){
            Phase = Math.PI / 6;
        }else if (phaseValue.equals("π/7")){
            Phase = Math.PI / 7;
        }else if (phaseValue.equals("π/8")){
            Phase = Math.PI / 8;
        }

        if (operationStep < 0 || operationStep > circuit.getNumberOfOperators()){
            errorMessage = "Enter valid index for gate step, should be between 0 and " + circuit.getNumberOfOperators();
        }

        if (errorMessage.length() == 0){
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }
}
