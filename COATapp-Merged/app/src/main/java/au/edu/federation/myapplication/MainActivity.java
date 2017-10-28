package au.edu.federation.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import au.edu.federation.myapplication.AvatarPackage.AvatarActivity;
import au.edu.federation.myapplication.DatabasePackage.WellbeingActivity;
import au.edu.federation.myapplication.DietaryLogPackage.DietaryLog;
import au.edu.federation.myapplication.GlobalClasses.Global;
import au.edu.federation.myapplication.PedometerService.PedometerActivity;
import au.edu.federation.myapplication.TreasureHunt.ExerciseHandler;
import au.edu.federation.myapplication.GlobalClasses.SharedPreferencesHandler;
import au.edu.federation.myapplication.WellBeingPackage.WellbeingPopUp;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    public SharedPreferencesHandler prefHand;
    private Toolbar toolbar;
    private ConstraintLayout excerciseLayout, dietaryLayout, treasureLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefHand = new SharedPreferencesHandler();
        int coins = prefHand.getBalance(this);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("COATApp");
        toolbar.setSubtitle("Coins: " + coins);

        prefHand = new SharedPreferencesHandler();

        excerciseLayout = (ConstraintLayout)findViewById(R.id.excercise_layout);
        dietaryLayout = (ConstraintLayout)findViewById(R.id.dietary_layout);
        treasureLayout = (ConstraintLayout)findViewById(R.id.treasure_layout);

        treasureLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ExerciseHandler.class));
            }
        });

        dietaryLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DietaryLog.class));
            }
        });

        excerciseLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PedometerActivity.class));
            }
        });

        Log.d(TAG, Global.questionsAnswered().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_avatar_settings){
            startActivity(new Intent(getApplicationContext(), AvatarActivity.class));
        }

        if(item.getItemId() == R.id.action_wellbeing){
            startActivity(new Intent(getApplicationContext(), WellbeingActivity.class));
        }

        if(item.getItemId() == R.id.action_setting){
            startActivity(new Intent(getApplicationContext(), Settings.class));
        }

        if(item.getItemId() == R.id.action_testing_bench){
            startActivity(new Intent(getApplicationContext(), TestingBench.class));
        }

        if(item.getItemId() == R.id.action_wellbeing_popup){
            startActivity(new Intent(getApplicationContext(), WellbeingPopUp.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setToolbar(){

        int coins;

        coins = prefHand.getBalance(this);
        toolbar.setSubtitle("Coins: " + Integer.toString(coins));
    }

    @Override
    public void onResume(){
        super.onResume();
        setToolbar();

    }
}
