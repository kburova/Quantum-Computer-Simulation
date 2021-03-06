/****************************************************
 Circuit class

 Consists of maximum of 2 registers so far, X and/or Y

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model;

import javafx.scene.control.Alert;
import qcs.model.operator.Operator;
import qcs.model.Register;
import java.util.LinkedList;
import java.util.List;

public class Circuit {

    private Register x;
    private Register y;
    private List <Operator> operators;
    int currentStep = 0;

    // don't need constructor .... we have to re-init circuit using function if
    // button is clicked over and over again
    public Circuit(){
    }

    // init each register with number of qubits n each, and restart
    // operations (circuit)
    public void initializeRegisters(int qubitsX, int qubitsY){
        x = new Register("X", qubitsX);
        y = new Register("Y", qubitsY);
        currentStep = 0;
        operators = new LinkedList<>();
    }

    public void reInitializeRegisterQubits(){
        currentStep = 0;
        x.reinitializeState();
        y.reinitializeState();
    }

    final public Register getX(){ return x; }

    final public Register getY(){ return y; }


    //add gate/measurement to a list of operators
    public void addOperator( Operator operator, int step ){
        if (step == operators.size()) {
            operators.add(operator);
        }
        else {
            operators.add(step, operator);
        }
    }

    final public int getNumberOfOperators(){
        return operators.size();
    }

    public Operator getOperator(int index) {
        return operators.get(index);
    }

    public Operator getLastOperator(){
        return operators.get(operators.size()-1);
    }

    public int getCurrentStep(){
        return currentStep;
    }

    public void setCurrentStep(int step){
        currentStep = step;
    }
    public void incrementStep(){
        currentStep++;
    }
    public void decrementStep(){
        currentStep--;
    }

    /**  alert if information was entered wrong **/
    public void showError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.setTitle("Error Dialog");
        alert.showAndWait();
    }

    public void deleteOperator(int index) {
        operators.remove(index);
    }
}
