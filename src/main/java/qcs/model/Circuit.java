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
    private int numberOfOperators; // equals to number of steps
    private List <Operator> operators;

    // don't need constructor .... we have to re-init circuit using function if
    // button is clicked over and over again
    public Circuit(){
    }

    // init each register with number of qubits n each, and restart
    // operations (circuit)
    public void initializeRegisters(Integer x, Integer y){
        this.x = new Register("X", x);
        this.y = new Register("Y", y);
        numberOfOperators = 0;
        operators = new LinkedList<>();
    }

    final public Register getX(){ return x; }

    final public Register getY(){ return y; }

    //Implement 1 step forward through "music" cord
    public void stepForward(){

    }

    //Implement 1 step back through "music" cord
    public void stepBack(){

    }

    //Return to the beginning of circuit, restart computation
    public void restart(){

    }

    //run through all steps
    public void runAll(){

    }

    //add gate/measurement
    public void addOperator( Operator operator ){

    }

    //remove gate/measurement
    public void removeOperator(Operator o) {

        //TODO: recalculate after deletion if nessacary, or restart circuit
        if (operators.remove(o)) {
            numberOfOperators--;
        } else{
            //operator wasn't found - error
        }
    }

    //TODO: think if possible, to be able to delete from the middle
    //select gate/measurement
    public void selectOperator(){

    }

    final public int getNumberOfOperators(){
        return numberOfOperators;
    }

}
