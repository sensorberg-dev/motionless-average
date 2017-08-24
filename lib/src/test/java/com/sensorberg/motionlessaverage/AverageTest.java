package com.sensorberg.motionlessaverage;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AverageTest {

  static final float ACCEPTABLE_VARIANCE = 0.0000005f;

  static final float[] test_values = {
      0f,
      1f,
      2f,
      3f,
      4f,
      0f,
      5f,
      6f,
      7f,
      8f,
      9f,
      10f
  };

  static final float[] expected_results_filter_1dot1 = {
      0f,
      0.9090909091f,
      1.900826446f,
      2.900075131f,
      3.90000683f,
      0.3545460755f,
      4.577686007f,
      5.870698728f,
      6.897336248f,
      7.899757841f,
      8.899977986f,
      9.899997999f
  };

  static final float[] expected_results_filter_2 = {
      0f,
      0.5f,
      1.25f,
      2.125f,
      3.0625f,
      1.53125f,
      3.265625f,
      4.6328125f,
      5.81640625f,
      6.908203125f,
      7.954101562f,
      8.977050781f
  };

  static final float[] expected_results_filter_5 = {
      0f,
      0.2f,
      0.56f,
      1.048f,
      1.6384f,
      1.31072f,
      2.048576f,
      2.8388608f,
      3.67108864f,
      4.536870912f,
      5.42949673f,
      6.343597384f
  };

  @Test
  public void filter_1dot1() throws Exception {
    for (int i = 1; i < test_values.length; i++) {
      float average = MathHelpers.calculateAverage(expected_results_filter_1dot1[i - 1], test_values[i], 1.1f);
      assertEquals("Values do not match for filter 1.1f", expected_results_filter_1dot1[i], average, ACCEPTABLE_VARIANCE);
    }
  }

  @Test
  public void filter_2() throws Exception {
    for (int i = 1; i < test_values.length; i++) {
      float average = MathHelpers.calculateAverage(expected_results_filter_2[i - 1], test_values[i], 2);
      assertEquals("Values do not match for filter 2", expected_results_filter_2[i], average, ACCEPTABLE_VARIANCE);
    }
  }

  @Test
  public void filter_5() throws Exception {
    for (int i = 1; i < test_values.length; i++) {
      float average = MathHelpers.calculateAverage(expected_results_filter_5[i - 1], test_values[i], 5);
      assertEquals("Values do not match for filter 5", expected_results_filter_5[i], average, ACCEPTABLE_VARIANCE);
    }
  }


}
