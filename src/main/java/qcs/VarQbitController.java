package qcs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            circuit.addOperator(new VarQbitOperator(targetRegister, id));
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        if ( x.isSelected() )
            targetRegister = circuit.getX();
        else if (y.isSelected()){
            targetRegister = circuit.getY();
        }
        return true;
    }
}
