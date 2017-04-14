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

  //|0> -> |1> -> |0>
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
    assertTrue(util.complex_vector_match(input,output));
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
    assertTrue(util.complex_vector_match(actual,expected));
  }

  //|10> -> |11> -> |10>
  @Test
  public void cnot_control_set_unitary_with_self()
  {
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    //|11>
    Complex[] cnot = util.cnot(input, 4, 0, 0);

    //|10>
    Complex[] output = util.cnot(cnot, 4, 0, 0);

    //assert
    assertTrue(util.complex_vector_match(input,output));
  }

  @Test
  public void cnot_control_set_one_to_zero()
  {
    //|11>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(0);
    input[3] = new Complex(1);

    //|10>
    Complex[] output = util.cnot(input, 4, 0, 1);

    //|10>
    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(1);
    expected[3] = new Complex(0);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void cnot_does_nothing_when_control_bit_not_set()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //|0>
    Complex[] output = util.cnot(input, 2, 0, 0);

    //assert
    assertTrue(util.complex_vector_match(input, output));
  }

  @Test
  public void hadamard_is_unitary_with_self()
  {
    //arrange
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //act
    Complex[] intermediate = util.hadamard(input,2,0);
    Complex[] output = util.hadamard(intermediate,2,0);

    //assert
    assertTrue(util.complex_vector_match(input, output));
  }

  @Test
  public void hadamard_on_single_qubit_zero()
  {
    //|0> -> (1/(sqrt(2))*(|0> + |1>)
    //arrange
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //act
    Complex[] output = util.hadamard(input,2,0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1/Math.sqrt(2));
    expected[1] = new Complex(1/Math.sqrt(2));

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void hadamard_on_single_qubit_one()
  {
    //|1> -> (1/(sqrt(2))*(|0> - |1>)
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] output = util.hadamard(input,2,0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1/Math.sqrt(2));
    expected[1] = new Complex(-1/Math.sqrt(2));

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void phase_unitary_with_self_if_two_pi_shift()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] output = util.phase(input,2,0,2*Math.PI);

    //assert
    assertTrue(util.complex_vector_match(input, output));
  }

  @Test
  public void phase_cyclical_on_two_pi_period()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] output = util.phase(input,2,0,4*Math.PI);

    //assert
    assertTrue(util.complex_vector_match(input, output));
  }

  @Test
  public void phase_unitary_with_inverse_phase()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] intermediate = util.phase(input,2,0,Math.PI/4);
    Complex[] output = util.inversePhase(intermediate,2,0,Math.PI/4);

    //assert
    assertTrue(util.complex_vector_match(input, output));
  }

  @Test
  public void square_root_squared_is_not()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] square_root_not = util.squareRootNot(input,2,0);
    Complex[] expected_not = util.squareRootNot(square_root_not,2,0);
    Complex[] not = util.not(input,2,0);

    //assert
    assertTrue(util.complex_vector_match(expected_not,not));
  }

  @Test
  public void pauli_y_unitary_with_self()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] intermediate = util.y(input,2,0);
    Complex[] output = util.y(input,2,0);

    //assert
    assertTrue(util.complex_vector_match(input,output));
  }

  @Test
  public void pauli_y_qubit_one_returns_imaginary_minus_zero()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] output = util.y(input,2,0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0,-1);
    expected[1] = new Complex(0);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void pauli_y_qubit_zero_returns_imaginary_one()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //act
    Complex[] output = util.y(input,2,0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0,1);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void pauli_z_qubit_one_too_minus_one()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //act
    Complex[] output = util.z(input,2,0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(-1);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void swap_swaps()
  {
    //arrange
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    //act
    Complex[] output = util.swap(input,4,0,1);

    //|01>
    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);
    expected[2] = new Complex(0);
    expected[3] = new Complex(0);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }
}
