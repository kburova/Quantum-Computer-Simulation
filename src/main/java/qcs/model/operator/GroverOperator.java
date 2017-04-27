/****************************************************
Grover Operator

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/

package qcs.model.operator;
import qcs.model.Register;

public class GroverOperator extends Operator {
    int searchValue;

    public GroverOperator(Register r, int searchValue, String name){
        super(r,name);
        type = "Grover";
        this.searchValue = searchValue;
    }

    @Override
    public void doOperation() {
        register.Grover();
    }

    @Override
    public void undoOperation() {
        doOperation();
    }

    public  int getSearchValue(){
        return searchValue;
    }
}
