package com.sensorberg.motionlessaverage;

import static com.sensorberg.motionlessaverage.MathHelpers.calculateAngle;
import static com.sensorberg.motionlessaverage.MathHelpers.calculateConstant;

/**
 * MotionlessAverage,
 * because you don't need to allocate a million size array to calculateAverage a moving average
 */
public interface MotionlessAverage {

  float average(float newValue);

  class Builder {
    static final long NANO = 1000000;

    /**
     * This static factory creates an average with a constant filter.
     * This is ideal for scenarios where the update rate of the value being averaged is somewhat constant.
     * For example: Sensor values.
     *
     * @param filter value for the filter, must be greater than 1
     * @return a MotionlessAverage using a constant filter
     */
    public static MotionlessAverage createConstantFilterAverage(float filter) {
      if (filter < 1f) {
        throw new IllegalArgumentException("Filter value must be greater than 1");
      }
      return new ConstantFilter(filter);
    }

    /**
     * This static factory creates an average with a filter based on frequency of averaging.
     * This is ideal for scenarios where the update rate of the value being averaged varies.
     * For example: bluetooth scans
     *
     * @param minFilter minimum allowed filter value. Must be greater than one
     * @param maxFilter maximum allowed filter value. Must be greater than minFilter
     * @param minTimeMs minimum allowed update period (in milliseconds). Must be greater than zero
     * @param maxTimeMs maximum allowed update period (in milliseconds). Must be greater than minTimeMs
     * @return a MotionlessAverage using time based filter
     */
    public static MotionlessAverage createTimeDependentAverage(float minFilter, float maxFilter, long minTimeMs, long maxTimeMs) {

      if (minTimeMs <= 0) {
        throw new IllegalArgumentException("minTimeMs must be greater than zero");
      }

      if (maxTimeMs <= minTimeMs) {
        throw new IllegalArgumentException("maxTimeMs must be greater than minTimeMs");
      }

      if (minFilter < 1) {
        throw new IllegalArgumentException("minFilter must be greater than one or equal");
      }

      if (maxFilter <= minFilter) {
        throw new IllegalArgumentException("maxFilter must be greater than minFilter");
      }

      long minTime = minTimeMs * NANO;
      long maxTime = maxTimeMs * NANO;
      float angle = calculateAngle(minTime, maxTime, minFilter, maxFilter);
      float constant = calculateConstant(angle, maxTime, maxFilter);
      return new TimedFilter(angle, constant, minTime, maxTime);
    }
  }

  class Helper {
    /**
     * Executes the calculations of the constant filter average, without instantiating a ConstantFilter object
     *
     * @param prevValue previously calculated value
     * @param newValue  newly received value
     * @param filter    filter value
     * @return new value for the averaging
     */
    public float calculateConstantFilterAverage(float prevValue, float newValue, float filter){
        return MathHelpers.calculateAverage(prevValue, newValue, filter);
    }
  }
}
