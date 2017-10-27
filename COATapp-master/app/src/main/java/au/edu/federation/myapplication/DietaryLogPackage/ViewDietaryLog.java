package au.edu.federation.myapplication.DietaryLogPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import au.edu.federation.myapplication.Global;
//import au.edu.federation.myapplication.ImageAdapter;
import au.edu.federation.myapplication.R;

public class ViewDietaryLog extends AppCompatActivity {

    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dietary_log);

        global = global.getInstance();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        //gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //TODO: Bundle up item data to display in a new activity
                Toast.makeText(ViewDietaryLog.this, "Food:" + global.dietaryLogList.get(position).getFoodName() + " Time: " + global.dietaryLogList.get(position).getTimeStamp(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
