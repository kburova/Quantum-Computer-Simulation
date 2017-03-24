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
    int numberOfQubits;

    public Register (String n, int numOfQubits){
        name = n;
        qubits = new ArrayList<>(numOfQubits);

        //initialize qubits
        for (int i = 0; i < numOfQubits; i++){
            qubits.add(new Qubit(i , name));
        }
    }

    final public String getName(){
        return name;
    }

    final public int getNumberOfQubits() {
        return numberOfQubits;
    }

    public void resetNumberOfQubits(int numOfQubits){
        numberOfQubits = numOfQubits;
        qubits = new ArrayList<>(numOfQubits);

        //initialize qubits
        for (int i = 0; i < numOfQubits; i++){
            qubits.set(i, new Qubit(i , name)) ;
        }
    }
    //this is used by our mainapp controller to draw the grid
    final public List<Qubit> getQubits() {
        return qubits;
    }

    public void calculateValue(){
        // TODO: ??? Do we need to calculate anything here??
    }
}
