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

public class Register {

    private String name;
   //private double coefficient;
    private ArrayList < Qubit > qubits;
    int numberOfQubits;

    public Register (String n, int numOfQubits){
        name = n;
        numberOfQubits = numOfQubits;
        qubits = new ArrayList<>(numOfQubits);

        //initialize qubits
        for (int i = 0; i < numberOfQubits; i++){
            qubits.add(new Qubit(i , name));
        }
    }

    final public String getName(){
        return name;
    }

    final public int getNUmberOfQubits(){
        return numberOfQubits;
    }

    public void resetNumberOfQubits(int n){
        numberOfQubits = n;
        qubits = new ArrayList<>(n);

        //initialize qubits
        for (int i = 0; i < n; i++){
            qubits.set(i, new Qubit(i , name)) ;
        }
    }

    public void calculateValue(){
        // TODO: ??? Do we need to calculate anything here
    }
}
