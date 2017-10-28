package au.edu.federation.myapplication.BroadCastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import au.edu.federation.myapplication.GlobalClasses.Global;

/**
 * Created by Nick on 21/10/2017.
 *
 *  This broadcast receiver will be triggered each time the device is booted
 *
 *  This is primarily used to reset alarms for notifications if the device is restarted
 *
 */

public class BootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Resets the alarms on boot
        Global.setWellbeingAlarm();

    }


}
