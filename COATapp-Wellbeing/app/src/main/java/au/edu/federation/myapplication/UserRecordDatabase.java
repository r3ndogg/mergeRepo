package au.edu.federation.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 15/04/2017.
 */

public class UserRecordDatabase
{
    private final static String TAG = "UserRecordDatabase";
    private SQLiteDatabase database;
    private UserRecordMySQLiteHelper dbHelper;

    private static final String[] userColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_USER_NAME,
            UserRecordMySQLiteHelper.COLUMN_ACHIEVEMENT_POINTS,
            UserRecordMySQLiteHelper.COLUMN_GENDER};

    private static final String[] exerciseColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_DISTANCE,
            UserRecordMySQLiteHelper.COLUMN_STEPS,
            UserRecordMySQLiteHelper.COLUMN_DATE};

    private static final String[] dietColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_HEALTH_RATING,
            UserRecordMySQLiteHelper.COLUMN_MEAL_TYPE,
            UserRecordMySQLiteHelper.COLUMN_PICTURE,
            UserRecordMySQLiteHelper.COLUMN_LIQUIDS,
            UserRecordMySQLiteHelper.COLUMN_MEAL_COMMENTS,
            UserRecordMySQLiteHelper.COLUMN_DATE_TIME};

    private static final String[] foodColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_FOOD_NAME,
            UserRecordMySQLiteHelper.COLUMN_QUANTITY,
            UserRecordMySQLiteHelper.COLUMN_DAILY_INTAKE_PERC,
            UserRecordMySQLiteHelper.COLUMN_DATE_TIME};

    private static final String[] wellbeingColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_QUESTION_1,
            UserRecordMySQLiteHelper.COLUMN_QUESTION_2,
            UserRecordMySQLiteHelper.COLUMN_QUESTION_3,
            UserRecordMySQLiteHelper.COLUMN_QUESTION_4,
            UserRecordMySQLiteHelper.COLUMN_QUESTION_5,
            UserRecordMySQLiteHelper.COLUMN_DATE};

    private static final String[] wellbeingCommentsColumns = {
            UserRecordMySQLiteHelper.COLUMN_USER_ID,
            UserRecordMySQLiteHelper.COLUMN_WELLBEING_COMMENTS,
            UserRecordMySQLiteHelper.COLUMN_DATE_TIME};

    public UserRecordDatabase(Context context)
    {

        dbHelper = new UserRecordMySQLiteHelper(context);
    }

    // Open database for reading and writing
    public void open()
    {
        try
        {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLException sqle)
        {
            Log.e(TAG, "Could not open database! " + sqle.getMessage() );
        }
    }

    // Close the database
    public void close() { database.close(); }


