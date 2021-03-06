/****************************************************
 Binary Operator

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model.operator;
import qcs.model.Register;

public class BinaryOperator extends Operator {
    int target;
    int control;

    public BinaryOperator(Register r, int target, int control, String name){
        super(r,name);
        type = "Binary";
        this.target = target;
        this.control = control;
    }

    @Override
    public void doOperation() {
        if (name.equals("Swap")){
            register.Swap(target,control);
        }else if (name.equals("CNOT")){
            register.CNOT(control,target);
        }
    }

    @Override
    public void undoOperation() {
        doOperation();
    }

    public int getTarget(){
        return target;
    }
    public int getControl(){
        return control;
    }
}
