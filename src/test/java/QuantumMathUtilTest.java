/**
 * Created by nick on 4/8/17.
 */
import org.apache.commons.math3.complex.Complex;
import qcs.model.QuantumMathUtil;
import org.junit.*;
import static org.junit.Assert.*;

public class QuantumMathUtilTest {
  //setup tested module
  private final QuantumMathUtil util = new QuantumMathUtil();

  @Test
  public void testRootOfUnityZeroInput()
  {
    //act
    Complex[] result = util.rootOfUnity(0);

    //assert
    assertTrue(result.length == 0);
  }

  @Test
  public void testRootOfUnityOneInput()
  {
    //act
    Complex[] result = util.rootOfUnity(1);
    Complex[] expected = new Complex[1];
    expected[0] = new Complex(1);

    //assert
    assertArrayEquals(result, expected);
  }

  @Test
  public void not_is_unitary_with_self()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //act
    Complex[] not = util.not(input, 2, 0);
    Complex[] output = util.not(not, 2, 0);

    //assert
    assertTrue(util.complex_match(input[0], output[0])
      && util.complex_match(input[1], output[1]));
  }

  @Test
  public void not_qubit_one_is_qubit_zero()
  {
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
    assertTrue(util.complex_match(expected[0], actual[0])
      && util.complex_match(expected[1], actual[1]));
  }
}
