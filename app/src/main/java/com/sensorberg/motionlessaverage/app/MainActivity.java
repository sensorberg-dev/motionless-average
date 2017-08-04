package com.sensorberg.motionlessaverage.app;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sensorberg.motionlessaverage.MotionlessAverage;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

  private static final int[] COLORS = {
      Color.BLUE,
      Color.RED,
      Color.GREEN,
      Color.GRAY
  };

  private long createdAt;
  private float value = 1;
  private LineChart chart;
  private LineData data;
  private MotionlessAverage averageTimed = MotionlessAverage.Builder.createTimeDependentAverage(10, 100, 5, 50);
  private MotionlessAverage averageConstantFilter = MotionlessAverage.Builder.createConstantFilterAverage(50);
  private MovingAverage movingAverage = new MovingAverage(150);

  private SensorManager sensorManager;
  private Sensor sensor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    setContentView(R.layout.activity_main);

    chart = (LineChart) findViewById(R.id.chart);
    chart.setData(data = new LineData());
    createdAt = SystemClock.elapsedRealtime();

  }

  @Override protected void onResume() {
    super.onResume();
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
  }

  @Override protected void onPause() {
    sensorManager.unregisterListener(this);
    super.onPause();
  }

  private void update() {

    float now = ((float) (SystemClock.elapsedRealtime() - createdAt)) / 1000f;
    float movingAverage = this.movingAverage.average(value);
    float averageTimed = this.averageTimed.average(value);
    float averageConstantFilter = this.averageConstantFilter.average(value);

    Log.d("motionlessaverage", "Value: " + value + "; Moving average: " + movingAverage + "; Timed dependent filter: " + averageTimed);
    add(0, "original", now, value);
    add(1, "Moving avg", now, movingAverage);
    add(2, "Time based filter avg", now, averageTimed);
    add(3, "Constant filter avg", now, averageConstantFilter);
    notifyDataSetChanged();
  }

  private void add(int offset, String name, float time, float value) {
    ILineDataSet set = data.getDataSetByIndex(offset);
    if (set == null) {
      set = createSet(COLORS[offset], name);
      data.addDataSet(set);
    }
    set.addEntry(new Entry(time, value));
  }

  private LineDataSet createSet(int color, String name) {
    LineDataSet set = new LineDataSet(null, name);
    set.setAxisDependency(YAxis.AxisDependency.LEFT);
    set.setColor(color);
    set.setLineWidth(1.2f);
    set.setDrawCircles(false);
    set.setDrawValues(false);
    set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
    return set;
  }

  private void notifyDataSetChanged() {

    float cutout = ((SystemClock.elapsedRealtime() - createdAt) / 1000) - 10f;

    for (int i = 0, size = data.getDataSetCount(); i < size; i++) {
      ILineDataSet set = data.getDataSetByIndex(i);
      while (set.getEntryCount() > 1 && set.getEntryForIndex(0).getX() < cutout) {
        set.removeFirst();
      }
    }

    data.notifyDataChanged();
    chart.notifyDataSetChanged();
    chart.invalidate();

  }

  @Override public void onSensorChanged(SensorEvent event) {
    float[] values = event.values;
    value = (float) Math.sqrt(
        Math.pow(values[0], 2) +
            Math.pow(values[1], 2) +
            Math.pow(values[2], 2));
    update();
  }

  @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { /* */ }
}
