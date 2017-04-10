/*************************************
CNOT Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Register;

public class CNOTGate extends Operator {

    int control;

    public CNOTGate(Register r, int q, int c, String n){
        super(r,q,n);
        control = c;
    }

    public void doOperation(){

    }

    public int getControl(){
        return control;
    }
}
