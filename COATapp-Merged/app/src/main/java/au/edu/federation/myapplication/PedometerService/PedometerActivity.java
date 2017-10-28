package au.edu.federation.myapplication.PedometerService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import au.edu.federation.myapplication.AvatarPackage.AvatarActivity;
import au.edu.federation.myapplication.DatabasePackage.WellbeingActivity;
import au.edu.federation.myapplication.GlobalClasses.Global;
import au.edu.federation.myapplication.R;
import au.edu.federation.myapplication.Settings;
import au.edu.federation.myapplication.TestingBench;
import au.edu.federation.myapplication.WellBeingPackage.WellbeingPopUp;

import static au.edu.federation.myapplication.R.id.stepsTaken;

public class PedometerActivity extends AppCompatActivity {

    static boolean active = false;
    private Toolbar toolbar;
    private static TextView stepsTakenField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        stepsTakenField = (TextView) findViewById(stepsTaken);
        stepsTakenField.setText(String.valueOf(Global.getSteps()));

        toolbar = (Toolbar) findViewById(R.id.PedoToolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("COATApp");
        //toolbar.setSubtitle("Coins: " + coins);

    }

    public static void increaseSteps(int steps){
        stepsTakenField.setText(String.valueOf(steps));
    }

    public void loadSteps(){
        stepsTakenField.setText(String.valueOf(Global.getSteps()));
    }

    @Override
    public void onResume(){
        super.onResume();
        loadSteps();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
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
}
