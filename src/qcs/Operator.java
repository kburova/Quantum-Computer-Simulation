/********************************************
 Operator is an abstract class

 Extending classes are Gate and Measure

 */
package qcs;

public abstract class Operator {

    Register register;
    Qubit target;
    String name;

    public Operator (Register r, Qubit q, String n){
        register = r;
        target = q;
        name = n;
    }

    //operation specific to each gate/measurement
    public abstract void doOperation();

}
