package qcs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.Measurement;
import qcs.model.operator.VarQbitOperator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by apple on 4/10/17.
 */
public class VarQbitController {
    private Stage dialogStage;
    private  int addClicked = -1;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int qubitNum, operationStep, From, To;

    @FXML
    private TextField qubitVal;

    @FXML
    private TextField from;
    @FXML
    private TextField to;

    @FXML
    private RadioButton x;

    @FXML
    private RadioButton y;

    @FXML
    private TextField step;

    @FXML
    private RadioButton qubit;

    @FXML
    private RadioButton all;

    @FXML
    private RadioButton range;

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
            circuit.addOperator( new VarQbitOperator(targetRegister, id, From, To), operationStep );
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
            From = 0;
            To = targetRegister.getNumberOfQubits()-1;
        }else if(qubit.isSelected()){
            try{
                qubitNum = Integer.parseInt(qubitVal.getText());
            }
            catch(Exception e){
                errorMessage = "Enter Integer values";
            }
            if (qubitNum < 0 || qubitNum > targetRegister.getNumberOfQubits() ){
                errorMessage = "Enter valid qubit number";
            }else{
                From = To = qubitNum;
            }
        }else if (range.isSelected()) {
            try{
                From = Integer.parseInt(from.getText());
                To = Integer.parseInt(to.getText());
            }
            catch(Exception e){
                errorMessage = "Enter Integer values";
            }
            if ( From < 0 || To >= targetRegister.getNumberOfQubits() || From > To ) {
                errorMessage = "Enter valid qubit range";
            }
        }
        try{
            operationStep = Integer.parseInt(step.getText());
        }
        catch(Exception e){
            errorMessage = "Enter Integer values";
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

    public void ifOneSelected(ActionEvent event) {
        if (qubit.isSelected()) {
            from.setDisable(true);
            to.setDisable(true);
            qubitVal.setDisable(false);
        }
    }

    public void ifAllSelected(ActionEvent event) {
        if (all.isSelected()) {
            from.setDisable(true);
            to.setDisable(true);
            qubitVal.setDisable(true);
        }
    }

    public void ifRangeSelected(ActionEvent event) {
        if (range.isSelected()){
            from.setDisable(false);
            to.setDisable(false);
            qubitVal.setDisable(true);
        }
    }
}
