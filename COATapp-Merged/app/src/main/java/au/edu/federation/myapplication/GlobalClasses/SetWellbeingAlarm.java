package au.edu.federation.myapplication.GlobalClasses;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import au.edu.federation.myapplication.R;
import au.edu.federation.myapplication.Services.CreateQNotifyService;
import au.edu.federation.myapplication.WellBeingPackage.WellbeingPopUp;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Nick on 22/10/2017.
 */

public class SetWellbeingAlarm {

    private final int TODAY = 0;
    private final int TOMORROW = 1;
    private final int TEST = 3;
    private final String TAG = "SetWellbeingAlarm";

    private Context context;

    public SetWellbeingAlarm(Context context){
        this.context = context;
        Log.i(TAG, "Created");

    }

    public void setWellbeingAlarm(){

        //If all questions have been answered, don't create a notification,
        //just schedule an alarm for tomorrow
        if (Global.questionsAnswered()){
            setAlarm(TOMORROW);
            Log.d(TAG, "All questions answered");
        }
        else{
            setAlarm(TEST);
        }

    }

    private void setAlarm(int alarmDay){

        Intent myIntent = new Intent(context, CreateQNotifyService.class);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();

        switch (alarmDay){
            case TOMORROW:
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                break;
            case TODAY:
                calendar.set(Calendar.HOUR_OF_DAY, getHour() + 2);
                break;
            case TEST:
                Log.d(TAG, "Test");
                calendar.add(Calendar.SECOND, 10);
                break;
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

    }

    private void createNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText("Please answer a question")
                .setContentTitle("Hey!")
                .setSmallIcon(R.drawable.gamelogo_light);

        Intent myIntent = new Intent(context , WellbeingPopUp.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setVibrate(new long[] { 1000, 1000});
        builder.setAutoCancel(true);

        int notiId = 001;

        NotificationManager notifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(notiId, builder.build());

    }

    private int getHour(){

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(System.currentTimeMillis());
        return nowCalendar.get(Calendar.HOUR);
    }


}
