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
      throw new IllegalStateException("Artifact ID changed from `lib` to `motionlessaverage`.\n" +
              "Please update your build script accordingly.\n" +
              "Gradle: implementation 'com.sensorberg.motionlessaverage:motionlessaverage:1.2.0'\n" +
              "Maven: <artifactId>motionlessaverage</artifactId> <version>1.2.0</version>");
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
        throw new IllegalStateException("Artifact ID changed from `lib` to `motionlessaverage`.\n" +
                "Please update your build script accordingly.\n" +
                "Gradle: implementation 'com.sensorberg.motionlessaverage:motionlessaverage:1.2.0'\n" +
                "Maven: <artifactId>motionlessaverage</artifactId> <version>1.2.0</version>");
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
        throw new IllegalStateException("Artifact ID changed from `lib` to `motionlessaverage`. " +
                "Please update your build script accordingly." +
                "Gradle: implementation 'com.sensorberg.motionlessaverage:motionlessaverage:1.2.0'" +
                "Maven: <artifactId>motionlessaverage</artifactId> <version>1.2.0</version>");
    }
  }
}
