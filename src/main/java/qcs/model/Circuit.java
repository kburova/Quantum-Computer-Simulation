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

import qcs.MainAppController;
import qcs.model.operator.Operator;
import qcs.model.Register;
import java.util.LinkedList;
import java.util.List;

public class Circuit {

    private Register x;
    private Register y;
    private int numberOfOperators; // equals to number of steps
    private List <Operator> operators;
    private Integer canvas_line_index;

    // don't need constructor .... we have to re-init circuit using function if
    // button is clicked over and over again
    public Circuit(){
    }

    // init each register with number of qubits n each, and restart
    // operations (circuit)
    public void initializeRegisters(int qubitsX, int qubitsY){
        x = new Register("X", qubitsX);
        y = new Register("Y", qubitsY);
        numberOfOperators = 0;
        operators = new LinkedList<>();
        canvas_line_index = 0;
    }

    final public Register getX(){ return x; }

    final public Register getY(){ return y; }

    //Implement 1 step forward through "music" cord
    public void stepForward(){
      if(canvas_line_index >= operators.size() - 1){
          return;
      } else {
          canvas_line_index += 1;
      }
    }

    //Implement 1 step back through "music" cord
    public void stepBack(){
        if(canvas_line_index <= 0){
            return;
        } else {
            canvas_line_index -= 1;
        }
    }

    public Integer getCurrentFunctionIndex() {
        return canvas_line_index;
    }

    //Return to the beginning of circuit, restart computation
    public void restart(){
      canvas_line_index = 0;
    }

    //run through all steps
    public void runAll(){
        if(operators.size() == 0)
            canvas_line_index = 0;
        else
            canvas_line_index = operators.size() - 1;
    }

    //add gate/measurement
    public void insertOperator( Operator operator, Integer index){
      operators.add(index, operator);
    }

    //remove gate/measurement
    public void removeOperator(Integer index) {
        //TODO: recalculate after deletion if nessacary, or restart circuit
      try {
          operators.remove(index);
          numberOfOperators -= 1;
      } catch(IndexOutOfBoundsException e) {
          System.out.println("invalid attempt to delete out of index operator");
      }
    }

    //select gate/measurement
    public void selectOperator(){

    }

    final public int getNumberOfOperators(){
        return numberOfOperators;
    }

}
