package au.edu.federation.myapplication.DietaryLogPackage;

import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.edu.federation.myapplication.DietaryLogPackage.FoodAdapter;
import au.edu.federation.myapplication.DietaryLogPackage.FoodXMLReader;
import au.edu.federation.myapplication.R;

//// TODO: 10/10/2017 REFACTOR:

public class AddDietaryItem extends AppCompatActivity {

    private LayerDrawable ld;
    private LinearLayout ll;
    private ImageView iv;
    private GridView gv;
    private RatingBar rb;

    private FoodAdapter foodAdapter;

    FoodXMLReader foodXMLReader;
    private List<FoodXMLReader.Entry> foodsList = new ArrayList<FoodXMLReader.Entry>();

    //TODO: Refactor meal into own class.
    public List<FoodXMLReader.Entry> meal = new ArrayList<FoodXMLReader.Entry>();
    int kjMealTotal = 0;
    int maxKj = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dietary_item);

        //Retrieve layer list drawable and add it to linearlayout
        //ld = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.layer_list_plate);
        ll = (LinearLayout) findViewById(R.id.llplate);
        //rb = (RatingBar) findViewById(R.id.healthRating);
        iv = new ImageView(this);
        iv.setImageDrawable(ld);
        ll.addView(iv);

        gv = (GridView) findViewById(R.id.gridview);

        populateFoodView();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                FoodXMLReader.Entry food = foodsList.get(position);

                meal.add(food);
                kjMealTotal += Integer.parseInt(food.kj);
                //rb.setRating(maxKj / kjMealTotal);
                Log.d("Rating", "Total meal: " + kjMealTotal);
                Log.d("Rating", "Food: " + Integer.parseInt(food.kj));

                foodAdapter.notifyDataSetChanged();
            }
        });
    }


    private void populateFoodView() {
        foodXMLReader = new FoodXMLReader();
        try {
            foodsList = foodXMLReader.parse(this.getResources().openRawResource(R.raw.foods));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < foodsList.size(); i++){
            FoodXMLReader.Entry entry = foodsList.get(i);
            Log.d("Image", "getView: Getting Image: " + entry.drawableID);
            Log.d("Image", "getView: Getting Image for: " + entry.name);
            //entry.drawableID = getResources().getIdentifier(entry.drawableName, "drawable", "au.edu.federation.myapplication");
            Log.d("Image", "getView: Image resource:");
        }

        foodAdapter = new FoodAdapter(this, foodsList);
        gv.setAdapter(foodAdapter);

//        for (FoodXMLReader.Entry entry : foodsList) {
//            String TAG = "XML Readout";
//            Log.d(TAG, "ITEM");
//            Log.d(TAG, "Name: " + entry.name);
//            Log.d(TAG, "Serving: " + entry.serving);
//            Log.d(TAG, "Kj"  + entry.kj);
//        }

    }

}
