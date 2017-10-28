package au.edu.federation.myapplication.GlobalClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.federation.myapplication.DietaryLogPackage.DietaryLogItem;

/**
 * Modified by Nick to remove activity references, and implement a singleton pattern
 *
 * If you want to access things stored in this, make a local Global variable, and instantiate
 * it with getInstance
 */

public class GlobalOld {

    private static GlobalOld instance;

    //Temporary storage of dietary log, Needs to sync with on phone Database asap.
    public List<DietaryLogItem> dietaryLogList = new ArrayList<DietaryLogItem>();

    //To store what treasure have been collected. Needs to be refreshed each day.
    public ArrayList<String> treasureList = new ArrayList<String>();

    //Map to store each question for the questionnaire, and whether it has been answered today
    public static Map<String, Boolean> questions = new HashMap<>();

    static {
        questions.put("I felt happy", false);
        questions.put("I enjoyed school", false);
        questions.put("I enjoyed spending time with friends", false);
        questions.put("I enjoyed spending time with my family", false);
        questions.put("I felt good about myself", false);
    }

    //Tracks the balance across the application
    public int balance = 0 ;

    //Specify the constructor to do nothing, to stop people making more than one of these
    private GlobalOld(){}

    //gets the instance if it exists, creates it if doesn't
    public static synchronized GlobalOld getInstance(){
        if (instance == null){
            instance = new GlobalOld();
        }
        return instance;
    }
}
