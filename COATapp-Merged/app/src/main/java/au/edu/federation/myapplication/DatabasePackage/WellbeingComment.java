package au.edu.federation.myapplication.DatabasePackage;

/**
 * Created by Victor on 25/04/2017.
 */

public class WellbeingComment {

    // Wellbeing properties
    private int userID;
    private String comment;
    private String dateTime;

    public WellbeingComment(int userID, String comment, String dateTime)
    {
        this.userID = userID;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    //public void setUserID(int userID) { this.userID = userID; }
    //public void setSleep(int sleep) { this.sleep = sleep; }
    //public void setMood(int mood) { this.mood = mood; }
    //public void setEnergy(int energy) { this.energy = energy; }

    public int getUserID() { return userID; }
    public String getComment() { return comment; }
    public String getDate() { return dateTime; }
}