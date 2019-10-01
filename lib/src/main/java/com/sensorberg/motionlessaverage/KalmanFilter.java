package com.sensorberg.motionlessaverage;

/**
 * Kalman Filter
 * This {@link MotionlessAverage} implementation assumes a signal variance of zero.
 * For the complete Kalman filter that includes the `u` parameter use the {@link KalmanFilter#filter(float, float)}}
 * <p>
 * Based on the implementation from Wouter Bulten: https://github.com/wouterbulten/kalmanjs
 * <p>
 * Further references:
 * <p>
 * Wouter Bulten: "Kalman filters explained: Removing noise from RSSI signals"
 * Wouter Bulten: "Lightweight Javascript library for Noise filtering using Kalman filters"
 * Jenny Röbesaat, Peilin Zhang, Mohamed Abdelaal, and Oliver Theel: "An Improved BLE Indoor Localization with Kalman-Based Fusion: An Experimental Study"
 */
public class KalmanFilter implements MotionlessAverage {

  private final float r;
  private final float q;
  private final float a;
  private final float b;
  private final float c;

  /**
   * Estimated signal without noise
   */
  private float x = Float.NaN;
  private float cov = 0.0f;

  /**
   * Constructor for a Kalman Filter.
   * It's the ration between R and Q that matters, not the actual values.
   * The higher the ratio between R and Q the more the signal gets flatten and the filter is less affected by measurement noise.
   *
   * @param r models the process noise and describes how noisy a system internally is.
   *          How much noise can be expected from the system itself?
   *          When a system is constant R can be set to a (very) low value.
   * @param q resembles the measurement noise.
   *          How much noise is caused by the measurements?
   *          When it's expected that the measurements will contain most of the noise,
   *          it makes sense to set this parameter to a high number (especially in comparison to the process noise).
   *          Usually you make an estimate of R and Q based on measurements or domain knowledge.
   * @param a State vector
   * @param b Motion vector
   * @param c Measurement vector
   */
  private KalmanFilter(float r, float q, float a, float b, float c) {
    this.r = r;
    this.q = q;
    this.a = a;
    this.b = b;
    this.c = c;
  }

  /**
   * Complete Kalman filtering with measured signal and expected variance
   *
   * @param signal the measured signal
   * @param u      the expected variance
   * @return the filtered value
   */
  public float filter(float signal, float u) {
    if (Float.isNaN(x)) {
      this.x = (1 / c) * signal;
      cov = square(1 / c) * q;
    } else {
      float prediction = predict(x, u);
      float uncertainty = uncertainty();

      // kalman gain
      float kalmanGain = uncertainty * c * (1 / ((square(c) * uncertainty) + q));

      // correction
      this.x = prediction + kalmanGain * (signal - (c * prediction));
      cov = uncertainty - (kalmanGain * c * uncertainty);

    }
    return x;
  }

  /**
   * Simplified filtering to conform to the {@link MotionlessAverage} interface.
   * This assumes there's no expected variance (or the variance is unknown.
   *
   * @param newValue the measured signal
   * @return the averaged value
   */
  @Override
  public float average(float newValue) {
    return filter(newValue, 0.0f);
  }

  // private helpers
  private float square(float x) {
    return (float) Math.sqrt(x);
  }

  private float predict(float x, float u) {
    return (a * x) + (b * u);
  }

  private float uncertainty() {
    return (square(a) * cov) + r;
  }

  /**
   * KalmanFilter builder.
   * Defaults state/motion/measurement vectors to 1.0f/0.0f/1.0f respectively
   */
  public static class Builder {

    private float r;
    private float q;
    private float a = 1.0f;
    private float b = 0.0f;
    private float c = 1.0f;

    /**
     * Constructor for the Kalman filter with the required parameters
     *
     * @param r models the process noise and describes how noisy a system internally is.
     *          How much noise can be expected from the system itself?
     *          When a system is constant R can be set to a (very) low value.
     * @param q resembles the measurement noise.
     *          How much noise is caused by the measurements?
     *          When it's expected that the measurements will contain most of the noise,
     *          it makes sense to set this parameter to a high number (especially in comparison to the process noise).
     *          Usually you make an estimate of R and Q based on measurements or domain knowledge.
     */
    public Builder(float r, float q) {
      if (Float.isNaN(r)) {
        throw new IllegalArgumentException("r must be a valid float");
      }
      if (Float.isNaN(q)) {
        throw new IllegalArgumentException("q must be a valid float");
      }
      this.r = r;
      this.q = q;
    }

    public Builder stateVector(float a) {
      if (Float.isNaN(a)) {
        throw new IllegalArgumentException("State vector must be a valid float");
      }
      this.a = a;
      return this;
    }

    public Builder motionVector(float b) {
      if (Float.isNaN(b)) {
        throw new IllegalArgumentException("Motion vector must be a valid float");
      }
      this.b = b;
      return this;
    }

    public Builder measurementVector(float c) {
      if (Float.isNaN(c)) {
        throw new IllegalArgumentException("Measurement vector must be a valid float");
      }
      this.c = c;
      return this;
    }

    public KalmanFilter build() {
      return new KalmanFilter(r, q, a, b, c);
    }
  }
}
