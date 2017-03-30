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

}
