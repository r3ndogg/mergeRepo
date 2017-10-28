package au.edu.federation.myapplication.DatabasePackage;

/**
 * Created by Victor on 25/04/2017.
 */

public class Exercise {

    // Wellbeing properties
    private int userID;
    private int distance;
    private int steps;
    private String date;

    public Exercise(int userID, int distance, int steps, String date)
    {
        this.userID = userID;
        this.distance = distance;
        this.steps = steps;
        this.date = date;
    }

    public int getUserID() { return userID; }
    public int getDistance() { return distance; }
    public int getSteps() { return steps; }
    public String getDate() { return date; }
}
