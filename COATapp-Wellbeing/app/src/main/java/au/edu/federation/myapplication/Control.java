package au.edu.federation.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by Victor on 23/04/2017.
 */

public class Control {

    //achievement points awarded for each task
    static final int createWellbeingComment = 5;
    static final int answerWellbeingQuestion = 1;

    public Control()
    {

    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of User Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static User getUser(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        User user = dataSource.retrieveUserRow();

        dataSource.close();

        return user;
    }

    public static boolean setUser(Context context, int userID, String name, int achievementPoints, String gender)
    {
        Boolean success = false;

        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();
        if(dataSource.deleteAllUsers())
        {
            success = dataSource.insertUserRow(userID, name, achievementPoints, gender);
        }
        dataSource.close();

        return success;
    }

    //int achievementPoints paramter will be added to total Achivement Points
    public static boolean updateAchivementPoints(Context context, int achievementPoints)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();
        Boolean success = dataSource.updateUserTableAchievementPoints(achievementPoints);
        dataSource.close();

        return success;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Wellbeing Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<Wellbeing> getWellbeingTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        List<Wellbeing> wellbeingList = dataSource.retrieveWellbeingRows();

        dataSource.close();

        return wellbeingList;
    }

    public static Wellbeing getWellbeingTableCurrentDay(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Wellbeing wellbeing = dataSource.retrieveWellbeingRow(Time.getCurrentDate());

        dataSource.close();
        return wellbeing;
    }


    //Update a single column for the row of the current day
    //
    //Parameters:
    //column - column to be updated
    //feedback - data entered by user via the slider bars
    public static boolean updateWellbeingTableColumn(Context context, int feedback, String column)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        Boolean success = false;

        //produce date before updating to ensure row for current day exists
        String currentDate = Time.getCurrentDate();

        //Update Wellbeing Table
        inspectExerciseTable(context);

        dataSource.open();

                switch (column) {


                    case UserRecordMySQLiteHelper.COLUMN_QUESTION_1: {
                        success = dataSource.updateWellbeingTableColumn(feedback, currentDate, UserRecordMySQLiteHelper.COLUMN_QUESTION_1);
                        Log.d("updateWellbeingTable", "Wellbeing table updated1!");
                        break;
                    }

                    case UserRecordMySQLiteHelper.COLUMN_QUESTION_2: {
                        success = dataSource.updateWellbeingTableColumn(feedback, currentDate, UserRecordMySQLiteHelper.COLUMN_QUESTION_2);
                        Log.d("updateWellbeingTable", "Wellbeing table updated1!");
                        break;
                    }

                    case UserRecordMySQLiteHelper.COLUMN_QUESTION_3: {
                        success = dataSource.updateWellbeingTableColumn(feedback, currentDate, UserRecordMySQLiteHelper.COLUMN_QUESTION_3);
                        Log.d("updateWellbeingTable", "Wellbeing table updated1!");
                        break;
                    }

                    case UserRecordMySQLiteHelper.COLUMN_QUESTION_4: {
                        success = dataSource.updateWellbeingTableColumn(feedback, currentDate, UserRecordMySQLiteHelper.COLUMN_QUESTION_4);
                        Log.d("updateWellbeingTable", "Wellbeing table updated1!");
                        break;
                    }

                    case UserRecordMySQLiteHelper.COLUMN_QUESTION_5: {
                        success = dataSource.updateWellbeingTableColumn(feedback, currentDate, UserRecordMySQLiteHelper.COLUMN_QUESTION_5);
                        Log.d("updateWellbeingTable", "Wellbeing table updated1!");
                        break;
                    }
                }

        dataSource.close();

        if (success)
        {
            updateAchivementPoints(context, answerWellbeingQuestion);
        }

