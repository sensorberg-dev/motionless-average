package com.sensorberg.motionlessaverage;

import org.junit.Test;

import static com.sensorberg.motionlessaverage.MotionlessAverage.Builder.NANO;
import static junit.framework.TestCase.assertEquals;

public class TimedFilterTest {

  @Test(expected = IllegalArgumentException.class)
  public void should_block_time_less_than_zero() throws Exception {
    MotionlessAverage.Builder.createTimeDependentAverage(1, 2, -1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_block_max_min_time_direction() throws Exception {
    MotionlessAverage.Builder.createTimeDependentAverage(1, 2, 10, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_block_min_filter_less_than_one() throws Exception {
    MotionlessAverage.Builder.createTimeDependentAverage(0.9999f, 10, 1, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_block_max_min_filter_direction() throws Exception {
    MotionlessAverage.Builder.createTimeDependentAverage(10, 5, 1, 10);
  }

  @Test public void filter_test() throws Exception {
    TimedFilter avg = (TimedFilter) MotionlessAverage.Builder
        .createTimeDependentAverage(1, 3, 100, 1100);
    float firstValue = avg.averageForTime(20, 200 * NANO);
    assertEquals("First value equals inputs", 20.0f, firstValue);
    float secondValue = avg.averageForTime(25, 800 * NANO); // 600ms have passed
    assertEquals("Second value as expected", 22.5f, secondValue);
  }

}
