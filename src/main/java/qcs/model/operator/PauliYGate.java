/*************************************
 Pauli-Y Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class PauliYGate extends Operator {

    public PauliYGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}