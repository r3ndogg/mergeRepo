package au.edu.federation.myapplication.TreasureHunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import au.edu.federation.myapplication.R;

/**
 * Created by Nick on 5/09/2017.
 */

public class PopUp extends AppCompatActivity {

    private ImageView treasureFound;
    private Button closeButton;
    private TextView foundTextView, coinsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_popup);
        treasureFound = (ImageView) findViewById(R.id.treasureImage);
        foundTextView = (TextView) findViewById(R.id.foundTextView);
        coinsTextView = (TextView) findViewById(R.id.coinsTextView);

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopUp();
            }
        });

        Bundle extra = getIntent().getExtras();

        if (extra == null){
            this.finish();
        }

        String treasureCode = extra.getString("treasureCode");
        Log.i("Popup", treasureCode);

        switch (treasureCode) {
            case "treasure1":
                treasureFound.setImageResource(R.drawable.blue_trans);
                foundTextView.setText("You found the Blue Gem!");
                Log.i("Popup", treasureCode);
                break;
            case "treasure2":
                treasureFound.setImageResource(R.drawable.yellow_trans);
                foundTextView.setText("You found the Yellow Gem!");
                Log.i("Popup", treasureCode);
                break;
            case "treasure3":
                treasureFound.setImageResource(R.drawable.pink_trans);
                foundTextView.setText("You found the Pink Gem!");
                Log.i("Popup", treasureCode);
                break;
            case "treasure4":
                treasureFound.setImageResource(R.drawable.green_trans);
                foundTextView.setText("You found the Green Gem!");
                Log.i("Popup", treasureCode);
                break;
            case "duplicate":
                treasureFound.setImageResource(R.drawable.cross);
                foundTextView.setText("Treasure already found!, Try again");
                Log.i("Popup", treasureCode);
                break;
            default:
                treasureFound.setImageResource(R.drawable.cross);
                foundTextView.setText("That's not a treasure, Try again!");
                coinsTextView.setText("");

        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

       getWindow().setLayout((850),(1400));
    }

    private void closePopUp(){
        this.finish();
    }
}
