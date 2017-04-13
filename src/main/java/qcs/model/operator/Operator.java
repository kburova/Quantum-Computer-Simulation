/********************************************
 Operator is an abstract class

 Extending classes are Gate and Measure

 */
package qcs.model.operator;
import qcs.model.Register;

public abstract class Operator {

    Register register;
    String name;

    public Operator (Register r, String n){
        register = r;
        name = n;
    }

    //operation specific to each gate/measurement
    public abstract void doOperation();
    public String getName() {
        return name;
    }

    public String getRegisterName() {
        return register.getName();
    }
    public Register getRegister() {return register; }
}
