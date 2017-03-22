/****************************************************
 Register class

 Register may contain up to n qubits

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley
             Chris Martin

 Date: 03/18/2017
 ***************************************************/

package qcs.model;
import java.util.ArrayList;
import java.util.List;

public class Register {

    private String name;
   //private double coefficient;
    private List< Qubit > qubits;

    public Register (String n, int count){
        name = n;
        qubits = new ArrayList<>(count);

        //initialize qubits
        for (int i = 0; i < count; i++){
            qubits.add(new Qubit(i , name));
        }
    }

    //this is used by our mainapp controller to draw the grid
    final public List<Qubit> getQubits() {
        return qubits;
    }

    final public String getName(){
        return name;
    }

    final public int getNumberOfQubits() {
        return qubits.size();
    }

    public void resetNumberOfQubits(int count){
        qubits = new ArrayList<>(count);

        //initialize qubits
        for (int i = 0; i < count; i++){
            qubits.set(i, new Qubit(i , name)) ;
        }
    }

    public void calculateValue(){
        // TODO: ??? Do we need to calculate anything here
    }
}
