package com.sensorberg.motionlessaverage;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DiffTest {

  @Test
  public void diff_inside_range() throws Exception {
    assertEquals("Failed to calculate difference within range", 30, MathHelpers.calculateDiff(50, 20, 20, 40));
  }

  @Test
  public void diff_smaller_than_range() throws Exception {
    assertEquals("Failed to calculate difference smaller than range", 40, MathHelpers.calculateDiff(50, 20, 40, 50));
  }

  @Test
  public void diff_greater_than_range() throws Exception {
    assertEquals("Failed to calculate difference grater than range", 25, MathHelpers.calculateDiff(50, 20, 10, 25));
  }
}
