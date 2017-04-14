package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class BinaryOperator extends Operator {
    int target;
    int control;

    public BinaryOperator(Register r, int target, int control, String name){
        super(r,name);
        this.target = target;
        this.control = control;
    }

    @Override
    public void doOperation() {
        if (name.equals("Swap")){
            register.Swap(target,control);
        }else if (name.equals("Rotate")){
            //register.Rotate();
        }else if (name.equals("CCNOT")){
            register.CNOT(control,target);
        }

    }

    public int getTarget(){
        return target;
    }
    public int getControl(){
        return control;
    }
}
