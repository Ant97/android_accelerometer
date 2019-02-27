package com.example.android_accel;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    Sensor accelerometer;

    TextView xAccess, yAccess, zAccess; // text labels from frontend
    int index;      // keep track of # of samples being averaged (1 -> 10)
    float xSum, ySum, zSum;  // current x, y, z sums

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        index = 0;
        xSum = ySum = zSum = 0;

        xAccess = (TextView) findViewById(R.id.xAccess);
        yAccess = (TextView) findViewById(R.id.yAccess);
        zAccess = (TextView) findViewById(R.id.zAccess);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(MainActivity.this,
                accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        index = (index + 1) % 10;   // increment index 1 -> 10

        /* update the xyzAccess Text Labels with avg when index = 1 and reset xyz sums,
                otherwise, add current values to xyz sum variables
        */
        if (index == 1)
        {
            xAccess.setText(String.format("X Access: %.2f", xSum/10));
            yAccess.setText(String.format("Y Access: %.2f", ySum/10));
            zAccess.setText(String.format("Z Access: %.2f", zSum/10));
            xSum = ySum = zSum = 0;
        }
        xSum += sensorEvent.values[0];
        ySum += sensorEvent.values[1];
        zSum += sensorEvent.values[2];
    }
}
