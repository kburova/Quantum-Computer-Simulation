/*************************************
 Pauli-Z Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Register;

public class CNOTGate extends Operator {

    int control;

    public CNOTGate(Register r, int q, String n, int c){
        super(r,q,n);
        control = c;
    }

    public void doOperation(){

    }
}
