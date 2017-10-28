package au.edu.federation.myapplication.GlobalClasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nick on 29/08/2017.
 *
 * This is to have persistent storage where it's not necessary to store it into the database
 */

public class SharedPreferencesHandler{

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "CoatPreferences";

    public SharedPreferencesHandler(){}

    private SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getBalance(Context context){
        return getPrefs(context).getInt("balance", 0);
    }

    public void setBalance(Context context, int balance){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("balance", balance);
        editor.apply();
    }

    public void incrementBalance(Context context, int balance){

        int tempVal = getPrefs(context).getInt("balance", 0);
        tempVal += balance;

        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("balance", tempVal);
        editor.apply();
    }


    public void decrementBalance(Context context, int balance){
        int tempVal = getPrefs(context).getInt("balance", 0);
        tempVal -= balance;

        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("balance", tempVal);
        editor.apply();
    }
}
