/*************************************
 Square root of NOT Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;
import qcs.model.operator.Operator;

public class SqRootOfNOTGate extends Operator {

    public SqRootOfNOTGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}

