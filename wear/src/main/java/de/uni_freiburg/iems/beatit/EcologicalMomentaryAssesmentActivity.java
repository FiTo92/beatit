package de.uni_freiburg.iems.beatit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class EcologicalMomentaryAssesmentActivity extends WearableActivity implements SensorEventListener {

    private TextView mTextView;
    private SensorManager mSensorManager;
    private Sensor mSensorGyroscope;
    private Sensor mSensorAccelerometer;
    private FileOutputStream fileStream;
    private File file;
    private SimpleDateFormat format;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecological_momentary_assesment);

        mTextView = (TextView) findViewById(R.id.text);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // Create an File in an external storage
        String result = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(result)) {
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "beatit.txt");
            file.setWritable(true);
            if (!file.mkdirs()) {
                mTextView.setText(file.getAbsolutePath());
            }
        }
        // geting the device Time.
        calendar = Calendar.getInstance();
        format = new SimpleDateFormat("HH:mm:ss");


        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            String time = format.format(calendar.getTime());
            String value = "" + time + ";" +(int)event.values[0] + ";" +
                    (int)event.values[1] + ";" + (int)event.values[2] + "\n";
            //mTextView.setText(value);
            writeToFile(value);
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void writeToFile (String line) {
        try {
            fileStream = new FileOutputStream(file, true);
            fileStream.write(line.getBytes());
            fileStream.close();
        } catch (Exception e) {
           // mTextView.setText(e.getMessage());
        }
    }
}
