/*************************************
Pauli-X (NOT) Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class PauliXGate extends Operator {

    public PauliXGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}
