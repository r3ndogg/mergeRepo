package au.edu.federation.myapplication;

import java.util.ArrayList;
import java.util.List;

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

    //Specify the constructor to do nothing, to stop people making more than one of these
    private Global(){}

    //gets the instance if it exists, creates it if doesn't
    public static synchronized Global getInstance(){
        if (instance == null){
            instance = new Global();
        }
        return instance;
    }



}
