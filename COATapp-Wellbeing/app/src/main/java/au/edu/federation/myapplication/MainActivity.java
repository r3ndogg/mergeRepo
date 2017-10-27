package au.edu.federation.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import au.edu.federation.myapplication.DietaryLogPackage.DietaryLog;
import au.edu.federation.myapplication.ExercisePackage.ExerciseHandler;

public class MainActivity extends AppCompatActivity {

    private Button mapButton, avatarButton;
    public SharedPreferencesHandler prefHand;
    private Toolbar toolbar;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefHand = new SharedPreferencesHandler();
        int coins = prefHand.getBalance(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("COATApp");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitle("Coins: " + coins);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);

        prefHand = new SharedPreferencesHandler();

        mapButton = (Button) findViewById(R.id.activity);
        mapButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ExerciseHandler.class));
            }
        });

        avatarButton = (Button) findViewById(R.id.avatar);
        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AvatarActivity.class));
            }
        });

        context = this;
    }

    public void handleButtonPress(View v) {
        switch (v.getId()) {

            case R.id.wellbeingButton: {
                Intent intent = new Intent(this, WellbeingActivity.class);
                startActivity(intent);
                break; }
        }
    }

    public void LaunchDietaryLog(View v){
        Intent launchDietaryLog = new Intent(this, DietaryLog.class);
        startActivity(launchDietaryLog);
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
