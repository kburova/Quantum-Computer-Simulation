package qcs.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;

/**
 * Created by kseniaburova on 4/13/17.
 */
public class RemoveGateController {
    private Stage dialogStage;
    private  boolean deleteClicked = false;
    private Circuit circuit;
    int operatorToDelete;

    @FXML
    private TextField operator;

    //bind stage with controller
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public boolean isDelete(){
        return deleteClicked;
    }

    public void setCircuit(Circuit circuit){
        this.circuit = circuit;
        operator.setText( String.valueOf(circuit.getNumberOfOperators()-1) );
    }

    @FXML
    private void handleDelete(){
        if ( isInputValid() ){
            deleteClicked = true;
            circuit.deleteOperator(operatorToDelete);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){

        String errorMessage = "";
        try{
            operatorToDelete = Integer.parseInt(operator.getText());
        }catch (Exception e){
            errorMessage = "Enter Integer value";
        }
        if ( operatorToDelete < 0 || operatorToDelete >= circuit.getNumberOfOperators() ){
            errorMessage = "Invalid operator number";
        }
        if (errorMessage.length() == 0) {
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }

}
