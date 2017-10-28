package au.edu.federation.myapplication.GlobalClasses;

import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import au.edu.federation.myapplication.DatabasePackage.Control;
import au.edu.federation.myapplication.DatabasePackage.Exercise;
import au.edu.federation.myapplication.DatabasePackage.Wellbeing;
import au.edu.federation.myapplication.PedometerService.SensorListener;

/**
 * Created by Nick on 30/09/2017.
 *
 * Extends application class to both be created first, and be available app wide
 */

public class Global extends Application {

    private static Global singleton;
    private static TextToSpeechEngine TTSE;
    private static SetWellbeingAlarm SWBA;
    private final String TAG = "Global";

    private static int steps = 0;

    //Map to store each question for the questionnaire, and whether it has been answered today
    public static Map<String, Boolean> questions = new HashMap<>();

    public static Global getInstance(){
        return singleton;
    }

    private void checkPermissions(){
        // If any dangerous permissions are needed to be checked at every runtime
    }


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        loadQuestions();

        SWBA = new SetWellbeingAlarm(this);
        //SWBA.setWellbeingAlarm();

        TTSE = new TextToSpeechEngine(this); //Handles speech for the app

        this.startService(new Intent(this, SensorListener.class));
    }

    //Sends passed text to the text to speech engine for the app to vocalize
    public static void makeSpeech(String text){
        TTSE.speak(text);
    }

    public static void setWellbeingAlarm(){
        SWBA.setWellbeingAlarm();
    }

    private void loadQuestions(){

        Control.inspectWellbeingTable(this);

        Wellbeing wb;
        wb = Control.getWellbeingTableCurrentDay(this);

        if (wb.getSleep() != -1)
        {
            questions.put("I feel tired", true);
        }
        else{
            questions.put("I feel tired", false);
        }

        if (wb.getMood() != -1)
        {
            questions.put("My Mood is", true);
        }
        else{
            questions.put("My Mood is", false);
        }

        if (wb.getEnergy() != -1)
        {
            questions.put("My Energy Level Is", true);
        }
        else{
            questions.put("My Energy Level Is", false);
        }
    }

    public static Boolean questionsAnswered(){

        for (Boolean value : questions.values()) {
            if (!value)
                return false;
        }
        return true;
    }

    public static int getSteps(){
        return steps;
    }

    public static void increaseSteps(int steps1){
        steps = steps1;
    }

    public void loadStepsFromDatabase(){

        Exercise todaysRecord;
        todaysRecord = Control.getExerciseTableCurrentDay(this);

    }

    public void startPedoService(){



    }
}