////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of User Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Delete all user rows
    public Boolean deleteAllUsers()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_USER, null, null);
            Log.d(TAG, "deleted all users");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllUsers", "Error deleting users in user table!" + sqle.getMessage());
            return false;
        }
    }

    // Extract User object and return them
    private User cursorToUser(Cursor cursor)
    {
        try
        {
            User a = new User(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3)
            );
            return a;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    // Return the current User
    public User retrieveUserRow()
    {

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_USER, userColumns, null, null, null, null, null);
        cursor.moveToFirst();
        User user = cursorToUser(cursor);
        cursor.close();

        return user;
    }
    // Insert row into user table of database
    public boolean insertUserRow(int userID, String name, int achievementPoints, String gender)
    {

        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_USER_NAME, name);
        values.put(UserRecordMySQLiteHelper.COLUMN_ACHIEVEMENT_POINTS, achievementPoints);
        values.put(UserRecordMySQLiteHelper.COLUMN_GENDER, gender);

        try {
            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_USER, null, values);
            Log.d(TAG, "Inserted user " + insertId + " into the database!");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("insertUserRow", "Error inserting row into user table!" + sqle.getMessage());
            return false;
        }
    }

    // Update Achivement Points column in User table of database
    public boolean updateUserTableAchievementPoints(int achivementPoints)
    {

        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_ACHIEVEMENT_POINTS, achivementPoints + retrieveUserRow().getAchievementPoints());

        try {

            long insertId = database.update(UserRecordMySQLiteHelper.TABLE_USER, values, null, null);
            Log.d(TAG, "Achivement Points in User table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateAchivementPoints", "Error updating row in User table!" + sqle.getMessage());
            return false;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Wellbeing Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Insert row into wellbeing table of database
    public boolean insertWellbeingRow(int userID, String date)
    {

        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_1, -1);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_2, -1);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_3, -1);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_4, -1);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_5, -1);
        values.put(UserRecordMySQLiteHelper.COLUMN_DATE, date);

        try {
            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_WELLBEING, null, values);
            Log.d(TAG, "Inserted user " + insertId + " into the database!");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("insertWellbeingRow", "Error inserting row into wellbeing table!" + sqle.getMessage());
            return false;
        }
    }

    // Update column in wellbeing table of database
    public boolean updateWellbeingTableColumn(int feedback, String date, String column)
    {

        switch (column) {
            case UserRecordMySQLiteHelper.COLUMN_QUESTION_1: {
                ContentValues values = new ContentValues();
                String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
                String[] whereArgs = { date };

                values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_1, feedback);

                try {

                    long insertId = database.update(UserRecordMySQLiteHelper.TABLE_WELLBEING, values, whereClause, whereArgs);
                    Log.d(TAG, "Sleep column in Wellbeing table updated!");
                    return true;

                }catch(SQLException sqle){
                    // catch exceptions
                    Log.e("updateSleepColumn", "Error updating row in wellbeing table!" + sqle.getMessage());
                    return false;
                }
            }

            case UserRecordMySQLiteHelper.COLUMN_QUESTION_2: {
                ContentValues values = new ContentValues();
                String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
                String[] whereArgs = { date };

                values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_2, feedback);

                try {

                long insertId = database.update(UserRecordMySQLiteHelper.TABLE_WELLBEING, values, whereClause, whereArgs);
                Log.d(TAG, "Wellbeing table updated!");
                    return true;

                }catch(SQLException sqle){
                    // catch exceptions
                    Log.e("updateMoodColumn", "Error updating row in wellbeing table!" + sqle.getMessage());
                    return false;
                }
            }

            case UserRecordMySQLiteHelper.COLUMN_QUESTION_3: {
                ContentValues values = new ContentValues();
                String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
                String[] whereArgs = { date };

                values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_3, feedback);

                try {

                    long insertId = database.update(UserRecordMySQLiteHelper.TABLE_WELLBEING, values, whereClause, whereArgs);
                    Log.d(TAG, "Wellbeing table updated!");
                    return true;

                }catch(SQLException sqle){
                    // catch exceptions
                    Log.e("updateEnergyColumn", "Error updating row in wellbeing table!" + sqle.getMessage());
                    return false;
                }
            }

            case UserRecordMySQLiteHelper.COLUMN_QUESTION_4: {
                ContentValues values = new ContentValues();
                String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
                String[] whereArgs = { date };

                values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_4, feedback);

                try {

                    long insertId = database.update(UserRecordMySQLiteHelper.TABLE_WELLBEING, values, whereClause, whereArgs);
                    Log.d(TAG, "Wellbeing table updated!");
                    return true;

                }catch(SQLException sqle){
                    // catch exceptions
                    Log.e("updateEnergyColumn", "Error updating row in wellbeing table!" + sqle.getMessage());
                    return false;
                }
            }

            case UserRecordMySQLiteHelper.COLUMN_QUESTION_5: {
                ContentValues values = new ContentValues();
                String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
                String[] whereArgs = { date };

                values.put(UserRecordMySQLiteHelper.COLUMN_QUESTION_5, feedback);

                try {

                    long insertId = database.update(UserRecordMySQLiteHelper.TABLE_WELLBEING, values, whereClause, whereArgs);
                    Log.d(TAG, "Wellbeing table updated!");
                    return true;

                }catch(SQLException sqle){
                    // catch exceptions
                    Log.e("updateEnergyColumn", "Error updating row in wellbeing table!" + sqle.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    // Delete all Wellbeing rows
    public boolean deleteAllWellbeing()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_WELLBEING, null, null);
            Log.d(TAG, "deleted all Wellbeing rows");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting Wellbeing rows in Wellbeing table!" + sqle.getMessage());
            return false;
        }
    }

    // Delete single Wellbeing row indicated by date parameter
    public boolean deleteWellbeingRow(String date)
    {
        try {
            String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
            String[] whereArgs = { date };

            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_WELLBEING, whereClause, whereArgs);
            Log.d(TAG, "deleted Wellbeing row for " + date);
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting Wellbeing row in Wellbeing table! date:"+ date + sqle.getMessage());
            return false;
        }
    }

    // Extract row from Wellbeing table in an object and return it
    private Wellbeing cursorToWellbeing(Cursor cursor)
    {
        Wellbeing a = new Wellbeing(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                cursor.getString(6)
        );
        return a;
    }


    // Return a wellbeing row for the date in the date variable
    public Wellbeing retrieveWellbeingRow(String date)
    {
        String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
        String[] whereArgs = { date };

        Wellbeing wellbeing = null;

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_WELLBEING, null, whereClause, whereArgs, null, null, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            wellbeing = cursorToWellbeing(cursor);
        }
        cursor.close();

        return wellbeing;
    }

    // Return a List of all wellbeing rows
    public List<Wellbeing> retrieveWellbeingRows()
    {
        List<Wellbeing> wellbeingList = new ArrayList<Wellbeing>();

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_WELLBEING, wellbeingColumns, null, null, null, null, null);
        cursor.moveToFirst();


        while ( !cursor.isAfterLast() )
        {
            Wellbeing wellbeing = cursorToWellbeing(cursor);
            wellbeingList.add(wellbeing);
            cursor.moveToNext();
        }

        cursor.close();

        return wellbeingList;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Wellbeing Comments Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////



    // Insert feedback into wellbeing comments table of database
    public boolean insertWellbeingCommentsTableRow(int userID, String comments, String dateTime)
    {
        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_WELLBEING_COMMENTS, comments);
        values.put(UserRecordMySQLiteHelper.COLUMN_DATE_TIME, dateTime);

        try {

            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_WELLBEING_COMMENTS, null, values);
            Log.d(TAG, "Wellbeing Comments table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateFeedbackColumn", "Error updating row in Wellbeing Comments table!" + sqle.getMessage());
            return false;
        }
    }

    // Return a List of all wellbeing ccmment rows
    public List<WellbeingComment> retrieveWellbeingCommentsRows()
    {
        List<WellbeingComment> wellbeingCommentsList = new ArrayList<WellbeingComment>();

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_WELLBEING_COMMENTS, wellbeingCommentsColumns, null, null, null, null, null);
        cursor.moveToFirst();


        while ( !cursor.isAfterLast() )
        {
            WellbeingComment wellbeingComment = cursorToWellbeingComments(cursor);
            wellbeingCommentsList.add(wellbeingComment);
            cursor.moveToNext();
        }

        cursor.close();

        return wellbeingCommentsList;
    }

    // Delete all Wellbeing comment rows
    public boolean deleteAllWellbeingComments()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_WELLBEING_COMMENTS, null, null);
            Log.d(TAG, "deleted all Wellbeing Comments rows");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting Wellbeing rows in Wellbeing Comments table!" + sqle.getMessage());
            return false;
        }
    }

    // Delete single Wellbeing Comment row indicated by date parameter
    public boolean deleteWellbeingCommentRow(String dateTime)
    {
        try {
            String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE_TIME + " = ?";
            String[] whereArgs = { dateTime };

            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_WELLBEING_COMMENTS, whereClause, whereArgs);
            Log.d(TAG, "deleted Wellbeing Comment row for " + dateTime);
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting Wellbeing row in Wellbeing table! date:"+ dateTime + sqle.getMessage());
            return false;
        }
    }

    // Extract row from Wellbeing table in an object and return it
    private WellbeingComment cursorToWellbeingComments(Cursor cursor)
    {
        WellbeingComment a = new WellbeingComment(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );
        return a;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Diet Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Insert data into Diet table of database

    public boolean insertDietTableRow(int userID, int healthRating, int mealType, String picture,
                                      int liquids, String mealComments, String dateTime)
    {
        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_HEALTH_RATING, healthRating);
        values.put(UserRecordMySQLiteHelper.COLUMN_MEAL_TYPE, mealType);
        values.put(UserRecordMySQLiteHelper.COLUMN_PICTURE, picture);
        values.put(UserRecordMySQLiteHelper.COLUMN_LIQUIDS, liquids);
        values.put(UserRecordMySQLiteHelper.COLUMN_MEAL_COMMENTS, mealComments);
        values.put(UserRecordMySQLiteHelper.COLUMN_DATE_TIME, dateTime);

        try {

            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_DIET, null, values);
            Log.d(TAG, "Diet table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateFeedbackColumn", "Error updating row in Diet table!" + sqle.getMessage());
            return false;
        }
    }

    // Return a List of all Diet rows
    public List<Diet> retrieveDietRows()
    {
        List<Diet> DietList = new ArrayList<Diet>();

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_DIET, dietColumns, null, null, null, null, null);
        cursor.moveToFirst();


        while ( !cursor.isAfterLast() )
        {
            Diet diet = cursorToDiet(cursor);
            DietList.add(diet);
            cursor.moveToNext();
        }

        cursor.close();

        return DietList;
    }

    // Delete all Diet rows
    public boolean deleteAllDiet()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_DIET, null, null);
            Log.d(TAG, "deleted all Diet rows");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting rows in Diet table!" + sqle.getMessage());
            return false;
        }
    }

    // Delete single Diet row indicated by date parameter
    public boolean deleteDietRow(String dateTime)
    {
        try {
            String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE_TIME + " = ?";
            String[] whereArgs = { dateTime };

            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_DIET, whereClause, whereArgs);
            Log.d(TAG, "deleted Diet row for " + dateTime);
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteDietRow", "Error deleting Diet row in Diet table! date:"+ dateTime + sqle.getMessage());
            return false;
        }
    }

    // Extract row from Diet table in an object and return it
    private Diet cursorToDiet(Cursor cursor)
    {
        Diet a = new Diet(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                cursor.getString(5),
                cursor.getString(6)
        );
        return a;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Food Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Insert data into Food table of database

    public boolean insertFoodTableRow(int userID, String foodName, int quantity, int dailyIntakePerc, String dateTime)
    {
        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_FOOD_NAME, foodName);
        values.put(UserRecordMySQLiteHelper.COLUMN_QUANTITY, quantity);
        values.put(UserRecordMySQLiteHelper.COLUMN_DAILY_INTAKE_PERC, dailyIntakePerc);
        values.put(UserRecordMySQLiteHelper.COLUMN_DATE_TIME, dateTime);

        try {

            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_FOOD, null, values);
            Log.d(TAG, "Food table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateFeedbackColumn", "Error updating row in Food table!" + sqle.getMessage());
            return false;
        }
    }

    // Return a List of all Food rows
    public List<Food> retrieveFoodRows()
    {
        List<Food> FoodList = new ArrayList<Food>();

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_FOOD, foodColumns, null, null, null, null, null);
        cursor.moveToFirst();


        while ( !cursor.isAfterLast() )
        {
            Food food = cursorToFood(cursor);
            FoodList.add(food);
            cursor.moveToNext();
        }

        cursor.close();

        return FoodList;
    }

    // Delete all Food rows
    public boolean deleteAllFood()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_FOOD, null, null);
            Log.d(TAG, "deleted all Food rows");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting rows in Food table!" + sqle.getMessage());
            return false;
        }
    }

    // Delete single Food row indicated by date parameter
    public boolean deleteFoodRow(String dateTime)
    {
        try {
            String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE_TIME + " = ?";
            String[] whereArgs = { dateTime };

            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_FOOD, whereClause, whereArgs);
            Log.d(TAG, "deleted Food row for " + dateTime);
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteFoodRow", "Error deleting Food row in Food table! date:"+ dateTime + sqle.getMessage());
            return false;
        }
    }

    // Extract row from Food table in an object and return it
    private Food cursorToFood(Cursor cursor)
    {
        Food a = new Food(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4)
        );
        return a;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////
