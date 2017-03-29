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

import org.apache.commons.math3.complex.Complex;
import qcs.model.Qubit;
import java.util.ArrayList;
import java.util.List;


public class Register {

    private int numberOfQubits, numberOfBases;
    private String name;
    private List< Qubit > qubits;
    private Complex[] amplitudes;

    public Register (String n, int numOfQubits){
        name = n;
        qubits = new ArrayList<>(numOfQubits);
        numberOfQubits = numOfQubits;
        numberOfBases = (int) Math.pow(2.0, (double) numOfQubits);
        amplitudes = new Complex[numberOfBases];

        for (int i = 0; i < numberOfBases; i++) amplitudes[i] = new Complex(0,0);

        //initialize qubits
        for (int i = 0; i < numOfQubits; i++){
            qubits.add(new Qubit(i , name));
        }
    }

//    public void Hadamard(int targetQubit)
//    {
//        Complex alpha, beta;
//
//        for (int i = 0; i < numBases; i++)
//        {
//            if( (i & (1<<targetQubit)) == 0)
//            {
//                alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
//                beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());
//
//                amplitudes[i] = alpha.add(beta).divide(Math.sqrt(2.0));
//                amplitudes[i^(1<<targetQubit)] = alpha.subtract(beta).divide(Math.sqrt(2.0));
//            }
//        }
//    }
//
//    public void Identity()
//    {
//        return;
//    }
//
//    public void Phase(int targetQubit, double phase)
//    {
//        for (int i = 0; i < numBases; i++)
//        {
//            if( (i & (1<<targetQubit)) != 0)
//                amplitudes[i] = amplitudes[i].multiply(Complex.I.multiply(phase).exp());
//        }
//    }
//
//    public void InversePhase(int targetQubit, double phase)
//    {
//        for (int i = 0; i < numBases; i++)
//        {
//            if( (i & (1<<targetQubit)) != 0)
//                amplitudes[i] = amplitudes[i].divide(Complex.I.multiply(phase).exp());
//        }
//    }
//
//    public void T(int targetQubit)
//    {
//        //Phase shit by Pi/4.
//        Phase(targetQubit, Math.PI/4.0);
//    }
//
    public void Not(int targetQubit)
    {
        Complex swapVar;

        for (int i = 0; i < numberOfBases; i++)
        {
            if((i & (1<<targetQubit)) == 0)
            {
                swapVar = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                amplitudes[i] = amplitudes[i^(1<<targetQubit)];
                amplitudes[i^(1<<targetQubit)] = swapVar;
            }
        }
    }
//
//    public void SquareRootNot(int targetQubit)
//    {
//
//    }
//
//    public void Y(int targetQubit)
//    {
//        Complex alpha, beta;
//
//        for (int i = 0; i < numBases; i++)
//        {
//            if( (i & (1<<targetQubit)) == 0)
//            {
//                alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
//                beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());
//
//                amplitudes[i] = beta.multiply(Complex.I);
//                amplitudes[i^(1<<targetQubit)] = alpha.multiply(Complex.I.negate());
//            }
//        }
//    }
//
//    public void Z(int targetQubit)
//    {
//        for (int i = 0; i < numBases; i++)
//        {
//            if((i ^ (1<<targetQubit)) != 0 )
//                amplitudes[i] = amplitudes[i].negate();
//        }
//    }


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
