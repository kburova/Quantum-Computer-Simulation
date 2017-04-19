package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by kseniaburova on 4/15/17.
 */
public class RotateGate extends BinaryOperator {
     double phase;

    public RotateGate(Register r, int target, int control, double radians, String name){
        super(r,target,control,name);
        this.phase = radians;
    }
    @Override
    public void doOperation() {
        System.out.println("Phase: " + phase);
        register.ConditionalRotate(control, target, phase);
    }

    @Override
    public void undoOperation() {
        register.ConditionalRotate(control, target, -phase);
    }

    public double getPhase() {
        return phase;
    }
}

