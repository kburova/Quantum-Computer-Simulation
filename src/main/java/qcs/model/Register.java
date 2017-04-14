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

public class Register {

    private int numberOfQubits, numberOfBases;
    private String name;
    private int initialState;
    private Complex[] amplitudes;
    private final QuantumMathUtil util = new QuantumMathUtil();

    public Register (String n, int numOfQubits){
        initialState = 0;
        name = n;
        numberOfQubits = numOfQubits;
        numberOfBases = (int) Math.pow(2.0, (double) numOfQubits);
        amplitudes = new Complex[numberOfBases];

        for (int i = 0; i < numberOfBases; i++) amplitudes[i] = new Complex(0,0);
        //set 0s to red color here
    }

    public Complex[] getAmplitudes()
    {
      return amplitudes;
    }

    public Complex getQbitValue(int qbit)
    {
        return amplitudes[qbit];
    }

    public Complex[] Hadamard(int targetQubit)
    {
        Complex alpha, beta;

        for (int i = 0; i < numberOfBases; i++)
        {
            if( (i & (1<<targetQubit)) == 0)
            {
                alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());

                amplitudes[i] = alpha.add(beta).divide(Math.sqrt(2.0));
                amplitudes[i^(1<<targetQubit)] = alpha.subtract(beta).divide(Math.sqrt(2.0));
            }
        }

        return amplitudes;
    }

    public void Identity()
    {
        return;
    }

    public void Phase(int targetQubit, double phase)
    {
        for (int i = 0; i < numberOfBases; i++)
        {
            if( (i & (1<<targetQubit)) != 0)
                amplitudes[i] = amplitudes[i].multiply(Complex.I.multiply(phase).exp());
        }
    }

    public void InversePhase(int targetQubit, double phase)
    {
        for (int i = 0; i < numberOfBases; i++)
        {
            if( (i & (1<<targetQubit)) != 0)
                amplitudes[i] = amplitudes[i].divide(Complex.I.multiply(phase).exp());
        }
    }

    public void T(int targetQubit)
    {
        //Phase shit by Pi/4.
        Phase(targetQubit, Math.PI/4.0);
    }

    public void Not(int targetQubit)
    {
      amplitudes = util.not(amplitudes, numberOfBases, targetQubit);
    }

    // Binary Operators
    public void CNOT(int controlQubit, int targetQubit)
    {
        amplitudes = util.cnot(amplitudes,numberOfBases,targetQubit,controlQubit);
    }

    public void SquareRootNot(int targetQubit)
    {
        Complex alpha, beta;

        for(int i=0;i<numberOfBases;i++)
        {
            if( (i & (1<<targetQubit)) == 0)
            {
                alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());

                amplitudes[i] = alpha.add(beta).add(alpha.multiply(Complex.I)).subtract(beta.multiply(Complex.I)).multiply(.5);
                amplitudes[i^(1<<targetQubit)] = alpha.add(beta).add(beta.multiply(Complex.I)).subtract(alpha.multiply(Complex.I)).multiply(.5);
            }

        }
    }

    public void Y(int targetQubit)
    {
        Complex alpha, beta;

        for (int i = 0; i < numberOfBases; i++)
        {
            if( (i & (1<<targetQubit)) == 0)
            {
                alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());

                amplitudes[i] = beta.multiply(Complex.I);
                amplitudes[i^(1<<targetQubit)] = alpha.multiply(Complex.I.negate());
            }
        }
    }

    public void Z(int targetQubit)
    {
        for (int i = 0; i < numberOfBases; i++)
        {
            if((i & (1<<targetQubit)) != 0 )
                amplitudes[i] = amplitudes[i].negate();
        }
    }

    public void Rotate()
    {
        //Need to ask Dr. Maclennan about this one
    }

    public void Swap(int qubit1, int qubit2)
    {
        Complex swapVar;

        for(int i=0;i<numberOfBases;i++)
        {
            if( ((i & (1<<qubit1))) != 0 && ((i & (1<<qubit2))) == 0)
            {
                swapVar = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                amplitudes[i] = amplitudes[i^(1<<qubit1)^(1<<qubit2)].multiply(1);
                amplitudes[i^(1<<qubit1)^(1<<qubit2)] = swapVar.multiply(1);
            }
        }
    }

    //Ternary Operators


    final public String getName(){
        return name;
    }

    final public int getNumberOfQubits() {
        return numberOfQubits;
    }

    final public int getQubit(int index){
        if ( (initialState & (1 << index)) == 0 ) return 0;
        else return 1;
    }
    public void setQubit(int index, int value){
        if (value == 1){
            initialState |= (1 << index);
        }else{
            initialState &= ~(1 << index);
        }
    }

    //update amplitudes according to newState
    public void updateNewState(){
        for (int i = 0; i < numberOfBases; i++) amplitudes[i] = new Complex(0,0);
        amplitudes[initialState] = new Complex(1,0); //???
    }

}
