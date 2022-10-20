package com.example.datawithgraph;

import static java.lang.System.currentTimeMillis;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txt_currentAccel, txt_prevAccel, txt_Accel;
    ProgressBar prog_shakeMeter;

    //sensor variables
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    private  Sensor mGyro;
    private  Sensor mMagnetometer;

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    private int pointsPlotted = 5;
    private int graphIntervalCounter = 0;

    private Viewport viewport;



    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });

    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS ";
    private SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    private  SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            cal.setTimeInMillis(currentTimeMillis ());
            String date = formatter.format(cal.getTime());

            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                float[] gyro = event.values;
                System.out.println(date + "gyro x = " + gyro[0]);
                System.out.println(date + "gyro y = " + gyro[1]);
                System.out.println(date + "gyro z = " + gyro[2]);
            }

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float[] accel = event.values;
                System.out.println(date + "accel x = " + accel[0]);
                System.out.println(date + "accel y = " + accel[1]);
                System.out.println(date + "accel z = " + accel[2]);

            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                float[] magnetic = event.values;
                System.out.println(date + "mag x = " + magnetic[0]);
                System.out.println(date + "mag y = " + magnetic[1]);
                System.out.println(date + "mag z = " + magnetic[2]);

            }

//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//
//            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z );
//            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
//            accelerationPreviousValue = accelerationCurrentValue;
//
//            //update text views
//            txt_currentAccel.setText("Current = " + (int)accelerationCurrentValue);
//            txt_prevAccel.setText("Prev = " + (int)accelerationPreviousValue);
//            txt_Accel.setText("Acceleration change = " + (int)changeInAcceleration);
//
//            prog_shakeMeter.setProgress((int)changeInAcceleration);
//            //update graph
//            pointsPlotted++;
//
////            if(pointsPlotted >= 1000){
////                pointsPlotted = 1;
////                series.resetData(new DataPoint[] {new DataPoint(1,0)});
////            }
//
//            series.appendData( new DataPoint(pointsPlotted, changeInAcceleration), true, pointsPlotted );
//            viewport.setMaxX(pointsPlotted);
//            viewport.setMinX(pointsPlotted-200);
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
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //sample graph code
        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScrollable(true);
        viewport.setXAxisBoundsManual(true);
        graph.addSeries(series);

    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}