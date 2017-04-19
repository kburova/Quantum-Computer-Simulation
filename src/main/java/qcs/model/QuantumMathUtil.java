package qcs.model;

import org.apache.commons.math3.complex.Complex;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by nick on 4/8/17.
 * It is my intention that this is a function module full of useful math ops
 * That being said there should be no class level state unless it is declared
 * final.
 * As it grows we can break it down into further modules.
 */
public class QuantumMathUtil {
  public Complex[] qftAllQubits(Complex[] amplitudes, int numberOfBases, int numberOfQubits)
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

    return resultant;
  }

  public Complex[] qftSubset(Complex[] amplitudes, int numberOfBases, int firstQubitIndex, int qubitCount)
  {
    amplitudes = qftSubroutine(amplitudes, numberOfBases, firstQubitIndex, qubitCount);

    for(int i = 0; i < qubitCount / 2; i++)
    {
      int to = i;
      int from = qubitCount - 1 - i;
      amplitudes = swap(amplitudes,numberOfBases,to,from);
    }

    return amplitudes;
  }

  private Complex[] qftSubroutine(Complex[] amplitudes, int numberOfBases, int firstQubitIndex, int qubitCount)
  {
    if(qubitCount == 1)
    {
      return hadamard(amplitudes,numberOfBases,firstQubitIndex);
    }
    else
    {
      amplitudes = qftSubset(amplitudes, numberOfBases, firstQubitIndex, qubitCount - 1);
      int lastIndex = qubitCount - 1;
      for(int i = 0; i < lastIndex; i++)
      {
        double phase = Math.PI / Math.pow(2,lastIndex - i);
        amplitudes = conditionalRotate(amplitudes,numberOfBases,i,lastIndex,phase);
      }
      return hadamard(amplitudes,numberOfBases,lastIndex);
    }
  }

  public Complex[] conditionalRotate(Complex[] amplitudes, int numberOfBases
    , int targetQubit, int controlQubit, double phase)
  {
    for (int i = 0; i < numberOfBases; i++)
      if((i & 1<<controlQubit) !=0 && (i & (1<<targetQubit)) != 0)
        amplitudes[i] = amplitudes[i].multiply(Complex.I.multiply(phase).exp());
    return amplitudes;
  }

  public Complex[] phase(Complex[] amplitudes, int numberOfBases, int targetQubit
    , double phase)
  {
    for (int i = 0; i < numberOfBases; i++)
    {
      if ((i & (1 << targetQubit)) != 0)
        amplitudes[i] = amplitudes[i].multiply(Complex.I.multiply(phase).exp());
    }
    return amplitudes;
  }

  public Complex[] not(Complex[] amplitudes, int numberOfBases, int targetQubit)
  {
    for (int i = 0; i < numberOfBases; i++)
      amplitudes = notHelpful(amplitudes, targetQubit, i);
    return amplitudes;
  }

  //to avoid confusion use this index guide for qubits
  //qubit |000>
  //index  210
  public Complex[] cnot(Complex[] amplitudes, int numberOfBases, int targetQubit
    , int controlQubit)
  {
    for(int i=0;i<numberOfBases;i++)
      if((i & (1<<controlQubit)) != 0)
        amplitudes = notHelpful(amplitudes, targetQubit, i);

    return amplitudes;
  }

  public Complex[] ccnot(Complex[] amplitudes, int numberOfBases
    , int targetQubit, int controlQubit1, int controlQubit2)
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
    return amplitudes;
  }

  public Complex[] squareRootNot(Complex[] amplitudes, int numberOfBases, int targetQubit)
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
    return amplitudes;
  }

  private Complex[] notHelpful(Complex[] amplitudes, int targetQubit, int i)
  {
    if((i & (1<<targetQubit)) == 0)
    {
      Complex swapVar = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
      amplitudes[i] = amplitudes[i^(1<<targetQubit)].multiply(1);
      amplitudes[i^(1<<targetQubit)] = swapVar;
    }

    return amplitudes;
  }

  public Complex[] walshHadamard(Complex[] amplitudes, int numberOfBases, int numberOfQubits, ArrayList<Integer> targetQubits)
  {
    Complex alpha, beta;
    int targetQubit;

    for(int i=numberOfQubits-1;i>=0;i--)
    {
      targetQubit = targetQubits.indexOf(i);

      if(targetQubit != -1)
      {
        targetQubit = 1<<targetQubit;
        for (int j = 0; j < numberOfBases; j++)
        {
          if ((j & targetQubit) == 0)
          {
            alpha = new Complex(amplitudes[j].getReal(), amplitudes[j].getImaginary());
            beta = new Complex(amplitudes[j ^ targetQubit].getReal(), amplitudes[j ^ targetQubit].getImaginary());

            amplitudes[j] = alpha.add(beta).divide(Math.sqrt(2.0));
            amplitudes[j ^ targetQubit] = alpha.subtract(beta).divide(Math.sqrt(2.0));
          }
        }
      }
    }
    return amplitudes;
  }

  public Complex[] measurement(Complex[] amplitudes, int numberOfBases, ArrayList<Integer> targetQubits, SecureRandom RNG)
  {
    int targetQubit;
    double sum;

    for(int i=0;i<targetQubits.size();i++)
    {
      sum = 0.0;
      targetQubit = 1<<targetQubits.get(i);

      for(int j=0;j<numberOfBases;j++)
      {
        if((j & targetQubit) != 0)
          sum += amplitudes[j].abs()*amplitudes[j].abs();
      }

      System.out.println(sum);

      if(RNG.nextDouble() <= sum)
      {
        for(int j =0;j<numberOfBases;j++)
        {
          if((j & targetQubit) == 0) amplitudes[j] = Complex.ZERO;
          else if (sum != 0)amplitudes[j] = amplitudes[j].divide(Math.sqrt(sum));
          else;
        }
      }
      else
      {
        for(int j =0;j<numberOfBases;j++)
        {
          if((j & targetQubit) != 0) amplitudes[j] = Complex.ZERO;
          else if(sum != 0) amplitudes[j] = amplitudes[j].divide(Math.sqrt(sum));
          else;
        }
      }
    }

    return amplitudes;
  }

  public Complex[] hadamard(Complex[] amplitudes, int numberOfBases
    , int targetQubit)
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

  public Complex[] inversePhase(Complex[] amplitudes, int numberOfBases, int targetQubit
  , double phase)
  {
    for (int i = 0; i < numberOfBases; i++)
      if( (i & (1<<targetQubit)) != 0)
        amplitudes[i] = amplitudes[i].divide(Complex.I.multiply(phase).exp());
    return amplitudes;
  }

  public Complex[] y(Complex[] amplitudes, int numberOfBases, int targetQubit)
  {
    Complex alpha, beta;

    for (int i = 0; i < numberOfBases; i++)
    {
      if( (i & (1<<targetQubit)) == 0)
      {
        alpha = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
        beta = new Complex(amplitudes[i^(1<<targetQubit)].getReal(), amplitudes[i^(1<<targetQubit)].getImaginary());

        amplitudes[i] = beta.multiply(Complex.I.negate());
        amplitudes[i^(1<<targetQubit)] = alpha.multiply(Complex.I);
      }
    }
    return amplitudes;
  }

  public Complex[] z(Complex[] amplitudes, int numberOfBases, int targetQubit)
  {
    for (int i = 0; i < numberOfBases; i++)
      if((i & (1<<targetQubit)) != 0 )
        amplitudes[i] = amplitudes[i].negate();
    return amplitudes;
  }

  //to avoid confusion use this index guide for qubits
  //qubit |000>
  //index  210
  public Complex[] swap(Complex[] amplitudes, int numberOfBases, int to, int from)
  {
    Complex swapVar;

    for(int i=0;i<numberOfBases;i++)
    {
      if( ((i & (1<<to))) != 0 && ((i & (1<<from))) == 0)
      {
        swapVar = new Complex(amplitudes[i].getReal(), amplitudes[i].getImaginary());
        amplitudes[i] = amplitudes[i^(1<<to)^(1<<from)].multiply(1);
        amplitudes[i^(1<<to)^(1<<from)] = swapVar.multiply(1);
      }
    }
    return amplitudes;
  }

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

  public boolean complex_vector_match(Complex[] a, Complex[] b)
  {
    if(a.length != b.length) return false;
    else
      for (int i = 0; i < a.length; i++)
        if(!complex_match(a[i], b[i])) return false;
    return true;
  }

  //checks to 4 places
  public boolean complex_match(Complex a, Complex b)
  {
    return Math.abs(a.getReal() - b.getReal()) < 1e-4
      && Math.abs(a.getImaginary() - b.getImaginary()) < 1e-4;
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
