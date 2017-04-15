package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class Measurement extends Operator {
    private int from, to;

    public Measurement(Register r, String name, int from, int to){
        super(r,name);
        type = "Measurement";
        this.from = from;
        this.to = to;
    }

    @Override
    public void doOperation() {
        if (name.equals("CompB")){

        }else if (name.equals("SignB")){

        }else if (name.equals("Trash")){

        }
    }

    @Override
    public void undoOperation() {
    }

    public int getFrom(){
        return from;
    }
    public  int getTo(){
        return to;
    }
}
