package au.edu.federation.myapplication.TreasureHunt;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import au.edu.federation.myapplication.R;

/**
 * Created by Nick on 6/08/2017.
 */

public class ActivityData extends Activity
    implements SensorEventListener{

    private static final String TAG = "ActivityData";

    private TextView displaySteps;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;

    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "ActivityDataStarted");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        displaySteps = (TextView) findViewById(R.id.displaySteps);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }


    //If a step, or multiple steps have been taken, add it to the counter
    public void onSensorChanged(SensorEvent event){

        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0){
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            displaySteps.setText("Step Counter Detected : " + value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Resume");
        super.onResume();
        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Stop");
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
    }


}
