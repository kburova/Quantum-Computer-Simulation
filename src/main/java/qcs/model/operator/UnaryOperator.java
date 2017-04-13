package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class UnaryOperator extends Operator{
    int target;
    public UnaryOperator(Register r, int target, String name){
        super(r,name);
        this.target = target;
    }

    @Override
    public void doOperation() {
        if (name.equals("X")){
        }else if (name.equals("Y")){
        }else  if (name.equals("Z")){
        }else if (name.equals("SqRoot")){
        }else if (name.equals("Hadamard")){
        }else if (name.equals("Identity")){
        }else if (name.equals("Inverse")){
        }else if (name.equals("Phase")){
        }else if (name.equals("Shift")) {
        }
    }
    public int getTarget(){
        return target;
    }
}
