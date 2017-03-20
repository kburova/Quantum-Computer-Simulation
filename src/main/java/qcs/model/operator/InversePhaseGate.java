/*************************************
 Inverse Phase Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class InversePhaseGate extends Operator {

    public InversePhaseGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}

