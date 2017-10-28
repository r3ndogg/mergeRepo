package au.edu.federation.myapplication;

/**
 * Created by Victor on 25/04/2017.
 */

public class Wellbeing {

    // Wellbeing properties
    private int userID;
    private int question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private String date;

    public Wellbeing(int userID, int question1, int question2, int question3, int question4, int question5, String date)
    {
        this.userID = userID;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.date = date;
    }

    //public void setUserID(int userID) { this.userID = userID; }
    //public void setSleep(int sleep) { this.sleep = sleep; }
    //public void setMood(int mood) { this.mood = mood; }
    //public void setEnergy(int energy) { this.energy = energy; }

    public int getUserID() { return userID; }
    public int getQuestion1() { return question1; }
    public int getQuestion2() { return question2; }
    public int getQuestion3() { return question3; }
    public int getQuestion4() { return question4; }
    public int getQuestion5() { return question5; }
    public String getDate() { return date; }
}
