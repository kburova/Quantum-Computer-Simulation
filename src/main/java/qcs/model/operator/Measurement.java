package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by apple on 4/10/17.
 */
public class Measurement extends Operator {
    private int qubit;
    private boolean all;

    public Measurement(Register r, String name, int qubit){
        super(r,name);
        this.qubit = qubit;
        all = false;
    }
    public Measurement(Register r, String name){
        super(r,name);
        all = true;
    }
    @Override
    public void doOperation() {
        if (all){
            if (name.equals("CompB")){

            }else if (name.equals("SignB")){

            }else if (name.equals("Trash")){

            }
        }else{
            if (name.equals("CompB")){

            }else if (name.equals("SignB")){

            }else if (name.equals("Trash")){

            }
        }
    }

    public int getQubit(){
        return qubit;
    }
    public boolean isAll(){
        return all;
    }
}
