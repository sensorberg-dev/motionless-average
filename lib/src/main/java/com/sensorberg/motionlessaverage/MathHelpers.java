package com.sensorberg.motionlessaverage;

class MathHelpers {
  static long calculateDiff(long now, long last, long min, long max) {
    return Math.min(Math.max(now - last, min), max);
  }

  static float calculateAverage(float prevValue, float newValue, float filter) {
    return prevValue - ((prevValue - newValue) / filter);
  }

  static float calculateY(float angle, float constant, float valueX) {
    return angle * valueX + constant;
  }

  static float calculateAngle(float minX, float maxX, float minY, float maxY) {
    return (maxY - minY) / (maxX - minX);
  }

  static float calculateConstant(float angle, float x, float y) {
    return y - angle * x;
  }
}
