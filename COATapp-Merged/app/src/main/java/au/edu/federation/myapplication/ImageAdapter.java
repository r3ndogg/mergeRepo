package au.edu.federation.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.myapplication.DietaryLogPackage.DietaryLogItem;
import au.edu.federation.myapplication.GlobalClasses.GlobalOld;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    GlobalOld globalOld;

    private List<Bitmap> mThumbIds;

    public ImageAdapter(Context c) {
        mContext = c;

        globalOld = globalOld.getInstance();

        mThumbIds = new ArrayList<Bitmap>();

        for (DietaryLogItem item: globalOld.dietaryLogList) {
            mThumbIds.add(item.getFoodImage());
        }
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(320, 320));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mThumbIds.get(position));
        return imageView;
    }
}