        return success;
    }


    //Check if row exists in Wellbeing table for the current day. If not add it in
    public static void inspectWellbeingTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        if(dataSource.retrieveWellbeingRow(Time.getCurrentDate()) != null)
        {
            Log.d("inspectWellbeingTable", "Wellbeing table does not need updating!"+ Time.getCurrentDate());
        }
        else
        {
            dataSource.insertWellbeingRow(dataSource.retrieveUserRow().getUserID(), Time.getCurrentDate());
            Log.d("inspectWellbeingTable", "Wellbeing table updated!");
        }

        dataSource.close();


    }

    public static Boolean deleteWellbeingRow(Context context, String date)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.deleteWellbeingRow(date);

        dataSource.close();

        return success;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Wellbeing Comments Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Insert feedback into Wellbeing Comments Table
    //
    //Parameters:
    //feedback - text entered by user via EditText View
    public static boolean insertWellbeingCommentsTableRow(Context context, String comments)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.insertWellbeingCommentsTableRow(
                dataSource.retrieveUserRow().getUserID(), comments, Time.getCurrentDateTime());
        Log.d("insertWellbeingComments", "Wellbeing Comments table feedback column updated!");

        dataSource.close();

        if (success)
        {
            updateAchivementPoints(context, createWellbeingComment);
        }

        return success;
    }

    public static List<WellbeingComment> getWellbeingCommentsTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        List<WellbeingComment> wellbeingCommentsList = dataSource.retrieveWellbeingCommentsRows();

        dataSource.close();

        return wellbeingCommentsList;
    }

    public static Boolean deleteWellbeingCommentRow(Context context, String dateTime)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.deleteWellbeingCommentRow(dateTime);

        dataSource.close();

        return success;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Diet Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Insert data into Diet Comments Table
    //

    public static boolean insertDietTableRow(Context context, int healthRating, int mealType, String picture,
                                             int liquids, String mealComments)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();
        Boolean success = dataSource.insertDietTableRow(
                dataSource.retrieveUserRow().getUserID(), healthRating, mealType, picture, liquids,
                    mealComments, Time.getCurrentDateTime());
        Log.d("insertDietTable", "Diet table feedback column updated!");

        dataSource.close();
        return success;
    }

    public static List<Diet> getDietTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        List<Diet> dietList = dataSource.retrieveDietRows();

        dataSource.close();

        return dietList;
    }

    public static Boolean deleteDietRow(Context context, String dateTime)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.deleteDietRow(dateTime);

        dataSource.close();

        return success;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Food Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Insert data into Food Comments Table
    //

    public static boolean insertFoodTableRow(Context context, String foodName, int quantity, int dailyIntakePerc)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();
        Boolean success = dataSource.insertFoodTableRow(
                dataSource.retrieveUserRow().getUserID(), foodName, quantity, dailyIntakePerc, Time.getCurrentDateTime());
        Log.d("insertDietTable", "Diet table feedback column updated!");

        dataSource.close();
        return success;
    }

    public static List<Food> getFoodTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        List<Food> foodList = dataSource.retrieveFoodRows();

        dataSource.close();

        return foodList;
    }

    public static Boolean deleteFoodRow(Context context, String dateTime)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.deleteFoodRow(dateTime);

        dataSource.close();

        return success;
    }



////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Exercise Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    //Check if row exists in Exercise table for the current day. If not add it in
    public static void inspectExerciseTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        if(dataSource.retrieveExerciseRow(Time.getCurrentDate()) != null)
        {
            Log.d("inspectExerciseTable", "Exercise table does not need updating!"+ Time.getCurrentDate());
        }
        else
        {
            dataSource.insertExerciseTableRow(dataSource.retrieveUserRow().getUserID(), Time.getCurrentDate());
            Log.d("inspectExerciseTable", "Exercise table updated!");
        }

        dataSource.close();
    }

    //Update a single column for the row of the current day
    //
    //Parameters:
    //column - column to be updated
    //feedback - data entered by user via the slider bars
    public static boolean updateExerciseTable(Context context, int distance, int steps)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        //produce date before updating to ensure row for current day exists
        String currentDate = Time.getCurrentDate();

        //Update Exercise Table
        inspectExerciseTable(context);

        dataSource.open();

        Boolean success = dataSource.updateExerciseTable(distance, steps, currentDate);
        Log.d("updateExerciseTable", "Exercise table updated!");

        dataSource.close();
        return success;
    }

    public static List<Exercise> getExerciseTable(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        List<Exercise> exerciseList = dataSource.retrieveExerciseRows();

        dataSource.close();

        return exerciseList;
    }


    public static Exercise getExerciseTableCurrentDay(Context context)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Exercise exercise = dataSource.retrieveExerciseRow(Time.getCurrentDate());

        dataSource.close();
        return exercise;
    }

    public static Boolean deleteExerciseRow(Context context, String date)
    {
        UserRecordDatabase dataSource = new UserRecordDatabase(context);

        dataSource.open();

        Boolean success = dataSource.deleteExerciseRow(date);

        dataSource.close();

        return success;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Server Connection section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void login(String username, String password, Context context)
    {
        //Check that connection exits before attempting to communicate
        if(connectionTest())
        {
            //Send login details entered by the user, to the server
            new UploadDataAsyncTask().execute("login", username, password);
        }

    }

    public static void uploadData()
    {
        //Check that connection exits before attempting to communicate
        if(connectionTest())
        {
            //Upload data
            new UploadDataAsyncTask().execute("upload");
        }


    }

    public static boolean connectionTest()
    {
        // Get access to the connectivity service...
        ConnectivityManager conn;
        //conn = (ConnectivityManager) Global.getInstance().appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        conn = (ConnectivityManager) LoginActivity.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // ...and query it for active data network interfaces!
        NetworkInfo activeInfo = conn.getActiveNetworkInfo();

        // Are we connected to an active network interface?
        if (activeInfo != null && activeInfo.isConnected()) {
            // If so, which one?
            boolean wifiConnected   = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected || mobileConnected)
            {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }


}