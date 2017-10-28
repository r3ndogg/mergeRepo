package au.edu.federation.myapplication;

/**
 * Created by Victor on 23/04/2017.
 */

public class User {

    // User properties
    private int userID;
    private String name;
    private int achievementPoints;
    private String gender;

    public User(int userID, String name, int achievementPoints, String gender)
    {
        this.userID = userID;
        this.name = name;
        this.achievementPoints = achievementPoints;
        this.gender = gender;
    }

    public int getUserID() { return userID; }
    public String getName() { return name; }
    public int getAchievementPoints() { return achievementPoints; }
    public String getGender() { return gender; }
}
