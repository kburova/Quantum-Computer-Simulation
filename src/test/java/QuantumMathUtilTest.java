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
}
