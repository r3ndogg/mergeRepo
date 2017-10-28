package au.edu.federation.myapplication.DatabasePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Victor on 15/04/2017.
 */

public class UserRecordMySQLiteHelper extends SQLiteOpenHelper
{
    private static final String TAG = "UserRecMySQLiteHelper";
    private static final String DATABASE_NAME = "record.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_ACHIEVEMENT_POINTS = "achievementPoints";

    public static final String TABLE_EXERCISE = "exercise";
    public static final String COLUMN_DISTANCE = "distance";
    public static final String COLUMN_STEPS = "steps";

    public static final String TABLE_DIET = "diet";
    public static final String COLUMN_HEALTH_RATING = "healthRating";
    public static final String COLUMN_MEAL_TYPE = "mealType";
    public static final String COLUMN_PICTURE = "picture";
    public static final String COLUMN_MEAL_COMMENTS = "mealComments";

    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_FOOD_NAME = "foodName";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DAILY_INTAKE_PERC = "dailyIntakePerc";

    public static final String TABLE_WELLBEING = "wellbeing";
    public static final String COLUMN_MOOD = "mood";
    public static final String COLUMN_ENERGY = "energy";
    public static final String COLUMN_SLEEP = "sleep";

    public static final String TABLE_WELLBEING_COMMENTS = "wellbeingComments";
    public static final String COLUMN_WELLBEING_COMMENTS = "wellbeingComments";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE_TIME = "dateTime";

    private static final String DATABASE_CREATE_TABLE_USER = "create table "
            + TABLE_USER + "("
            + COLUMN_USER_ID + " integer primary key, "
            + COLUMN_USER_NAME + " text not null, "
            + COLUMN_ACHIEVEMENT_POINTS + " integer not null);";


    private static final String DATABASE_CREATE_TABLE_EXERCISE = "create table "
            + TABLE_EXERCISE + "("
            + COLUMN_USER_ID + " integer not null, "
            + COLUMN_DISTANCE + " integer not null, "
            + COLUMN_STEPS + " integer not null, "
            + COLUMN_DATE + " text not null, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_DATE + ")"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String DATABASE_CREATE_TABLE_DIET = "create table "
            + TABLE_DIET + "("
            + COLUMN_USER_ID + " integer not null, "
            + COLUMN_HEALTH_RATING + " integer not null, "
            + COLUMN_MEAL_TYPE + " integer not null, "
            + COLUMN_PICTURE + " text not null, "
            + COLUMN_MEAL_COMMENTS + " text not null, "
            + COLUMN_DATE_TIME + " text not null, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_DATE_TIME + ")"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String DATABASE_CREATE_TABLE_FOOD = "create table "
            + TABLE_FOOD + "("
            + COLUMN_USER_ID + " integer not null, "
            + COLUMN_FOOD_NAME + " text not null, "
            + COLUMN_QUANTITY + " integer not null, "
            + COLUMN_DAILY_INTAKE_PERC + " integer not null, "
            + COLUMN_DATE_TIME + " text not null, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_FOOD_NAME + ", " + COLUMN_DATE_TIME + ")"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String DATABASE_CREATE_TABLE_WELLBEING = "create table "
            + TABLE_WELLBEING + "("
            + COLUMN_USER_ID + " integer not null, "
            + COLUMN_SLEEP + " integer not null, "
            + COLUMN_MOOD + " integer not null, "
            + COLUMN_ENERGY + " integer not null, "
            + COLUMN_DATE + " text not null, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_DATE + ")"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String DATABASE_CREATE_TABLE_WELLBEING_COMMENTS = "create table "
            + TABLE_WELLBEING_COMMENTS + "("
            + COLUMN_USER_ID + " integer not null, "
            + COLUMN_WELLBEING_COMMENTS + " text not null, "
            + COLUMN_DATE_TIME + " text not null, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_DATE_TIME + ")"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES "+ TABLE_USER + "(" + COLUMN_USER_ID + "));";

    public UserRecordMySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_TABLE_USER);
        Log.d(TAG, "TABLE_USER created");
        db.execSQL(DATABASE_CREATE_TABLE_EXERCISE);
        Log.d(TAG, "TABLE_EXERCISE created");
        db.execSQL(DATABASE_CREATE_TABLE_DIET);
        Log.d(TAG, "TABLE_DIET created");
        db.execSQL(DATABASE_CREATE_TABLE_FOOD);
        Log.d(TAG, "TABLE_FOOD created");
        db.execSQL(DATABASE_CREATE_TABLE_WELLBEING);
        Log.d(TAG, "TABLE_WELLBEING created");
        db.execSQL(DATABASE_CREATE_TABLE_WELLBEING_COMMENTS);
        Log.d(TAG, "TABLE_WELLBEING_COMMENTS created");


        db.execSQL("INSERT INTO " + TABLE_USER + " (" + COLUMN_USER_ID + ", " + COLUMN_USER_NAME + ", " +
                COLUMN_ACHIEVEMENT_POINTS + ")" + " VALUES (9999, 'Someone', 0);");
        //db.execSQL("INSERT INTO " + TABLE_WELLBEING + " (" + COLUMN_USER_ID + ", "
        //        + COLUMN_SLEEP +  ", " + COLUMN_MOOD +  ", " + COLUMN_ENERGY +  ", " + COLUMN_DATE + ")" +
        //        " VALUES (9999, -1, -1, -1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
        Log.d(TAG, "Upgraded database!");
    }
}
