package au.edu.federation.myapplication.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import au.edu.federation.myapplication.R;
import au.edu.federation.myapplication.WellBeingPackage.WellbeingPopUp;

/**
 * Created by Nick on 1/10/2017.
 */

public class CreateQNotifyService extends Service{

    private final int TODAY = 0;
    private final int TOMORROW = 1;
    private final int TEST = 3;
    private final String TAG = "CreateQNotifyService";

    @Override
    public void onCreate() {

        Log.i(TAG, "Created");

        createNotification();
        //Global.setWellbeingAlarm();
        stopSelf();

        /*
        //If all questions have been answered, don't create a notification,
        //just schedule an alarm for tomorrow
        if (Global.questionsAnswered()){
            setAlarm(TOMORROW);
            Log.d(TAG, "Incorrectly Called");
            stopSelf();
        }


        //If it's later than 9pm, forget today's results, and schedule an alarm for tomorrow
        if (getHour() > 21){
            setAlarm(TOMORROW);
            stopSelf();
        }

        createNotification();
        setAlarm(TEST);

        stopSelf(); //Stops service from running again until an alarm calls it
    }

    */
    }

    private void createNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText("Please answer a question")
                .setContentTitle("Hey!")
                .setSmallIcon(R.drawable.gamelogo_light);

        Intent myIntent = new Intent(this , WellbeingPopUp.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setVibrate(new long[] { 1000, 1000});
        builder.setAutoCancel(true);

        int notiId = 001;

        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyManager.notify(notiId, builder.build());

    }
    /*
    private void setAlarm(int alarmDay){

        Intent myIntent = new Intent(this, CreateQNotifyService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();

        switch (alarmDay){
            case TOMORROW:
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                break;
            case TODAY:
                calendar.set(Calendar.HOUR_OF_DAY, getHour() + 1);
                break;
            case TEST:
                Log.d(TAG, "Test");
                calendar.add(Calendar.MINUTE, 1);
                break;
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

    }

    private int getHour(){

        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(System.currentTimeMillis());
        return nowCalendar.get(Calendar.HOUR);
    }

    */

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
