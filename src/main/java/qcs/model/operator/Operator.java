/****************************************************
 Operator - abstract class

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model.operator;
import qcs.model.Register;

public abstract class Operator {

    Register register;
    String name;
    String type;

    public Operator (Register r, String n){
        register = r;
        name = n;
    }

    //operation specific to each gate/measurement
    public abstract void doOperation();
    public abstract void undoOperation();

    public String getName() {
        return name;
    }

    public String getRegisterName() {
        return register.getName();
    }
    public Register getRegister() {return register; }

    public String getType() {
        return type;
    }
}
