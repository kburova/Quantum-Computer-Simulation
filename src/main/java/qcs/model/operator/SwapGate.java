/*************************************
Swap Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Register;

public class SwapGate extends Operator {

    int control;

    public SwapGate(Register r, int q, int c, String n){
        super(r,q,n);
        control = c;
    }

    public void doOperation(){

    }

    public int getControl(){
        return control;
    }
}