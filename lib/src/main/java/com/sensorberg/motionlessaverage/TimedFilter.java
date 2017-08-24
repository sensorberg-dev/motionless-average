package com.sensorberg.motionlessaverage;

import static com.sensorberg.motionlessaverage.MathHelpers.calculateAverage;
import static com.sensorberg.motionlessaverage.MathHelpers.calculateDiff;
import static com.sensorberg.motionlessaverage.MathHelpers.calculateY;

class TimedFilter implements MotionlessAverage {

  private final float angle;
  private final float constant;
  private final long minTime;
  private final long maxTime;

  private long lastSeen = 0;
  private float value = Float.NaN;

  TimedFilter(float angle, float constant, long minTime, long maxTime) {
    this.angle = angle;
    this.constant = constant;
    this.minTime = minTime;
    this.maxTime = maxTime;
  }

  @Override public float average(float newValue) {
    return averageForTime(newValue, System.nanoTime());
  }

  float averageForTime(float newValue, long now) {
    if (lastSeen == 0) {
      value = newValue;
    } else {
      long diff = calculateDiff(now, lastSeen, minTime, maxTime);
      float filter = calculateY(angle, constant, diff);
      value = calculateAverage(value, newValue, filter);
    }
    lastSeen = now;
    return value;
  }
}