////Start of Exercise Table Section
////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    // Insert data into Exercise table of database

    public boolean insertExerciseTableRow(int userID, String date)
    {
        ContentValues values = new ContentValues();

        values.put(UserRecordMySQLiteHelper.COLUMN_USER_ID, userID);
        values.put(UserRecordMySQLiteHelper.COLUMN_DISTANCE, 0);
        values.put(UserRecordMySQLiteHelper.COLUMN_STEPS, 0);
        values.put(UserRecordMySQLiteHelper.COLUMN_DATE, date);

        try {

            long insertId = database.insert(UserRecordMySQLiteHelper.TABLE_EXERCISE, null, values);
            Log.d(TAG, "Exercise table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateFeedbackColumn", "Error updating row in Exercise table!" + sqle.getMessage());
            return false;
        }
    }

    // Update column in Exercise table of database
    public boolean updateExerciseTable(int distance, int steps, String date)
    {

        ContentValues values = new ContentValues();
        String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
        String[] whereArgs = { date };

        values.put(UserRecordMySQLiteHelper.COLUMN_DISTANCE, distance);
        values.put(UserRecordMySQLiteHelper.COLUMN_STEPS, steps);

        try {

            long insertId = database.update(UserRecordMySQLiteHelper.TABLE_EXERCISE, values, whereClause, whereArgs);
            Log.d(TAG, "Row in Exercise table updated!");
            return true;

        }catch(SQLException sqle){
            // catch exceptions
            Log.e("updateExerciseRow", "Error updating row in Exercise table!" + sqle.getMessage());
            return false;
        }
    }

    // Return a List of all Exercise rows
    public List<Exercise> retrieveExerciseRows()
    {
        List<Exercise> ExerciseList = new ArrayList<Exercise>();

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_EXERCISE, exerciseColumns, null, null, null, null, null);
        cursor.moveToFirst();


        while ( !cursor.isAfterLast() )
        {
            Exercise exercise = cursorToExercise(cursor);
            ExerciseList.add(exercise);
            cursor.moveToNext();
        }

        cursor.close();

        return ExerciseList;
    }

    // Delete all Exercise rows
    public boolean deleteAllExercise()
    {
        try {
            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_EXERCISE, null, null);
            Log.d(TAG, "deleted all Exercise rows");
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteAllWellbeing", "Error deleting rows in Exercise table!" + sqle.getMessage());
            return false;
        }
    }

    // Delete single Exercise row indicated by date parameter
    public boolean deleteExerciseRow(String date)
    {
        try {
            String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
            String[] whereArgs = { date };

            //database.delete(UserRecordMySQLiteHelper.TABLE_USER, UserRecordMySQLiteHelper.COLUMN_USER_ID + " = " + id, null);
            database.delete(UserRecordMySQLiteHelper.TABLE_EXERCISE, whereClause, whereArgs);
            Log.d(TAG, "deleted Exercise row for " + date);
            return true;
        }catch(SQLException sqle){
            // catch exceptions
            Log.e("deleteExercise", "Error deleting Exercise row in Exercise table! date:"+ date + sqle.getMessage());
            return false;
        }
    }

    // Extract row from Exercise table in an object and return it
    private Exercise cursorToExercise(Cursor cursor)
    {
        Exercise a = new Exercise(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3)
        );
        return a;
    }

    // Return a Exercise row for the date in the date variable
    public Exercise retrieveExerciseRow(String date)
    {
        String whereClause = UserRecordMySQLiteHelper.COLUMN_DATE + " = ?";
        String[] whereArgs = { date };

        Exercise exercise = null;

        Cursor cursor = database.query(UserRecordMySQLiteHelper.TABLE_EXERCISE, null, whereClause, whereArgs, null, null, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            exercise = cursorToExercise(cursor);
        }
        cursor.close();

        return exercise;
    }
}
