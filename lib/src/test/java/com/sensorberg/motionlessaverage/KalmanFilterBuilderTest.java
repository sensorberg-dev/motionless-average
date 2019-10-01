package com.sensorberg.motionlessaverage;

import org.junit.Assert;
import org.junit.Test;

public class KalmanFilterBuilderTest {

  @Test(expected = IllegalArgumentException.class)
  public void builder_should_throw_Exception_when_parameter_r_is_NAN() {
    new KalmanFilter.Builder(Float.NaN, 1f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builder_should_throw_Exception_when_parameter_q_is_NAN() {
    new KalmanFilter.Builder(1f, Float.NaN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builder_should_throw_Exception_when_stateVector_is_NAN() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(1f, 1f);
    builder.stateVector(Float.NaN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builder_should_throw_Exception_when_motionVector_is_NAN() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(1f, 1f);
    builder.motionVector(Float.NaN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builder_should_throw_Exception_when_measurementVector_is_NAN() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(1f, 1f);
    builder.measurementVector(Float.NaN);
  }

  @Test
  public void builder_should_return_non_null_instance() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(1f, 1f);
    builder.stateVector(1f);
    builder.motionVector(2f);
    builder.measurementVector(3f);
    KalmanFilter result = builder.build();

    Assert.assertNotNull(result);
  }

  @Test
  public void create_simplified_build_should_return_non_null_instance() {
    MotionlessAverage motionlessAverage = MotionlessAverage.Builder.createSimplifiedKalmanFilter(1f, 1f);

    Assert.assertNotNull(motionlessAverage);
    Assert.assertTrue(motionlessAverage instanceof KalmanFilter);
  }
}
