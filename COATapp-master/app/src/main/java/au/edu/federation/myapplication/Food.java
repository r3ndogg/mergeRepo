package au.edu.federation.myapplication;

/**
 * Created by Victor on 23/04/2017.
 */

//Old data structure -Madeleine 6/10/2017

public class Food {

    // User properties
    private int userID;
    private String foodName;
    private int quantity;
    private int dailyIntakePerc;
    private String dateTime;

    public Food(int userID, String foodName, int quantity, int dailyIntakePerc, String dateTime)
    {
        this.userID = userID;
        this.foodName = foodName;
        this.quantity = quantity;
        this.dailyIntakePerc = dailyIntakePerc;
        this.dateTime = dateTime;
    }

    public int getUserID() { return userID; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
    public int getDailyIntakePerc() { return dailyIntakePerc; }
    public String getDateTime() { return dateTime; }
}
