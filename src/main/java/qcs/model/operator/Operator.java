/********************************************
 Operator is an abstract class

 Extending classes are Gate and Measure

 */
package qcs.model.operator;
import qcs.model.Register;

public abstract class Operator {
    private Integer qbitIndexInRegister;
    private String name;
    private Register register;

    public Integer getQbitIndexInRegister() { return qbitIndexInRegister; }
    public String getName() { return name; }
    public Register getRegister() { return register; }

    //I am leaving the register passed here to prevent this change from causing issues
    //in parker's branch
    public Operator(Register r, Integer qbitIndexInRegister, String name)
    {
        this.register = r;
        this.qbitIndexInRegister = qbitIndexInRegister;
        this.name = name;
    }

    //operation specific to each gate/measurement
    public abstract void doOperation();

}
