/********************************************
 Operator is an abstract class

 Extending classes are Gate and Measure

 */
package qcs.model.operator;
import qcs.model.Register;

public abstract class Operator {

    Register register;
    int target;
    String name;

    public Operator (Register r, int q, String n){
        register = r;
        target = q;
        name = n;
    }

    //operation specific to each gate/measurement
    public abstract void doOperation();

    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }

    public String getRegisterName() {
        return register.getName();
    }

    public int getControl(){ return 0;}
    public int getControl2(){ return 0;}
}
