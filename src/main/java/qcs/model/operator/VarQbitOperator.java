/****************************************************
 Variable Qubits Operator

 Written by:

 Ksenia Burova

 Date: 03/18/2017
 ***************************************************/
package qcs.model.operator;
import qcs.model.Register;
import java.util.ArrayList;

public class VarQbitOperator extends Operator{

    int from, to;
    private ArrayList <Integer> qubits = new ArrayList<>();

    public VarQbitOperator(Register r, String name, int from, int to){
        super(r,name);
        type = "VarQbit";
        this.from = from;
        this.to = to;
        for (int i = from; i <= to; i++){
            qubits.add(i - from,i);
        }
    }

    @Override
    public void doOperation() {
        if (name.equals("QFT")){
            register.QFT(qubits);
        }else if(name.equals("iQFT")){

        }else if(name.equals("WH")){
            register.WalshHadamard(qubits);
        }else if(name.equals("General")){

        }else  if (name.equals("Eval")){

        }else if (name.equals("err")){
            register.Error(qubits);
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
