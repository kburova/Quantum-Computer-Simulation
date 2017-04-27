/****************************************************
 Rotate Operator

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model.operator;
import qcs.model.Register;

public class RotateGate extends BinaryOperator {
     double phase;

    public RotateGate(Register r, int target, int control, double radians, String name){
        super(r,target,control,name);
        this.phase = radians;
    }
    @Override
    public void doOperation() {
        //System.out.println("Phase: " + phase);
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

