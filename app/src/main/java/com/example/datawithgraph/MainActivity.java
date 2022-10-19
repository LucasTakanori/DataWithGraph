package com.example.datawithgraph;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_currentAccel;
    ProgressBar prog_shakeMeter;
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;
    private double changeInAcceleration;

    private  SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z );
            accelerationPreviousValue = accelerationCurrentValue;
            changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);

            //uptdate text views
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_currentAccel = findViewById(R.id.txtAccel);
        prog_shakeMeter = findViewById(R.id.prog_shakeMeter);

        //initialize sensor data
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}