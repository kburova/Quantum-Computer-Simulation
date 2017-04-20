/**
 * Created by nick on 4/8/17.
 */
import org.apache.commons.math3.complex.Complex;
import qcs.model.QuantumMathUtil;
import org.junit.*;

import java.security.SecureRandom;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuantumMathUtilTest {
  //setup tested module
  private final QuantumMathUtil util = new QuantumMathUtil();

  @Test
  public void qft_experimental_one_qubit_is_hadamard()
  {
    Complex[] input1 = new Complex[2];
    input1[0] = new Complex(0);
    input1[1] = new Complex(1);

    Complex[] input2 = new Complex[2];
    input2[0] = new Complex(0);
    input2[1] = new Complex(1);

    ArrayList target = new ArrayList<Integer>();
    target.add(0);

    Complex[] output = util.qftExperimental(input1,2,target,1);
    Complex[] expected = util.hadamard(input2,2,0);


    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(expected[0]);
    System.out.println(expected[1]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void qft_experimental_two_qubits()
  {
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(1);
    input[2] = new Complex(0);
    input[3] = new Complex(0);

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(1.0/2.0);
    expected[1] = new Complex(0,1.0/2.0);
    expected[2] = new Complex(-1.0/2.0);
    expected[3] = new Complex(0,-1.0/2.0);


    ArrayList target = new ArrayList<Integer>();
    target.add(0);
    target.add(1);

    Complex[] output = util.qftExperimental(input, 4, target, 2);

    System.out.println(expected[0]);
    System.out.println(expected[1]);
    System.out.println(expected[2]);
    System.out.println(expected[3]);
    System.out.println("bla");
    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(output[2]);
    System.out.println(output[3]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void qft_experimental_two_qubits_qft_on_one_qubit_is_hadamard_on_one_qubit()
  {
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(1);
    input[2] = new Complex(0);
    input[3] = new Complex(0);

    Complex[] input1 = new Complex[4];
    input1[0] = new Complex(0);
    input1[1] = new Complex(1);
    input1[2] = new Complex(0);
    input1[3] = new Complex(0);


    Complex[] expected = util.hadamard(input, 4, 1);


    ArrayList target = new ArrayList<Integer>();
    target.add(1);

    Complex[] output = util.qftExperimental(input1, 4, target, 2);

    System.out.println(expected[0]);
    System.out.println(expected[1]);
    System.out.println(expected[2]);
    System.out.println(expected[3]);
    System.out.println("bla");
    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(output[2]);
    System.out.println(output[3]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void qft_experimental_two_qubits_two_one_qubit_qfts()
  {
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(1);
    input[2] = new Complex(0);
    input[3] = new Complex(0);

    Complex[] input1 = new Complex[4];
    input1[0] = new Complex(0);
    input1[1] = new Complex(1);
    input1[2] = new Complex(0);
    input1[3] = new Complex(0);

    Complex[] hadamard_zero = util.hadamard(input, 4, 1);

    System.out.println(hadamard_zero[0]);
    System.out.println(hadamard_zero[1]);
    System.out.println(hadamard_zero[2]);
    System.out.println(hadamard_zero[3]);

    Complex[] expected = util.hadamard(hadamard_zero, 4, 0);


    ArrayList target1 = new ArrayList<Integer>();
    target1.add(0);

    ArrayList target2 = new ArrayList<Integer>();
    target2.add(1);

    Complex[] qft_zero = util.qftExperimental(input1, 4, target2, 2);

    System.out.println(qft_zero[0]);
    System.out.println(qft_zero[1]);
    System.out.println(qft_zero[2]);
    System.out.println(qft_zero[3]);

    Complex[] output = util.qftExperimental(qft_zero, 4, target1, 2);

    System.out.println(expected[0]);
    System.out.println(expected[1]);
    System.out.println(expected[2]);
    System.out.println(expected[3]);
System.out.println("bla");
    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(output[2]);
    System.out.println(output[3]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void qft_experimental_two_qubits_order_of_qubit_operations_does_not_affect_outcome()
  {
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(1);
    input[2] = new Complex(0);
    input[3] = new Complex(0);

    Complex[] input1 = new Complex[4];
    input1[0] = new Complex(0);
    input1[1] = new Complex(1);
    input1[2] = new Complex(0);
    input1[3] = new Complex(0);


    ArrayList target1 = new ArrayList<Integer>();
    target1.add(0);

    ArrayList target2 = new ArrayList<Integer>();
    target2.add(1);


    Complex[] qft_zero_1 = util.qftExperimental(input, 4, target2, 2);
    Complex[] expected = util.qftExperimental(qft_zero_1, 4, target1, 2);

    Complex[] qft_zero = util.qftExperimental(input1, 4, target1, 2);
    Complex[] output = util.qftExperimental(qft_zero, 4, target2, 2);

    System.out.println(expected[0]);
    System.out.println(expected[1]);
    System.out.println(expected[2]);
    System.out.println(expected[3]);
    System.out.println("bla");
    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(output[2]);
    System.out.println(output[3]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void measure_one_with_no_alpha_returns_one_deterministic()
  {
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    //|1>
    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    ArrayList targets = new ArrayList<Integer>();
    targets.add(0);

    //|1>
    Complex[] output = util.measurement(input,2,targets,new SecureRandom(new byte[0]));
    System.out.println(output[0]);
    System.out.println(output[1]);

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void measure_zero_with_no_beta_returns_zero_deterministic()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //|0>
    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1);
    expected[1] = new Complex(0);

    ArrayList targets = new ArrayList<Integer>();
    targets.add(0);

    //|0>
    Complex[] output = util.measurement(input,2,targets,new SecureRandom(new byte[0]));

    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void measure_zero_with_beta_removes_beta_returning_zero_or_one()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    //entangled |0>
    Complex[] complex_input = util.hadamard(input, 2, 0);

    //|0>
    Complex[] expected1 = new Complex[2];
    expected1[0] = new Complex(1);
    expected1[1] = new Complex(0);

    //|1>
    Complex[] expected2 = new Complex[2];
    expected2[0] = new Complex(0);
    expected2[1] = new Complex(1);

    ArrayList targets = new ArrayList<Integer>();
    targets.add(0);

    //|0> or |1>
    Complex[] output = util.measurement(complex_input,2,targets,new SecureRandom(new byte[0]));

    assertTrue(util.complex_vector_match(expected1,output) || util.complex_vector_match(expected2,output));
  }

  //|0> -> |1> -> |0>
  @Test
  public void not_is_unitary_with_self()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1);
    expected[1] = new Complex(0);

    //act
    Complex[] not = util.not(input, 2, 0);
    Complex[] output = util.not(not, 2, 0);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
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

  @Test
  public void conditional_rotate_control_set_unitary_on_two_pi()
  {
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(1);
    expected[3] = new Complex(0);

    //|10>
    Complex[] output = util.conditionalRotate(input, 4, 0, 1, Math.PI*2);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void conditional_rotate_does_nothing_if_control_not_set()
  {
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(1);
    expected[3] = new Complex(0);

    //|10>
    Complex[] output = util.conditionalRotate(input, 4, 1, 0, Math.PI/2);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void conditional_rotate_behaves_like_phase_control_set()
  {
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    Complex[] output = util.conditionalRotate(input, 4, 0, 1, Math.PI/2);
    Complex[] expected = util.phase(input, 4, 0, Math.PI/2);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
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

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(1);
    expected[3] = new Complex(0);

    //|11>
    Complex[] cnot = util.cnot(input, 4, 0, 0);

    //|10>
    Complex[] output = util.cnot(cnot, 4, 0, 0);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
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

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1);
    expected[1] = new Complex(0);

    //|0>
    Complex[] output = util.cnot(input, 2, 0, 0);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void ccnot_controls_set_unitary()
  {

    //|110>
    Complex[] input = new Complex[8];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(0);
    input[3] = new Complex(0);
    input[4] = new Complex(0);
    input[5] = new Complex(0);
    input[6] = new Complex(1);
    input[7] = new Complex(0);

    Complex[] expected = new Complex[8];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(0);
    expected[3] = new Complex(0);
    expected[4] = new Complex(0);
    expected[5] = new Complex(0);
    expected[6] = new Complex(1);
    expected[7] = new Complex(0);

    //|111>
    Complex[] ccnot = util.ccnot(input,8,0,1,2);

    //|110>
    Complex[] output = util.ccnot(ccnot,8,0,1,2);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
  }

  @Test
  public void ccnot_control_set_one_to_zero()
  {
    //|111>
    Complex[] input = new Complex[8];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(0);
    input[3] = new Complex(0);
    input[4] = new Complex(0);
    input[5] = new Complex(0);
    input[6] = new Complex(0);
    input[7] = new Complex(1);

    //|110>
    Complex[] output = util.ccnot(input, 8,0,1,2);

    //|110>
    Complex[] expected = new Complex[8];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(0);
    expected[3] = new Complex(0);
    expected[4] = new Complex(0);
    expected[5] = new Complex(0);
    expected[6] = new Complex(1);
    expected[7] = new Complex(0);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void walsh_hadamard_is_unitary_with_self()
  {
    //arrange
    //|10>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(1);
    input[3] = new Complex(0);

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0);
    expected[2] = new Complex(1);
    expected[3] = new Complex(0);

    ArrayList targets = new ArrayList<Integer>();
    targets.add(0);
    targets.add(1);

    //act
    Complex[] intermediate = util.walshHadamard(input,4,2,targets);
    Complex[] output = util.walshHadamard(intermediate,4,2,targets);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void walsh_hadamard_two_one_qubits_as_expected()
  {
    //|11>
    Complex[] input = new Complex[4];
    input[0] = new Complex(0);
    input[1] = new Complex(0);
    input[2] = new Complex(0);
    input[3] = new Complex(1);

    Complex[] expected = new Complex[4];
    expected[0] = new Complex(0.5);
    expected[1] = new Complex(-0.5);
    expected[2] = new Complex(-0.5);
    expected[3] = new Complex(0.5);


    ArrayList targets = new ArrayList<Integer>();
    targets.add(0);
    targets.add(1);
    System.out.println(expected[0]);
    System.out.println(expected[1]);
    System.out.println(expected[2]);
    System.out.println(expected[3]);

    //act
    Complex[] output = util.walshHadamard(input,4,2,targets);
    System.out.println(output[0]);
    System.out.println(output[1]);
    System.out.println(output[2]);
    System.out.println(output[3]);
    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void hadamard_is_unitary_with_self()
  {
    //|0>
    Complex[] input = new Complex[2];
    input[0] = new Complex(1);
    input[1] = new Complex(0);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(1);
    expected[1] = new Complex(0);

    //act
    Complex[] intermediate = util.hadamard(input,2,0);
    Complex[] output = util.hadamard(intermediate,2,0);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
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

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    //act
    Complex[] output = util.phase(input,2,0,2*Math.PI);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void phase_cyclical_on_two_pi_period()
  {
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    //act
    Complex[] output = util.phase(input,2,0,4*Math.PI);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void phase_unitary_with_inverse_phase()
  {
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    //act
    Complex[] intermediate = util.phase(input,2,0,Math.PI/4);
    Complex[] output = util.inversePhase(intermediate,2,0,Math.PI/4);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void phase_by_pi_div_2()
  {
    //arrange
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(2);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(0,1);

    //act
    Complex[] output = util.phase(input,2,0,Math.PI/2);

    //assert
    assertTrue(util.complex_vector_match(expected, output));
  }

  @Test
  public void square_root_squared_is_not()
  {
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    //act
    Complex[] square_root_not = util.squareRootNot(input,2,0);
    Complex[] expected_not = util.squareRootNot(square_root_not,2,0);
    Complex[] not = util.not(input,2,0);

    //assert
    assertTrue(util.complex_vector_match(expected_not,not));
  }

  @Test
  public void square_root_not_one_one_qubit_is_expected()
  {
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0.5,-0.5);
    expected[1] = new Complex(0.5,0.5);

    //act
    Complex[] output = util.squareRootNot(input,2,0);

    System.out.println(expected[0]);
    System.out.println(expected[1]);

    System.out.println(output[0]);
    System.out.println(output[1]);

    //assert
    assertTrue(util.complex_vector_match(output,expected));
  }

  @Test
  public void pauli_y_unitary_with_self()
  {
    //arrange
    //|1>
    Complex[] input = new Complex[2];
    input[0] = new Complex(0);
    input[1] = new Complex(1);

    Complex[] expected = new Complex[2];
    expected[0] = new Complex(0);
    expected[1] = new Complex(1);

    //act
    Complex[] intermediate = util.y(input,2,0);
    Complex[] output = util.y(intermediate,2,0);

    //assert
    assertTrue(util.complex_vector_match(expected,output));
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
