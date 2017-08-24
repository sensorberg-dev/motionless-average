package com.sensorberg.motionlessaverage;

import static com.sensorberg.motionlessaverage.MathHelpers.calculateAverage;

class ConstantFilter implements MotionlessAverage {

  private final float filter;
  private float value = Float.NaN;

  ConstantFilter(float filter) {
    this.filter = filter;
  }

  @Override public float average(float newValue) {
    if (Float.isNaN(value)) {
      value = newValue;
    } else {
      value = calculateAverage(value, newValue, filter);
    }
    return value;
  }
}