package au.edu.federation.myapplication.DietaryLogPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import au.edu.federation.myapplication.R;

public class DietaryLog extends AppCompatActivity {

    //Global global;
//    Button approveButton = (Button)findViewById(R.id.btnApprove);
    //Redundant (scope change)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietary_log);

       // global = (Global)getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();

//
//        if (global.adultSignin == false){
//            approveButton.setVisibility(View.GONE);
//        }
    }


    public void LaunchAddDietaryLog(View v){
        Intent launchAddDietaryLog = new Intent(this, AddDietaryLog.class);
        startActivity(launchAddDietaryLog);
    }

    public void LaunchViewDietaryLog(View v){
        Intent launchViewDietaryLog = new Intent(this, ViewDietaryLog.class);
        startActivity(launchViewDietaryLog);
    }


}
