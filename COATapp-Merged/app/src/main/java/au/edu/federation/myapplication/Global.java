package au.edu.federation.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import au.edu.federation.myapplication.DietaryLogPackage.DietaryLogItem;

/**
 * Modified by Nick to remove activity references, and implement a singleton pattern
 *
 * If you want to access things stored in this, make a local Global variable, and instantiate
 * it with getInstance
 */

public class Global {

    private static Global instance;

    //Temporary storage of dietary log, Needs to sync with on phone Database asap.
    public List<DietaryLogItem> dietaryLogList = new ArrayList<DietaryLogItem>();

    //To store what treasure have been collected. Needs to be refreshed each day.
    public ArrayList<String> treasureList = new ArrayList<String>();

    //Tracks the balance across the application
    public int balance = 0 ;

    public Context appContext = null ;

    //Specify the constructor to do nothing, to stop people making more than one of these
    private Global(){}

    //gets the instance if it exists, creates it if doesn't
    public static synchronized Global getInstance(){
        if (instance == null){
            instance = new Global();
        }
        return instance;
    }

    //Create timer to automatically upload data at set intervals of time
    public void createTimer()
    {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Control.uploadData();
            }
        }, 1000, 15*60*1000);//minutes*seconds*milliseconds, 2*60*1000 = 15 minutes
        //Wait 1 second before executing, execute every 2 minutes after
    }
}
