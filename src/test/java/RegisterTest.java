/**
 * Created by nick on 4/9/17.
 */
import org.apache.commons.math3.complex.Complex;
  import qcs.model.QuantumMathUtil;
  import org.junit.*;
import qcs.model.Register;

import static org.junit.Assert.*;

public class RegisterTest {

  @Test
  public void Not_Is_Invertible()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex original = register.getQbitValue(0);

    register.Not(0);
    register.Not(0);

    Complex not_not_original = register.getQbitValue(0);

    //assert
    assertTrue(original.getReal() == not_not_original.getReal()
      && original.getImaginary() == not_not_original.getImaginary());
  }

  @Test
  public void Not_One_Is_Zero()
  {
    //arrange
    Register register = new Register("", 1);

    //act
    Complex expected = new Complex(0);

    register.Not(0);

    Complex not_one = register.getQbitValue(0);

    //assert
    assertTrue(expected.getReal() == not_one.getReal()
      && expected.getImaginary() == not_one.getImaginary());
  }

  @Test
  public void CNot_One_Control_Set_Is_Invertible()
  {
    //arrange
    Register register = new Register("", 2);


    //act
    //set control bit
    register.Not(0);

    Complex original = register.getQbitValue(1);

    register.CNOT(0,1);
    register.CNOT(0,1);

    Complex not_not_original = register.getQbitValue(1);

    //assert
    assertTrue(original.getReal() == not_not_original.getReal()
      && original.getImaginary() == not_not_original.getImaginary());
  }

  @Test
  public void CNot_One_Control_Set_Is_Zero()
  {
    //arrange
    Register register = new Register("", 2);

    //act
    //set control bit
    register.Not(0);

    Complex expected = new Complex(0);

    register.CNOT(0,1);

    Complex not_one = register.getQbitValue(1);

    //assert
    assertTrue(expected.getReal() == not_one.getReal()
      && expected.getImaginary() == not_one.getImaginary());
  }

  @Test
  public void CNot_Control_Not_Set_Yield_No_Change()
  {
    //arrange
    Register register = new Register("", 2);

    //act
    Complex before = register.getQbitValue(1);

    register.CNOT(0,1);

    Complex after = register.getQbitValue(1);

    //assert
    assertTrue(before.getReal() == after.getReal()
      && before.getImaginary() == after.getImaginary());
  }
}
