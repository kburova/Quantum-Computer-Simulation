package qcs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.GroverOperator;
import qcs.model.operator.VarQbitOperator;

/**
 * Created by apple on 4/10/17.
 */
public class GroverGateController {
    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int svalue;

    @FXML
    private TextField searchValue;

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
            circuit.addOperator(new GroverOperator(targetRegister,svalue, id));
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
        String value = searchValue.getText();
        System.out.println(value);

        try{
            svalue = Integer.parseInt(value);
        }
        catch(Exception e){
            errorMessage = "Enter an Integer value ";
        }

        if (svalue < 0 || svalue > (Math.pow(2,targetRegister.getNumberOfQubits())-1) ){
            errorMessage = "Enter valid state number, 0 trough (2^n - 1)";
        }

        if (errorMessage.length() == 0){
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }
}
