/****************************************************
 Go To Controller class

 Communicates with Dialog window for 'Go To Step' and
 reflects results back into main app

 Created by:

 Ksenia Burova

 Date: 04/26/2017
 ***************************************************/
package qcs.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;

public class GoToController {
    private Stage dialogStage;
    private  int deleteClicked = -1;
    private Circuit circuit;
    int operatorToGoTo;

    @FXML
    private TextField operator;

    //bind stage with controller
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public int isOk(){
        return deleteClicked;
    }

    public void setCircuit(Circuit circuit){
        this.circuit = circuit;
        operator.setText( String.valueOf(circuit.getNumberOfOperators()-1) );
    }

    @FXML
    private void handleOK(){
        if ( isInputValid() ){
            deleteClicked = operatorToGoTo;
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
            operatorToGoTo = Integer.parseInt(operator.getText());
        }catch (Exception e){
            errorMessage = "Enter Integer value";
        }
        if ( operatorToGoTo < 0 || operatorToGoTo >= circuit.getNumberOfOperators() ){
            errorMessage = "Invalid operator number";
        }
        if ( operatorToGoTo == circuit.getCurrentStep() ){
            errorMessage = "Can't go to current step";
        }
        if (errorMessage.length() == 0) {
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }

}
