package com.example.datawithgraph;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_AccelZ, txt_AccelY, txt_AccelX, txt_GyroZ, txt_GyroY, txt_GyroX, txt_MagZ, txt_MagY, txt_MagX, txt_Azimuth, txt_Pitch, txt_Roll, txt_Yaw, txt_Pitch2, txt_Roll2 , txt_Display, txt_Timer;

    //sensor variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;         //Mide en m/s 2 la fuerza de aceleración que se aplica a un dispositivo en los tres ejes físicos (x, y, z), incluida la fuerza de gravedad.
    private Sensor mGyro;                  //Mide en rad/s la velocidad de rotación de un dispositivo alrededor de cada uno de los tres ejes físicos (x, y, z).
    private Sensor mMagnetometer;          //Mide el campo geomagnético ambiental de los tres ejes físicos (x, y, z) en μT.
    private Sensor mRotation;              //Mide la orientación de un dispositivo mediante los tres elementos del vector de rotación del dispositivo.
    //USAR Note: This sensor type exists for legacy reasons, please use rotation vector sensor type and getRotationMatrix()
    // in conjunction with remapCoordinateSystem() and getOrientation() to compute these values instead.
    private Sensor mOrientation;


    private float[] rMatrix = new float[9];
    private float[] rotVecDeg = new float[9];

    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    private int pointsPlotted = 5;
    private int graphIntervalCounter = 0;
    String path;

    private Viewport viewport;

    private String accel_Data, gyro_Data, mag_Data, rot_data, orientation_data;

    private float display = 0, act = 0, prev = -1;
    private float threshold=20;


    JSONArray dataGyro, dataAccel, dataMag, dataRot, dataOrientation, dataRotDeg;


    boolean recordMode = false;

    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
            new DataPoint(0, 0),
    });

    int i = 0;

    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Calendar calReference = Calendar.getInstance(Locale.ENGLISH);
    private String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS ";
    private String timeFormat = "HH:mm:ss.SSS";
    private SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
    private SimpleDateFormat formatter1 = new SimpleDateFormat(timeFormat);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    int originalTime =  -1;
    long timeReference;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (!recordMode) return;
            if(originalTime == -1) {
                timeReference = currentTimeMillis();
                originalTime = 0;
            }
            cal.setTimeInMillis(currentTimeMillis());
            String date = formatter.format(cal.getTime());
            calReference.setTimeInMillis(currentTimeMillis()-timeReference);
            String time = formatter1.format(calReference.getTime());
            System.out.println(time);
            txt_Timer.setText(time);


            //JSONObject data = new JSONObject();
            JSONObject data1 = new JSONObject();


            /*if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float[] gyro = event.values;
                System.out.println(date + "gyro x = " + gyro[0]);
                System.out.println(date + "gyro y = " + gyro[1]);
                System.out.println(date + "gyro z = " + gyro[2]);

*//*            txt_GyroX.setText("x = " + df.format(gyro[0]));
                txt_GyroY.setText("y = " + df.format(gyro[1]));
                txt_GyroZ.setText("z = " + df.format(gyro[2]));*//*

             *//*try {
                    data.put("x", gyro[0]);
                    data.put("y", gyro[1]);
                    data.put("z", gyro[2]);
                    data.put("timeStamp",  cal.getTimeInMillis());
                    dataGyro.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*//*
            }*/

            /*if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float[] accel = event.values;
                System.out.println(date + "accel x = " + accel[0]);
                System.out.println(date + "accel y = " + accel[1]);
                System.out.println(date + "accel z = " + accel[2]);

*//*                txt_AccelX.setText("x = " + df.format(accel[0]));
                txt_AccelY.setText("y = " + df.format(accel[1]));
                txt_AccelZ.setText("z = " + df.format(accel[2]));*//*

                try {
                    data.put("x", accel[0]);
                    data.put("y", accel[1]);
                    data.put("z", accel[2]);
                    data.put("timeStamp",  cal.getTimeInMillis());
                    dataAccel.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

            /*if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                float[] magnetic = event.values;
                System.out.println(date + "mag x = " + magnetic[0]);
                System.out.println(date + "mag y = " + magnetic[1]);
                System.out.println(date + "mag z = " + magnetic[2]);

*//*                txt_MagX.setText("x = " + df.format(magnetic[0]));
                txt_MagY.setText("y = " + df.format(magnetic[1]));
                txt_MagZ.setText("z = " + df.format(magnetic[2]));*//*
                try {
                    data.put("x", magnetic[0]);
                    data.put("y", magnetic[1]);
                    data.put("z", magnetic[2]);
                    data.put("timeStamp",  cal.getTimeInMillis());
                    dataMag.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                float[] rotVector = event.values;
                /*System.out.println(date + "x*sin(θ/2) = " + rotVector[0]);
                System.out.println(date + "y*sin(θ/2) = " + rotVector[1]);
                System.out.println(date + "z*sin(θ/2) = " + rotVector[2]);
                System.out.println(date + "cos(θ/2) = " + rotVector[3]);
                System.out.println(date + "estimated heading Accuracy " + rotVector[4]);*/


                calculateAngles(rotVecDeg, rotVector);
                System.out.println(date + "Yaw = " + rotVecDeg[0]);
                //System.out.println(date + "Roll = " + rotVecDeg[1]);
                //System.out.println(date + "Pitch = " + rotVecDeg[2]);


                act = rotVecDeg[0] + 180;

                if (prev == -1) prev = act;
                System.out.println("act : " + act);
                System.out.println("prev : " + prev);


                if (prev < threshold && act > (360 - threshold)) {
                    display -= 360 - act + prev;
                } else if (prev > (360 - threshold) && act < threshold) {
                    display += 360 - prev + act;
                } else {
                    display += act - prev;
                }
                System.out.println("display : " + display);

                prev = act;

                txt_Yaw.setText("Yaw = " + df.format(rotVecDeg[0]));
                txt_Display.setText("Delta = " + df.format(display));

                /*try {
                    data.put("x*sin(θ/2) = ", rotVector[0]);
                    data.put("y*sin(θ/2) = ", rotVector[1]);
                    data.put("z*sin(θ/2) = ", rotVector[2]);
                    data.put("cos(θ/2) = ", rotVector[3]);
                    data.put("estimated heading Accuracy " , rotVector[4]);
                    data.put("timeStamp",  cal.getTimeInMillis());
                    dataRot.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                try {
                    data1.put("yaw", rotVecDeg[0]);
                    data1.put("roll", rotVecDeg[1]);
                    data1.put("pitch", rotVecDeg[2]);
                    data1.put("delta", display);
                    data1.put("timeStamp", cal.getTimeInMillis());
                    dataRotDeg.put(data1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            /*if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                float[] orientation = event.values;
                System.out.println(date + "Azimuth = " + orientation[0]);
                System.out.println(date + "Pitch  = " + orientation[1]);
                System.out.println(date + "Roll = " + orientation[2]);

*//*                txt_Azimuth.setText("Azimuth = " + df.format(orientation[0]));
                txt_Pitch.setText("Pitch = " + df.format(orientation[1]));
                txt_Roll.setText("Roll = " + df.format(orientation[2]));*//*

                try {
                    data.put("Azimuth = ", orientation[0]);
                    data.put("Pitch  = ", orientation[1]);
                    data.put("Roll = ", orientation[2]);
                    data.put("timeStamp",  cal.getTimeInMillis());
                    dataOrientation.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/


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
            //update graph
            pointsPlotted++;

            //if(pointsPlotted >= 1000){
            //    pointsPlotted = 1;
            //    series.resetData(new DataPoint[] {new DataPoint(1,0)});
            //}

            series.appendData( new DataPoint(pointsPlotted, display), true, pointsPlotted );
            viewport.setMaxX(pointsPlotted);
            viewport.setMinX(pointsPlotted-1000);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        findViewById(R.id.button_Record).setOnClickListener(this);

//
//        txt_AccelZ = findViewById(R.id.txt_AccelZ);
//        txt_AccelY = findViewById(R.id.txt_AccelY);
//        txt_AccelX = findViewById(R.id.txt_AccelX);
//
//        txt_GyroZ = findViewById(R.id.txt_GyroZ);
//        txt_GyroY = findViewById(R.id.txt_GyroY);
//        txt_GyroX = findViewById(R.id.txt_GyroX);
//
//        txt_MagX = findViewById(R.id.txt_MagX);
//        txt_MagY = findViewById(R.id.txt_MagY);
//        txt_MagZ = findViewById(R.id.txt_MagZ);
//
//       txt_Azimuth = findViewById(R.id.txt_Azimuth);
//        txt_Pitch = findViewById(R.id.txt_Pitch);
//        txt_Roll = findViewById(R.id.txt_Roll);

        txt_Yaw = findViewById(R.id.txt_Yaw);
        txt_Display = findViewById(R.id.txt_Display);
        txt_Timer = findViewById(R.id.txt_Timer);

        //initialize sensor data
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //accel_Data = "ACCELEROMETRO " + mAccelerometer.getName() + " RANGO MAX: " + mAccelerometer.getMaximumRange () + " RESOLUTION: " + mAccelerometer.getResolution()  + " VENDOR " + mAccelerometer.getVendor() ;
            //gyro_Data = "GIROSCOPIO " + mGyro.getName() + " RANGO MAX: " + mGyro.getMaximumRange() + " RESOLUTION: " + mGyro.getResolution()  + " VENDOR " + mGyro.getVendor() ;
            //mag_Data = "MAGNETOMETRO " + mMagnetometer.getName() + " RANGO MAX: " + mMagnetometer.getMaximumRange () + " RESOLUTION: " + mMagnetometer.getResolution()  + " VENDOR " + mMagnetometer.getVendor();
            rot_data = "ROTATION VECTOR " + mRotation.getName() + " RANGO MAX: " + mRotation.getMaximumRange() + " RESOLUTION: " + mRotation.getResolution() + " VENDOR " + mRotation.getVendor();
            //orientation_data = "ORIENTATION " + mOrientation.getName() + " RANGO MAX: " + mOrientation.getMaximumRange () + " RESOLUTION: " + mOrientation.getResolution()  + " VENDOR " + mOrientation.getVendor() ;
        }

        //System.out.println(accel_Data);
        //System.out.println(gyro_Data);
        //System.out.println(mag_Data);
        System.out.println(rot_data);
        //System.out.println(orientation_data);

        //dataGyro = new JSONArray();
        //dataAccel = new JSONArray();
        //dataMag = new JSONArray();
        //dataOrientation = new JSONArray();
        //dataRot = new JSONArray();
        dataRotDeg = new JSONArray();

        Context context = getApplicationContext();
        path = context.getFilesDir().getAbsolutePath();


        //sample graph code
        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScrollable(true);
        viewport.setXAxisBoundsManual(true);
        graph.addSeries(series);

    }

    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        //mSensorManager.registerListener(sensorEventListener, mGyro, SensorManager.SENSOR_DELAY_FASTEST);
        //mSensorManager.registerListener(sensorEventListener, mMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListener, mRotation, SensorManager.SENSOR_DELAY_FASTEST);
        //mSensorManager.registerListener(sensorEventListener, mOrientation, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_Record) {
            if (this.recordMode) {
                JSONObject finalObject = new JSONObject();
                try {
//                    finalObject.put("accel specs",accel_Data);
//                    finalObject.put("gyro specs",gyro_Data);
//                    finalObject.put("mag specs",mag_Data);
                    //finalObject.put("accel",dataAccel);
                    //finalObject.put("gyro",dataGyro);
                    //finalObject.put("mag",dataMag);
                    //finalObject.put("rot",dataRot);
                    finalObject.put("rotDeg", dataRotDeg);
                    //finalObject.put("orientation",dataOrientation);

                    JsonManagement.test(path, cal.getTime().toString().replaceAll(" ", ""), finalObject);

                    //dataAccel = new JSONArray();
                    //dataGyro = new JSONArray();
                    //dataMag = new JSONArray();
                    //dataRot = new JSONArray();
                    dataRotDeg = new JSONArray();
                    display = 0;
                    act = 0;
                    prev = -1;
                    originalTime = -1;

                    //dataOrientation = new JSONArray();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.recordMode = !this.recordMode;

        }
    }

    /**
     * @param result    the array of Euler angles in the order: yaw, roll, pitch
     * @param rotVector the rotation vector
     */
    public void calculateAngles(float[] result, float[] rotVector) {
        //caculate rotation matrix from rotation vector first
        SensorManager.getRotationMatrixFromVector(rMatrix, rotVector);

        //calculate Euler angles now
        SensorManager.getOrientation(rMatrix, result);

        //The results are in radians, need to convert it to degrees
        convertToDegrees(result);
    }

    private void convertToDegrees(float[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (float) Math.toDegrees(vector[i]);
        }
    }
}