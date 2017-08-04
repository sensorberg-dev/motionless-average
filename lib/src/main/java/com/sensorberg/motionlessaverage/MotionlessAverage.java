package com.sensorberg.motionlessaverage;

/**
 * MotionlessAverage,
 * because you don't need to allocate a million size array to calculate a moving average
 */
public interface MotionlessAverage {

  float average(float newValue);

  class Builder {
    private static final long NANO = 1000000;

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

      if (minFilter <= 1) {
        throw new IllegalArgumentException("minFilter must be greater than one");
      }

      if (maxFilter <= minFilter) {
        throw new IllegalArgumentException("maxFilter must be greater than minFilter");
      }

      long minTime = minTimeMs * NANO;
      long maxTime = maxTimeMs * NANO;
      float angle = (maxFilter - minFilter) / ((float) minTime - (float) maxTime);
      float constant = maxFilter - angle * (float) minTime;
      return new TimedFilter(angle, constant, minTime, maxTime);
    }


    private static class ConstantFilter implements MotionlessAverage {

      private final float filter;
      private float value = Float.NaN;

      private ConstantFilter(float filter) {
        this.filter = filter;
      }

      @Override public float average(float newValue) {
        if (Float.isNaN(value)) {
          value = newValue;
        } else {
          value = value - ((value - newValue) / filter);
        }
        return value;
      }
    }

    private static class TimedFilter implements MotionlessAverage {

      private final float angle;
      private final float constant;
      private final long minTime;
      private final long maxTime;

      private long lastSeen = 0;
      private float value = Float.NaN;

      private float filter;
      private long diff;

      private TimedFilter(float angle, float constant, long minTime, long maxTime) {
        this.angle = angle;
        this.constant = constant;
        this.minTime = minTime;
        this.maxTime = maxTime;
      }

      @Override public float average(float newValue) {
        long now = System.nanoTime();
        if (lastSeen == 0) {
          value = newValue;
        } else {
          diff = limit(now - lastSeen, minTime, maxTime);
          filter = angle * ((float) diff) + constant;
          value = value - ((value - newValue) / filter);
        }
        lastSeen = now;
        return value;
      }
    }

    private static long limit(long value, long min, long max) {
      return Math.min(Math.max(value, min), max);
    }
  }
}
