package au.edu.federation.myapplication;

import java.util.Calendar;

/**
 * Created by Victor on 26/04/2017.
 */

public class Time {



    //Calendar c;

    public Time()
    {
        //c = Calendar.getInstance();
    }

    //Uses the format: "YYYY-MM-DD HH:MM:SS:MS"
    public static String getCurrentDateTime()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int milliSecond = c.get(Calendar.MILLISECOND);

        String date = String.format ("%d-%02d-%02d %02d:%02d:%02d:%02d", year, month, day, hour, minute, second, milliSecond);
        return date;
    }

    //Uses the format: "YYYY-MM-DD"
    public static String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = c.get(Calendar.DAY_OF_MONTH);
        //int hour = c.get(Calendar.HOUR_OF_DAY);
        //int minute = c.get(Calendar.MINUTE);
        //int second = c.get(Calendar.SECOND);
        //int milliSecond = c.get(Calendar.MILLISECOND);

        String date = String.format ("%d-%02d-%02d", year, month, day);
        return date;
    }

    //Uses the format: "HH:MM:SS"
    public static String getCurrentTime()
    {
        Calendar c = Calendar.getInstance();
        //int year = c.get(Calendar.YEAR);
        //int month = c.get(Calendar.MONTH) + 1; // Note: zero based!
        //int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int milliSecond = c.get(Calendar.MILLISECOND);

        String date = String.format ("%02d:%02d:%02d:%02d", hour, minute, second, milliSecond);
        return date;
    }

    public static boolean isCurrentDateTime(String date)
    {
        if (date.substring(0, 10).equals(getCurrentDate()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
