package com.example.datawithgraph;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    TextView txt_currentAccel, txt_prevAccel, txt_Accel;
    ProgressBar prog_shakeMeter;
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;


    private  SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z );
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            //update text views
            txt_currentAccel.setText("Current = " + (int)accelerationCurrentValue);
            txt_prevAccel.setText("Prev = " + (int)accelerationPreviousValue);
            txt_Accel.setText("Acceleration change = " + (int)changeInAcceleration);

            prog_shakeMeter.setProgress((int)changeInAcceleration);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_currentAccel = findViewById(R.id.txt_currentAccel);
        txt_prevAccel = findViewById(R.id.txt_prevAccel);
        txt_Accel = findViewById(R.id.txt_Accel);

        prog_shakeMeter = findViewById(R.id.prog_shakeMeter);

        //initialize sensor data
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //sample graph code
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

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