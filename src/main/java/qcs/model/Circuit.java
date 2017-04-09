/****************************************************
 Circuit class

 Consists of maximum of 2 registers so far, X and/or Y

 Created by: Ksenia Burova
            Parker Diamond
            Nick Kelley
            Chris Martin

 Date: 03/18/2017
 ***************************************************/
package qcs.model;

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

    final public Register getX(){ return x; }

    final public Register getY(){ return y; }


    //add gate/measurement
    public void addOperator( Operator operator ){
        operators.add(operator);
    }

    //remove gate/measurement
    public void removeOperator(Operator operator) {

        //TODO: recalculate after deletion if nessacary, or restart circuit
        if ( operators.remove(operator) ) {
        } else{
            //operator wasn't found - error
        }
    }

    //TODO: think if possible, to be able to delete from the middle
    //select gate/measurement
    public void selectOperator(){

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
}
