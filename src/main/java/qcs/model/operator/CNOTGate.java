/*************************************
 Pauli-Z Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class CNOTGate extends Operator {

    Qubit control;

    public CNOTGate(Register r, Qubit q, String n, Qubit c){
        super(r,q,n);
        control = c;
    }

    public void doOperation(){

    }
}
