/****************************************************
 Register class

 Register may contain up to n qubits, registers has a
 name, initial state and vector of amplitudes.

 All the quantum methods are implemented here as well.

 Created by: Ksenia Burova
             Parker Diamond
             Nick Kelley

 Date: 03/18/2017
 ***************************************************/

package qcs.model;

import org.apache.commons.math3.complex.Complex;

import java.security.SecureRandom;
import java.util.ArrayList;

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
        reinitializeState();
    }

    /** brings register to inital state **/
    public void reinitializeState(){
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
        Phase(targetQubit, Math.PI/4.0);
    }

    public void inverseT(int targetQubit){
        InversePhase(targetQubit, Math.PI/4.0);
    }

    public void Not(int targetQubit)
    {
      amplitudes = util.not(amplitudes, numberOfBases, targetQubit);
    }

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
      amplitudes = util.conditionalRotate(amplitudes, numberOfBases, targetQubit
        , controlQubit, phase);
    }

    public void Swap(int qubit1, int qubit2)
    {
      amplitudes = util.swap(amplitudes,numberOfBases,qubit1,qubit2);
    }

    //Ternary Operators
    public void CCNOT(int controlQubit1, int controlQubit2, int targetQubit)
    {
      amplitudes = util.ccnot(amplitudes,numberOfBases,targetQubit,controlQubit1
        ,controlQubit2);
    }

    //Variable Operators

    public void Grover()
    {

    }

    /*public void QuantumOracle()
    {

    }*/

    public void WalshHadamard(ArrayList<Integer> targetQubits)
    {
      amplitudes = util.walshHadamard(amplitudes,numberOfBases,numberOfQubits,targetQubits);
    }

    public void QFT(ArrayList<Integer> targetQubits)
    {
      amplitudes = util.qft(amplitudes,numberOfBases,targetQubits,numberOfQubits);
    }

    public void MeasureComputation(ArrayList<Integer> targetQubits)
    {
      amplitudes = util.measurement(amplitudes,numberOfBases,targetQubits,new SecureRandom());
    }

    public void MeasureSign(ArrayList<Integer> targetQubits)
    {
        //Tentative Sign Basis Measurement -- Need to check with Dr. Maclennan about correctness
        for(int i=0; i<targetQubits.size();i++) Hadamard(targetQubits.get(i));
        MeasureComputation(targetQubits);
        for(int i=0; i<targetQubits.size();i++) Hadamard(targetQubits.get(i));
    }

    public void Trash(ArrayList<Integer> targetQubits)
    {
        //Perform a measurement, but do not update the qubit states
        MeasureComputation(targetQubits);
    }

    public void Error(ArrayList<Integer> targetQubits)
    {
        int targetQubit;
        Complex[][] errorMatrix;
        SecureRandom RNG;

        RNG = new SecureRandom();
        errorMatrix = new Complex[2][2];

        for(int i=0;i<targetQubits.size();i++)
        {
            targetQubit = 1<<targetQubits.get(i);

            for(int j=0;j<numberOfBases;j++)
            {
                if( (j & targetQubit) == 0 )
                {

                }
            }
        }
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

    public int getState() {
        return initialState;
    }

    public Complex[] getAmplitudes(){
        return amplitudes;
    }
}
