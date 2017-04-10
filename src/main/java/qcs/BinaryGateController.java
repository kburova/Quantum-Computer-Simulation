package qcs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import qcs.model.Circuit;
import qcs.model.Register;
import qcs.model.operator.*;

/**
 * Created by kseniaburova on 4/9/17.
 */
public class BinaryGateController {

    private Stage dialogStage;
    private  boolean addClicked = false;
    private Circuit circuit;
    String id;
    Register targetRegister;
    int targetQubit, controlQubit;

    @FXML
    private TextField target;

    @FXML
    private TextField control;

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
            if (id.equals("CNOT")){
                circuit.addOperator(new CNOTGate(targetRegister, targetQubit, controlQubit, id));
            }else if (id.equals("Rotate")){
                circuit.addOperator(new RotateGate(targetRegister, targetQubit, controlQubit, id));
            }else  if (id.equals("Swap")) {
                circuit.addOperator(new SwapGate(targetRegister, targetQubit, controlQubit, id));
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
        System.out.println(targetVal);
        String controlVal = control.getText();
        System.out.println(controlVal);

        try{
            targetQubit = Integer.parseInt(targetVal);
            controlQubit = Integer.parseInt(controlVal);
        }
        catch(Exception e){
            errorMessage = "Enter an Integer value ";
        }

        if (targetQubit < 0 || controlQubit < 0 || targetQubit >= targetRegister.getNumberOfQubits() || controlQubit >= targetRegister.getNumberOfQubits()){
            errorMessage = "Enter valid qubit index";
        }
        if (errorMessage.length() == 0){
            return true;
        }else{
            //alert if information was entered wrong
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.setTitle("Error Dialog");
            alert.showAndWait();
            return false;
        }
    }
}
