package au.edu.federation.myapplication;

/**
 * Created by Victor on 23/04/2017.
 */

public class User {

    // User properties
    private int userID;
    private String name;
    private int achievementPoints;

    public User(int userID, String name, int achievementPoints)
    {
        this.userID = userID;
        this.name = name;
        this.achievementPoints = achievementPoints;
    }

    public int getUserID() { return userID; }
    public String getName() { return name; }
    public int getAchievementPoints() { return achievementPoints; }
}
