package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class VarQbitOperator extends Operator{

    int from, to;

    public VarQbitOperator(Register r, String name, int from, int to){
        super(r,name);
        type = "VarQbit";
        this.from = from;
        this.to = to;
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

    @Override
    public void undoOperation() {
        if (name.equals("QFT")){

        }else if(name.equals("iQFT")){

        }else if(name.equals("WH")){

        }else if(name.equals("General")){

        }else  if (name.equals("Eval")){

        }
    }

    public int getFrom(){
        return from;
    }
    public  int getTo(){
        return to;
    }
}
