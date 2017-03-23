/****************************************************
 Single Qubit class

 Qubit can be written as superposition α|0> + β|1>,
 where α and β are some values such that
 |α|^2+|β|^2 = 1

 Created by: Ksenia Burova
            Parker Diamond
            Nick Kelley
            Chris Martin

 Date: 03/18/2017
 ***************************************************/

package qcs.model;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
import org.apache.commons.math3.linear.ArrayFieldVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.FieldMatrix;

public class Qubit {

    private int id;
    private double state;
    private String register;
    private Complex alpha, beta;
    private FieldMatrix<Complex> qubitVector;

    // initialize qubit to 0 when register is created
    // superposition = 0, if α = 1 and β = 0
    public Qubit(int newId, String registerName)
    {
        id = newId;
        register = registerName;
        alpha = new Complex(1.0,0.0);
        beta = new Complex(0.0,0.0);

        Complex[] qubitArray = {alpha, beta};
        qubitVector = new Array2DRowFieldMatrix<Complex>(qubitArray);
    }

//    public void setId(int newId){
//        id = newId;
//    }
//
//    public void setRegister(String registerName){
//        register = registerName;
//    }

    public void Hadamard()
    {
        Complex normalizer = new Complex(1.0/Math.sqrt(2.0),0);
        Complex a = new Complex(1,0);
        Complex b = new Complex(-1,0);
        Complex[][] transform = { {a,a},{a,b} };
        FieldMatrix<Complex> transformMatrix = new Array2DRowFieldMatrix<Complex>(transform, true);

        qubitVector.preMultiply(transformMatrix);
        qubitVector.scalarMultiply(normalizer);
    }

    public void Identity()
    {

    }

    public void Phase()
    {
        Complex phase = new Complex(0,Math.PI/4.0).exp();
        qubitVector.multiplyEntry(1, 0, phase);
    }

    public void InversePhase()
    {
        Complex phase = new Complex(0,Math.PI/4.0).exp();
        qubitVector.multiplyEntry(1, 0, phase.reciprocal());
    }

    public void T()
    {
        //Phase shit by Pi/8?
    }

    public void Not()
    {
        Complex temp = qubitVector.getEntry(0,0);
        qubitVector.setEntry(0,0,qubitVector.getEntry(1,0));
        qubitVector.setEntry(1,0,temp);
    }

    public void SquareRootNot()
    {
        Complex a,b;
        Complex[][] sqrtXArray;
        FieldMatrix<Complex> sqrtXMatrix;

        a = new Complex(1,1);
        b = new Complex(1,-1);

        sqrtXArray = new Complex[][] {{a,b},{b,a}};
        sqrtXMatrix = new Array2DRowFieldMatrix<Complex>(sqrtXArray, true);

        qubitVector.preMultiply(sqrtXMatrix);
        qubitVector.scalarMultiply(new Complex(.5,0));
    }

    public void Y()
    {
        Complex temp = qubitVector.getEntry(0,0);
        qubitVector.setEntry(0,0,qubitVector.getEntry(1,0).multiply(Complex.I));
        qubitVector.setEntry(1,0,temp.multiply(Complex.I.negate()));
    }

    public void Z()
    {
        qubitVector.setEntry(1,0,qubitVector.getEntry(1,0).negate());
    }

    public void setAlpha(Complex a){
        alpha = a;
    }

    public void setBetta(Complex b){
        beta = b;
    }

    final public int getId(){
        return id;
    }

    final public Complex getAlpha(){
        return alpha;
    }
    final public Complex getBeta(){
        return beta;
    }

    final public String getRegister(){
        return register;
    }

    //calculate superposition to determine color of qubit
    public void calculateState(){
        //TODO: claculations here
    }

    public double getState(){
        return state;
    }
}
