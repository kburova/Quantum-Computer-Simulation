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

import java.util.ArrayList;
import java.util.LinkedList;

public class Circuit {
    private Register x;
    private Register y;
    private LinkedList<Operator> operators;

    //right now just ties into visible line of execution
    //will be integral for optimal quantum function running
    //as to not redo unnecessary calculations
    private Integer executing_operator_index;

    // don't need constructor .... we have to re-init circuit using function if
    // button is clicked over and over again
    public Circuit(){
    }

    // init each register with number of qubits n each, and restart
    // operations (circuit)
    public void initializeRegisters(int qubitsX, int qubitsY){
        x = new Register("X", qubitsX);
        y = new Register("Y", qubitsY);
        operators = new LinkedList<>();
        executing_operator_index = 0;
    }

    final public Register getX(){ return x; }

    final public Register getY(){ return y; }

    //Implement 1 step forward through "music" cord
    public Integer stepForward()
    {
      //within step logic is where we will be running quantum ops
      if(executing_operator_index != operators.size() - 1) executing_operator_index++;
      return executing_operator_index;
    }

    //Implement 1 step back through "music" cord
    public Integer stepBack()
    {
        if(executing_operator_index != 0) executing_operator_index--;
        return executing_operator_index;
    }

    public Integer getCurrentFunctionIndex() {
        return executing_operator_index;
    }

    //Return to the beginning of circuit, restart computation
    public void restart(){
      executing_operator_index = 0;
    }

    //run through all steps
    public Integer runAll()
    {
      //perhaps as an added bonus we could animate the stepping forward process
      while(executing_operator_index != this.stepForward()){}
      return executing_operator_index;
    }

    //add gate/measurement
    //TODO implement cache dump, save on recalculations if memory efficient
    //it will be (we already hold a cache for displaying step by step output)
    public void insertOperator(Operator operator, Integer index)
    {
      //appending to tail of list or insertion operation
      if(index > operators.size() - 1)
      {
        operators.addLast(operator);
      } else
      {
        //determines if cache flush necessary and currently executing operation
        //needs to be rolled back
        if(index <= executing_operator_index)
        {
          operators.add(index, operator);
          executing_operator_index = index;
          //flush cache to executing_operator_index - 1
          //start calculating executing_operator_index
        }
        else
        {
          operators.add(index, operator);
        }
      }
    }

    //remove gate/measurement
    //similar to insertOperation
    //only difference is remove may push line of execution off list
    //if that is the case sets it to 1 before the removal
    public void removeOperator(Integer index) {
      //pop from tail of list or delete from middle operation
      if(index > operators.size() - 1)
      {
        operators.remove(operators.size() - 1);
      }
      else
      {
        //determines if cache flush necessary and currently executing operation
        //needs to be rolled back
        if(index <= executing_operator_index)
        {
          //super tricky because mutation
          //the size check returns a different value  because remove
          operators.remove(index);
          if(index > operators.size() - 1)
          {
            executing_operator_index = index - 1;
            //flush cache to executing_operator_index - 1
            //done
          }
          else
          {
            executing_operator_index = index;
            //flush cache to executing_operator_index - 1
            //start calculating executing_operator_index
          }
        }
        else
        {
          operators.remove(index);
        }
      }
    }

    //select gate/measurement
    public void selectOperator(){

    }

    final public int getNumberOfOperators(){
        return operators.size();
    }

    final public LinkedList<Operator> getOperators() { return operators; }
}
