package qcs.model;

import org.apache.commons.math3.complex.Complex;

/**
 * Created by nick on 4/8/17.
 */
public class QuantumMathUtil {
  public Complex[] rootOfUnity(Integer n)
  {
    if(n < 1) return new Complex[0];
    else
    {
      Complex[] rootsOfUnity = new Complex[n];
      rootsOfUnity[0] = new Complex(1);

      for (int i = 1; i < n; i++)
      {
        Double real = Math.cos(2 * Math.PI * i / n);
        Double imaginary = Math.sin(2 * Math.PI * i / n);
        rootsOfUnity[i] = new Complex(real, imaginary);
      }

      return rootsOfUnity;
    }
  }

  public Complex[][] outerProduct(Complex[][] u, Complex[][] v)
  {
    return matrixMultiply(u, matrixTranspose(v));
  }

  //may actually have to be conjugate transpose
  public Complex[][] matrixTranspose(Complex[][] input)
  {
    //with respect to inputs
    int inner_length = input[0].length;
    int outer_length = input.length;

    Complex[][] output = new Complex[inner_length][];
    //TODO when we know this works and if time permits can be an "index swap"
    //rather than actual swapping values
    for(int i = 0; i < input.length; i++)
    {
      output[i] = new Complex[outer_length];
      for(int j = 0; j < input[i].length; j++)
      {
        output[j][i] = input[i][j];
      }
    }

    return output;
  }

  //http://stackoverflow.com/questions/17623876/matrix-multiplication-using-arrays
  public Complex[][] matrixMultiply(Complex[][] m1, Complex[][] m2) {
    int m1ColLength = m1[0].length; // m1 columns length
    int m2RowLength = m2.length;    // m2 rows length
    if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
    int mRRowLength = m1.length;    // m result rows length
    int mRColLength = m2[0].length; // m result columns length
    Complex[][] mResult = new Complex[mRRowLength][mRColLength];
    for(int i = 0; i < mRRowLength; i++) {         // rows from m1
      for(int j = 0; j < mRColLength; j++) {     // columns from m2
        for(int k = 0; k < m1ColLength; k++) { // columns from m1
          mResult[i][j] = mResult[i][j].add(m1[i][k].multiply(m2[k][j]));
        }
      }
    }
    return mResult;
  }

}
