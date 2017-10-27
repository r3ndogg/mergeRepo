package au.edu.federation.myapplication.DietaryLogPackage;

import android.graphics.Bitmap;
import android.net.Uri;

public class DietaryLogItem {

    private Bitmap foodImage;
    private String foodName;
    private String timeStamp;
    private Uri localUri;
    private String category;
    private Boolean isVerified;

    public DietaryLogItem(Bitmap foodImage, String foodName, String timeStamp, Uri localUri, String category) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.timeStamp = timeStamp;
        this.localUri = localUri;
        this.category = category;
        isVerified = false;
    }

    public Bitmap getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(Bitmap foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Uri getLocalUri() {
        return localUri;
    }

    public void setLocalURi(Uri localURi) {
        this.localUri = localURi;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
