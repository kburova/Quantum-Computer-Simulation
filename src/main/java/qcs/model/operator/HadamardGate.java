/*************************************
Hadamard Gate implementation

**************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class HadamardGate extends Operator {

    public HadamardGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}
