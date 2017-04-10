package qcs.model.operator;

import qcs.model.Register;

/**
 * Created by kseniaburova on 4/10/17.
 */
public class Toffoli extends Operator{

    int control1;
    int control2;

    public Toffoli(Register r, int q, int c1, int c2, String n){
        super(r,q,n);
        control1 = c1;
        control2 = c2;
    }

    public void doOperation(){

    }

    public int getControl(){
        return control1;
    }
    public int getControl2(){
        return control2;
    }
}
