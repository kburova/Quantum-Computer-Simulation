/*************************************
Phase Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class PhaseGate extends Operator {

    public PhaseGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}
