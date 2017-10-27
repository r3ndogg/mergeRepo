package au.edu.federation.myapplication;

/**
 * Created by Victor on 25/04/2017.
 */

public class Wellbeing {

    // Wellbeing properties
    private int userID;
    private int sleep;
    private int mood;
    private int energy;
    private String date;

    public Wellbeing(int userID, int sleep, int mood, int energy, String date)
    {
        this.userID = userID;
        this.sleep = sleep;
        this.mood = mood;
        this.energy = energy;
        this.date = date;
    }

    //public void setUserID(int userID) { this.userID = userID; }
    //public void setSleep(int sleep) { this.sleep = sleep; }
    //public void setMood(int mood) { this.mood = mood; }
    //public void setEnergy(int energy) { this.energy = energy; }

    public int getUserID() { return userID; }
    public int getSleep() { return sleep; }
    public int getMood() { return mood; }
    public int getEnergy() { return energy; }
    public String getDate() { return date; }
}
