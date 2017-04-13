package qcs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.ToffoliGate;

/**
 * Created by kseniaburova on 4/10/17.
 */
public class TernaryGateController {
    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int targetQubit, controlQubit1, controlQubit2;

    @FXML
    private TextField target;

    @FXML
    private TextField control1;
    @FXML
    private TextField control2;

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
            if (id.equals("Toffoli")){

                circuit.addOperator(new ToffoliGate(targetRegister, targetQubit, controlQubit1, controlQubit2, id));
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

        String targetVal = target.getText();
        String controlVal1 = control1.getText();
        String controlVal2 = control2.getText();


        try{
            targetQubit = Integer.parseInt(targetVal);
            controlQubit1 = Integer.parseInt(controlVal1);
            controlQubit2 = Integer.parseInt(controlVal2);
        }
        catch(Exception e){
            errorMessage = "Enter an Integer value ";
        }

        if (targetQubit < 0 || controlQubit1 < 0 || controlQubit2 < 0 ||
                targetQubit >= targetRegister.getNumberOfQubits() ||
                controlQubit1 >= targetRegister.getNumberOfQubits() ||
                controlQubit2 >= targetRegister.getNumberOfQubits()){
            errorMessage = "Enter valid qubit index";
        }
        if (errorMessage.length() == 0){
            return true;
        }else{
            circuit.showError(errorMessage);
            return false;
        }
    }
}
