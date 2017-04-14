/**
 * Created by nick on 4/9/17.
 */
import org.apache.commons.math3.complex.Complex;
import org.junit.*;
import qcs.model.Register;

import static org.junit.Assert.*;

public class RegisterTest {

  @Test
  public void Pauli_Z_Is_Unitary_With_Self()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex expected = register.getQbitValue(0);

    register.Z(0);
    register.Z(0);

    Complex actual = register.getQbitValue(0);

    //assert
    assertTrue(expected.getReal() == actual.getReal()
      && expected.getImaginary() == actual.getImaginary());
  }

  @Test
  public void Swap_Is_Self_Unitary()
  {
    //arrange
    Register register = new Register("", 2);
    register.Not(1);

    //act
    Complex expected = register.getQbitValue(0);

    register.Swap(0,1);
    register.Swap(0,1);

    Complex actual = register.getQbitValue(0);

    //assert
    assertTrue(expected.getReal() == actual.getReal()
      && expected.getImaginary() == actual.getImaginary());
  }
}
