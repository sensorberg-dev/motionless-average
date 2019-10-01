package com.sensorberg.motionlessaverage;

import org.junit.Assert;
import org.junit.Test;

public class KalmanFilterTest {
  @Test
  public void average_one_signal() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    KalmanFilter kalmanFilter = builder.build();

    float result = kalmanFilter.average(5f);

    Assert.assertEquals(5f, result, 0f);
  }

  @Test
  public void average_two_signals() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.average(5f);
    float result = kalmanFilter.average(4f);

    Assert.assertEquals(4.5f, result, 0.01f);
  }

  @Test
  public void average_three_signals() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.average(50f);
    kalmanFilter.average(1f);
    float result = kalmanFilter.average(45f);

    Assert.assertEquals(32f, result, 0.01f);
  }

  @Test
  public void filter_one_signal() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    KalmanFilter kalmanFilter = builder.build();

    float result = kalmanFilter.filter(5f, 0f);

    Assert.assertEquals(5f, result, 0f);
  }

  @Test
  public void filter_two_signals_with_motion() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    builder.motionVector(1f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.filter(5f, 1f);
    float result = kalmanFilter.filter(4f, 3f);

    Assert.assertEquals(6f, result, 0.01f);
  }

  @Test
  public void filter_three_signals_with_motion() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    builder.motionVector(2f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.filter(50f, 0f);
    kalmanFilter.filter(1f, 2f);
    float result = kalmanFilter.filter(55f, 4f);

    Assert.assertEquals(42f, result, 0.01f);
  }

  @Test
  public void filter_two_signals_with_state_vector() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(0.01f, 3f);
    builder.stateVector(2f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.filter(5f, 0f);
    float result = kalmanFilter.filter(4f, 0f);

    Assert.assertEquals(6.48f, result, 0.01f);
  }

  @Test
  public void filter_two_signals_with_r_param() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(2f, 3f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.filter(5f, 0f);
    float result = kalmanFilter.filter(4f, 0f);

    Assert.assertEquals(4.37f, result, 0.01f);
  }

  @Test
  public void filter_three_signals_with_measurment_vector() {
    KalmanFilter.Builder builder = new KalmanFilter.Builder(2f, 3f);
    builder.measurementVector(2f);
    KalmanFilter kalmanFilter = builder.build();

    kalmanFilter.filter(1f, 0f);
    kalmanFilter.filter(5f, 0f);
    float result = kalmanFilter.filter(8f, 0f);

    Assert.assertEquals(6.14f, result, 0.01f);
  }
}
