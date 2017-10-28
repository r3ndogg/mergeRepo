package au.edu.federation.myapplication;

/**
 * Created by Victor on 25/04/2017.
 */

public class Diet {

    // Wellbeing properties
    private int userID;
    private int healthRating;
    private int mealType;
    private String picture;
    private int liquids;
    private String mealComments;
    private String dateTime;

    public Diet(int userID, int healthRating, int mealType, String picture, int liquids, String mealComments, String dateTime)
    {
        this.userID = userID;
        this.healthRating = healthRating;
        this.mealType = mealType;
        this.picture = picture;
        this.liquids = liquids;
        this.mealComments = mealComments;
        this.dateTime = dateTime;
    }

    public int getUserID() { return userID; }
    public int getHealthRating() { return healthRating; }
    public int getMealType() { return mealType; }
    public String getPicture() { return picture; }
    public int getLiquids() { return liquids; }
    public String getMealComments() { return mealComments; }
    public String getDateTime() { return dateTime; }
}
