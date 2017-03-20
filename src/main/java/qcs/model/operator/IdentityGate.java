/*************************************
 Identity Gate implementation

 **************************************/
package qcs.model.operator;

import qcs.model.Qubit;
import qcs.model.Register;

public class IdentityGate extends Operator {

    public IdentityGate(Register r, Qubit q, String n){
        super(r,q,n);
    }

    public void doOperation(){

    }
}
