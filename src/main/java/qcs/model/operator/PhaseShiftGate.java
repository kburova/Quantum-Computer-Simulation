/*************************************
 Phase Shift Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class PhaseShiftGate extends Operator {

    public PhaseShiftGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}

