package com.sensorberg.motionlessaverage.app;

/**
 * For comparison
 */
public class MovingAverage {

  private final float[] data;
  private final int arraySize;
  private boolean arrayFull = false;
  private int pointer = 0;

  public MovingAverage(int arraySize) {
    this.arraySize = arraySize;
    this.data = new float[arraySize];
  }

  public float average(float newValue) {

    // add value
    data[pointer] = newValue;
    pointer++;
    if (pointer == arraySize) {
      pointer = 0;
      arrayFull = true;
    }

    // calculate
    float sum = 0;
    int count = arrayFull ? arraySize : pointer;
    for (int i = 0; i < count; i++) {
      sum += data[i];
    }
    return sum / (float) count;
  }

}
