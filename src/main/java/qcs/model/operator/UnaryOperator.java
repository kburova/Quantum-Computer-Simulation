package qcs.model.operator;

import qcs.MainApp;
import qcs.model.Register;

/**
 * Created by kseniaburova on 4/10/17.
 */
public class UnaryOperator extends Operator{
    int target;
    public UnaryOperator(Register r, int target, String name){
        super(r,name);
        this.target = target;
        type = "Unary";
    }

    @Override
    public void doOperation() {
        if (name.equals("X")){
            register.Not(target);
        }else if (name.equals("Y")){
            register.Y(target);
        }else  if (name.equals("Z")){
            register.Z(target);
        }else if (name.equals("SqRoot")){
            register.SquareRootNot(target);
        }else if (name.equals("Hadamard")){
            register.Hadamard(target);
        }else if (name.equals("Identity")){
            register.Identity();
        }else if (name.equals("Inverse")){
            register.InversePhase(target, Math.PI/2);
        }else if (name.equals("Phase")){
            register.Phase(target, Math.PI/2);
        }else if (name.equals("Shift")) {
            register.T(target);
        }
    }

    @Override
    public void undoOperation() {
        if (name.equals("X")){
            register.Not(target);
        }else if (name.equals("Y")){
            register.Y(target);
        }else  if (name.equals("Z")){
            register.Z(target);
        }else if (name.equals("SqRoot")){
            register.SquareRootNot(target);
            register.Not(target);
        }else if (name.equals("Hadamard")){
            register.Hadamard(target);
        }else if (name.equals("Identity")){
            register.Identity();
        }else if (name.equals("Inverse")){
            register.Phase(target, -Math.PI/2);
        }else if (name.equals("Phase")){
            register.InversePhase(target, -Math.PI/2);
        }else if (name.equals("Shift")) {
           register.inverseT(target);
        }
    }

    public int getTarget(){
        return target;
    }
}
