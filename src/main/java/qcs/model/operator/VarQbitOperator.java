package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class VarQbitOperator extends Operator{
    public VarQbitOperator(Register r, String name){
        super(r,name);
    }

    @Override
    public void doOperation() {
        if (name.equals("QFT")){

        }else if(name.equals("iQFT")){

        }else if(name.equals("WH")){

        }else if(name.equals("General")){

        }else  if (name.equals("Eval")){

        }
    }
}
