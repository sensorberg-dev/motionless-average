package com.sensorberg.motionlessaverage;

import org.junit.Test;

import static com.sensorberg.motionlessaverage.AverageTest.ACCEPTABLE_VARIANCE;
import static com.sensorberg.motionlessaverage.AverageTest.expected_results_filter_2;
import static com.sensorberg.motionlessaverage.AverageTest.test_values;
import static junit.framework.TestCase.assertEquals;

public class ConstantFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void should_block_filter_less_than_one() throws Exception {
    MotionlessAverage.Builder.createConstantFilterAverage(0.9999f);
  }

  @Test public void filter_test() throws Exception {
    MotionlessAverage avg = MotionlessAverage.Builder.createConstantFilterAverage(2);
    for (int i = 0; i < test_values.length; i++) {
      float average = avg.average(test_values[i]);
      assertEquals("Values do not match for filter 2", expected_results_filter_2[i], average, ACCEPTABLE_VARIANCE);
    }
  }
}
