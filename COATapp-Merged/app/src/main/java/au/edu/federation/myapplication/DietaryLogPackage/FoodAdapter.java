package au.edu.federation.myapplication.DietaryLogPackage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import au.edu.federation.myapplication.DietaryLogPackage.FoodXMLReader;
import au.edu.federation.myapplication.R;

/**
 * Created by Madeleine on 19/09/2017.
 */

public class FoodAdapter extends BaseAdapter {

    public final Context mContext;
    private final List<FoodXMLReader.Entry> foods;

    public FoodAdapter(Context context, List<FoodXMLReader.Entry> foods){
        this.mContext = context;
        this.foods = foods;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final FoodXMLReader.Entry food = foods.get(position);

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.food_grid_entry, null);
        }

        final ImageView foodImageView = (ImageView)convertView.findViewById(R.id.imageViewFood);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textViewFoodName);
        final TextView servingTextView = (TextView)convertView.findViewById(R.id.textViewServingSize);

        Log.d("Image", "Setting Image for " + food.name + ". Resource " + food.drawableID);

        foodImageView.setImageDrawable(ContextCompat.getDrawable(mContext, mContext.getResources().getIdentifier(food.drawableID, "drawable", mContext.getPackageName())));
        nameTextView.setText(food.name);
        servingTextView.setText(food.serving);
        //foodImageView.setImageBitmap(BitmapFactory.decodeResource(parent.getContext().getResources(), food.drawableID));



        return convertView;
    }
}

