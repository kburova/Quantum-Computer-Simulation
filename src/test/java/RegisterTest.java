/**
 * Created by nick on 4/9/17.
 */
import org.apache.commons.math3.complex.Complex;
import org.junit.*;
import qcs.model.Register;

import static org.junit.Assert.*;

public class RegisterTest {
  @Test
  public void Hadamard_Unitary_With_Self()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex before = register.getQbitValue(0);

    register.Hadamard(0);
    register.Hadamard(0);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void Identity_Unitary_With_Self()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex before = register.getQbitValue(0);

    register.Identity();
    register.Identity();

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void T_Unitary_With_Self_After_Two_PI_Shift()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex before = register.getQbitValue(0);

    //should result in a full rotation
    for(int i = 0; i < 8; i++)
      register.T(0);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void T_Cyclical_On_Two_Pi_Period()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    register.T(0);
    Complex before = register.getQbitValue(0);

    //should result in a full rotation
    for(int i = 0; i < 8; i++)
      register.T(0);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void Phase_Unitary_With_Self_If_Two_Pi_Shift()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex before = register.getQbitValue(0);

    //should result in a full rotation
    register.Phase(0,2 * Math.PI);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void Phase_Cyclical_On_Two_Pi_Period()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    register.Phase(0,Math.PI);
    Complex before = register.getQbitValue(0);

    //should result in a full rotation
    register.Phase(0,2 * Math.PI);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void Phase_Unitary_With_Inverse_Phase()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex before = register.getQbitValue(0);

    //should result in a full rotation
    register.Phase(0,Math.PI / 4);
    register.InversePhase(0,Math.PI / 4);

    Complex after = register.getQbitValue(0);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }

  @Test
  public void Square_Root_Not_Squared_Is_Not()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex expected = register.getQbitValue(0);


    register.SquareRootNot(0);
    register.SquareRootNot(0);
    register.Not(0);

    Complex actual = register.getQbitValue(0);

    //assert
    assertTrue(expected.getReal() == actual.getReal()
      && expected.getImaginary() == actual.getImaginary());
  }

  @Test
  public void Pauli_Y_Is_Unitary_With_Self_When_Called_Twice()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex expected = register.getQbitValue(0);

    register.Y(0);
    register.Y(0);

    Complex actual = register.getQbitValue(0);

    //assert
    assertTrue(expected.getReal() == actual.getReal()
      && expected.getImaginary() == actual.getImaginary());
  }

  @Test
  public void Pauli_Z_Is_Unitary_With_Self_When_Called_Twice()
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
