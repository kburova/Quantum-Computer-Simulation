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
        reinitializeQubits();
        //set 0s to red color here
    }

    public void reinitializeQubits(){
        for (int i = 0; i < numberOfBases; i++) {
            if (i == initialState) amplitudes[initialState] = new Complex(1, 0);
            else amplitudes[i] = new Complex(0, 0);
        }
    }

    public void Hadamard(int targetQubit)
    {
      amplitudes = util.hadamard(amplitudes, numberOfBases, targetQubit);
    }

    public void Identity()
    {
        return;
    }

    public void Phase(int targetQubit, double phase)
    {
      amplitudes = util.phase(amplitudes, numberOfBases, targetQubit, phase);
    }

    public void InversePhase(int targetQubit, double phase)
    {
      amplitudes = util.inversePhase(amplitudes, numberOfBases, targetQubit, phase);
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
      amplitudes = util.squareRootNot(amplitudes,numberOfBases,targetQubit);
    }

    public void Y(int targetQubit)
    {
      amplitudes = util.y(amplitudes,numberOfBases,targetQubit);
    }

    public void Z(int targetQubit)
    {
        amplitudes = util.z(amplitudes,numberOfBases,targetQubit);
    }

    public void ConditionalRotate(int controlQubit, int targetQubit, double phase)
    {
        for (int i = 0; i < numberOfBases; i++)
        {
            if((i & 1<<controlQubit) !=0 && (i & (1<<targetQubit)) != 0)
                amplitudes[i] = amplitudes[i].multiply(Complex.I.multiply(phase).exp());
        }
    }

    public void Swap(int qubit1, int qubit2)
    {
      amplitudes = util.swap(amplitudes,numberOfBases,qubit1,qubit2);
    }

    //Ternary Operators
    public void CCNOT(int controlQubit1, int controlQubit2, int targetQubit)
    {
        Complex swapVar;

        for(int i=0;i<numberOfBases;i++)
        {
            if((i & (1<<controlQubit1)) != 0 && (i & (1<<controlQubit2)) != 0 && (i & (1<<targetQubit)) != 0)
            {
                swapVar = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
                amplitudes[i] = amplitudes[i^(1<<targetQubit)].multiply(1);
                amplitudes[i^(1<<targetQubit)] = swapVar.multiply(1);
            }
        }
    }

    //Variable Operators

    public void Grover()
    {

    }

    public void WalshHadamard()
    {

    }

    public void QFT()
    {
        //This implmentation just works on all qubits -- will need to modify to make it work
        //on ranges.
        Complex omega;
        Complex[] resultant;

        omega = new Complex(0, 2.0*Math.PI/(1.0*numberOfQubits));
        resultant = new Complex[numberOfBases];

        for(int i=0;i<numberOfBases;i++)
        {
            resultant[i] = Complex.ZERO;
            for(int j=0;j<numberOfBases;j++)
                resultant[i] = resultant[i].add(amplitudes[j].multiply(omega.multiply(i*j).exp()));
            resultant[i] = resultant[i].divide(Math.sqrt(numberOfBases));
        }

        amplitudes = resultant;
    }

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

    public int getState() {
        return initialState;
    }

    public Complex[] getAmplitudes(){
        return amplitudes;
    }
}
