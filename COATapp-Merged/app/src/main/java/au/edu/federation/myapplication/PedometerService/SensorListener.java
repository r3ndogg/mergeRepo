package au.edu.federation.myapplication.PedometerService;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import au.edu.federation.myapplication.GlobalClasses.Global;

/**
 * Created by Nick on 1/10/2017.
 *
 * This is a background service to get the number of steps recorded from the
 * pedometer hardware
 */

public class SensorListener extends Service implements SensorEventListener {

    private final static long MICROSECONDS_IN_ONE_MINUTE = 60000000;
    private final String TAG = "SensorEventListener";

    @Override
    public void onAccuracyChanged(final Sensor sensor, int accuracy) {
    }


    //When steps are detected
    @Override
    public void onSensorChanged(final SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0){
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER){

            if (PedometerActivity.active){
                PedometerActivity.increaseSteps(value);
            }
            Global.increaseSteps(value);

            Log.d(TAG, Integer.toString(value));
        }
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Created");

        reRegisterSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }

    @Override
    public void onTaskRemoved(final Intent rootIntent) {

    }

    private void reRegisterSensor() {

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        try {
            sm.unregisterListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // enable batching with delay of max 5 min
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_NORMAL, (int) (5 * MICROSECONDS_IN_ONE_MINUTE));
    }
}
