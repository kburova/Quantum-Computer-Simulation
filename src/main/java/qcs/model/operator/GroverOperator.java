package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class GroverOperator extends Operator {
    int searchValue;
    public GroverOperator(Register r, int searchValue, String name){
        super(r,name);
        type = "Grover";
        this.searchValue = searchValue;
    }

    @Override
    public void doOperation() {
        //Grover
    }

    @Override
    public void undoOperation() {
        //inverse Grover
    }

    public  int getSearchValue(){
        return searchValue;
    }
}
