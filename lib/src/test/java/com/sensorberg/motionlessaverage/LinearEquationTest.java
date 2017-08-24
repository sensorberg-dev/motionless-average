package com.sensorberg.motionlessaverage;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LinearEquationTest {

  private static final float ACCEPTABLE_VARIANCE = 0;//0.0000005f;

  private static final float[] angle = {7.7777777f, 3.4736843f, 0.048879836f};
  private static final float[] constant = {12.222222f, 0.30736828f, 45.56008f};
  private static final float[] min_x = {1, 1.23f, 9};
  private static final float[] max_x = {10, 2.56f, 500};
  private static final float[] min_y = {20, 4.58f, 46};
  private static final float[] max_y = {90, 9.2f, 70};

  private static final float[] input_x = {5, 10, 20};
  private static final float[] expected_y = {51.1111105f, 35.04421128f, 46.53767672f};

  @Test
  public void angle_test() throws Exception {
    for (int i = 0; i < angle.length; i++) {
      assertEquals("Wrong angle", angle[i], MathHelpers.calculateAngle(min_x[i], max_x[i], min_y[i], max_y[i]), ACCEPTABLE_VARIANCE);
    }
  }

  @Test
  public void constant_test() throws Exception {
    for (int i = 0; i < angle.length; i++) {
      assertEquals("Wrong constant", constant[i], MathHelpers.calculateConstant(angle[i], min_x[i], min_y[i]), ACCEPTABLE_VARIANCE);
    }
  }

  @Test
  public void value_y_test() throws Exception {
    for (int i = 0; i < angle.length; i++) {
      assertEquals("Wrong value y", expected_y[i], MathHelpers.calculateY(angle[i], constant[i], input_x[i]), ACCEPTABLE_VARIANCE);
    }
  }

}
