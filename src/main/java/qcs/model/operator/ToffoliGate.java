/****************************************************
 Toffoli Gate

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model.operator;
import qcs.model.Register;

public class ToffoliGate extends Operator{

    int target;
    int control1;
    int control2;

    public ToffoliGate(Register r, int q, int c1, int c2, String n){
        super(r,n);
        target = q;
        control1 = c1;
        control2 = c2;
        type = "Ternary";
    }

    public void doOperation(){
        register.CCNOT(control1,control2,target);
    }

    @Override
    public void undoOperation() {
        doOperation();
    }

    public int getTarget(){
        return target;
    }
    public int getControl1(){
        return control1;
    }
    public int getControl2(){
        return control2;
    }
}
