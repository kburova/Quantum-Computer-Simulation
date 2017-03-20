/*************************************
 Pauli-Z Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class PauliZGate extends Operator {

    public PauliZGate(Register r, Qubit q, String n){
        super(r,q,n);

    }

    public void doOperation(){

    }
}
