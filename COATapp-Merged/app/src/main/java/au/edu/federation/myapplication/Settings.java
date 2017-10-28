package au.edu.federation.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import au.edu.federation.myapplication.AvatarPackage.AvatarActivity;
import au.edu.federation.myapplication.DatabasePackage.WellbeingActivity;
import au.edu.federation.myapplication.GlobalClasses.SharedPreferencesHandler;

public class Settings extends AppCompatActivity {

    public SharedPreferencesHandler prefHand;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefHand = new SharedPreferencesHandler();
        int coins = prefHand.getBalance(this);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("COATApp");
        toolbar.setSubtitle("Coins: " + coins);
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
