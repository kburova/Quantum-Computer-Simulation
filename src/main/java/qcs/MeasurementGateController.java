package qcs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.Measurement;

/**
 * Created by kseniaburova on 4/10/17.
 */
public class MeasurementGateController {

    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int qubitNum;
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

    //bind stage with controller
    public void setDialogStage(Stage dialogStage, String id){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
        this.id = id;
    }

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
            if (All){
                circuit.addOperator(new Measurement(targetRegister, id));
            }else{
                circuit.addOperator(new Measurement(targetRegister, id, qubitNum));
            }
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        String value;
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
            value = qubitVal.getText();
            try{
                qubitNum = Integer.parseInt(value);
            }
            catch(Exception e){
                errorMessage = "Enter an Integer value ";
            }
            if (qubitNum < 0 || qubitNum > targetRegister.getNumberOfQubits() ){
                errorMessage = "Enter valid qubit number";
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
