/**
 * Created by nick on 4/8/17.
 */
import org.apache.commons.math3.complex.Complex;
import qcs.model.QuantumMathUtil;
import org.junit.*;
import static org.junit.Assert.*;

public class QuantumMathUtilTest {

  @Test
  public void testRootOfUnityZeroInput()
  {
    //arrange
    QuantumMathUtil quantumMathUtil = new QuantumMathUtil();

    //act
    Complex[] result = quantumMathUtil.rootOfUnity(0);

    //assert
    assertTrue(result.length == 0);
  }

  @Test
  public void testRootOfUnityOneInput()
  {
    //arrange
    QuantumMathUtil quantumMathUtil = new QuantumMathUtil();

    //act
    Complex[] result = quantumMathUtil.rootOfUnity(1);
    Complex[] expected = new Complex[1];
    expected[0] = new Complex(1);

    //assert
    assertArrayEquals(result, expected);
  }

  @Test
  public void not_is_unitary_with_self()
  {
    //arrange
    QuantumMathUtil util = new QuantumMathUtil();

    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //act
    Complex[] not = util.not(input, 2, 0);
    Complex[] output = util.not(not, 2, 0);

    //assert
    assertTrue(complex_match(input[0], output[0])
      && complex_match(input[1], output[1]));
  }

  @Test
  public void not_qubit_one_is_qubit_zero()
  {
    //arrange
    QuantumMathUtil util = new QuantumMathUtil();

    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    Complex[] actual = util.not(input, 2, 0);

    //act
    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);


    //assert
    assertTrue(complex_match(expected[0], actual[0])
      && complex_match(expected[1], actual[1]));
  }

  private boolean complex_match(Complex a, Complex b)
  {
    return a.getReal() == b.getReal() && a.getImaginary() == b.getImaginary();
  }
}
