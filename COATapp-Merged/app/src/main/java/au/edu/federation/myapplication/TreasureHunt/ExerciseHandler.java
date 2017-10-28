
package au.edu.federation.myapplication.TreasureHunt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import au.edu.federation.myapplication.GlobalClasses.GlobalOld;
import au.edu.federation.myapplication.GlobalClasses.SharedPreferencesHandler;
import au.edu.federation.myapplication.R;

/**
 * Created by Nick on 21/08/2017.
 */

public class ExerciseHandler extends AppCompatActivity {


    private static final String TAG = "Exercise Handler";

    private Button startQrIntent, clearTreasureArray;
    private TextView infoTextView;
    private ImageView tickOrCross1, tickOrCross2, tickOrCross3, tickOrCross4;
    private GlobalOld globalOld;
    private SharedPreferencesHandler prefHand;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_handler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Treasure Hunt");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitle("Coins");
        toolbar.setSubtitleTextColor(0xFFFFFFFF);

        globalOld = globalOld.getInstance();
        prefHand = new SharedPreferencesHandler();

        setToolbar();

        infoTextView = (TextView) findViewById(R.id.infoTextView);
        tickOrCross1 = (ImageView) findViewById(R.id.tickOrCross1);
        tickOrCross2 = (ImageView) findViewById(R.id.tickOrCross2);
        tickOrCross3 = (ImageView) findViewById(R.id.tickOrCross3);
        tickOrCross4 = (ImageView) findViewById(R.id.tickOrCross4);

        setInfoTextView();
        setGameBoard();

        startQrIntent = (Button) findViewById(R.id.startQrIntent);
        startQrIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Checks if the camera permissions has been granted, and opens the camera if it has
                if ((ContextCompat.checkSelfPermission(ExerciseHandler.this,
                        Manifest.permission.CAMERA)) == PackageManager.PERMISSION_GRANTED){

                    IntentIntegrator integrator = new IntentIntegrator(ExerciseHandler.this);
                    integrator.setPrompt("");
                    integrator.initiateScan();
                }
                else{

                    ActivityCompat.requestPermissions(ExerciseHandler.this,
                            new String[]{Manifest.permission.CAMERA},
                            1);

                }



            }
        });

        clearTreasureArray = (Button) findViewById(R.id.clearArray);
        clearTreasureArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalOld.treasureList.clear();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    IntentIntegrator integrator = new IntentIntegrator(ExerciseHandler.this);
                    integrator.setPrompt("");
                    integrator.initiateScan();

                } else {

                }
                return;
            }
        }
    }

    //Sets the info text with the amount of treasures collected that day.
    private void setInfoTextView(){

        String message;

        if (globalOld.treasureList.size() == 4){
            message = "You've found all treasures for today. Well Done";
        }
        else if (globalOld.treasureList.size() == 0){
            message = "You haven't found any treasures today";
        }
        else{
            message = "You've found " + globalOld.treasureList.size()
                    + " out of 4 treasures";
        }

        infoTextView.setText(message);
    }

    //Sets the toolbar with correct values
    private void setToolbar(){

        int coins;

        coins = prefHand.getBalance(this);
        toolbar.setSubtitle("Coins: " + Integer.toString(coins));
    }

    private void setGameBoard(){

        if (globalOld.treasureList.contains("treasure1")){
            tickOrCross1.setImageResource(R.drawable.tick);
        }

        if (globalOld.treasureList.contains("treasure2")){
            tickOrCross2.setImageResource(R.drawable.tick);
        }

        if (globalOld.treasureList.contains("treasure3")){
            tickOrCross3.setImageResource(R.drawable.tick);
        }

        if (globalOld.treasureList.contains("treasure4")){
            tickOrCross4.setImageResource(R.drawable.tick);
        }

    }

    // Get the results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result.getContents() != null){

            String results = result.getContents();

            if (!(globalOld.treasureList.contains(results))){
                if ((results.equals("treasure1") ||
                                     results.equals("treasure2") ||
                                     results.equals("treasure3") ||
                                     results.equals("treasure4")
                                     )){
                                            globalOld.treasureList.add(results);
                                        }
                prefHand.incrementBalance(this, 5);
                setInfoTextView();
                setToolbar();
                setGameBoard();

                Intent i = new Intent(ExerciseHandler.this, PopUp.class);
                i.putExtra("treasureCode", results);
                startActivity(i);

            }
            else{
                Intent i = new Intent(ExerciseHandler.this, PopUp.class);
                i.putExtra("treasureCode", "duplicate");
                startActivity(i);
            }
        }
    }
}
