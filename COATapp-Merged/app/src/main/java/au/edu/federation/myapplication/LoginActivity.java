package au.edu.federation.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView viewWrong;

    public static Context context;

    //REMOVE FROM FINAL APPLICATION
    boolean skipLogin = true; //test value to stop login menu from appearing and making testing a pain in the ass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Global.getInstance().appContext = getApplicationContext();
        Log.d("appContext", Global.getInstance().appContext.toString());
        context = this;

        if (skipLogin){
            launchMainActivity();
        }

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        viewWrong = (TextView)findViewById(R.id.txtViewWrong);

        if(Control.getUser(this) != null)
        {
            launchMainActivity();
        }
    }

    public void login(View view){

        Log.d("LOGIN", "Login Attempt");
        Log.d("LOGIN", username.getText().toString());
        Log.d("LOGIN", password.getText().toString());
        String strUser = username.getText().toString();
        String strPass = password.getText().toString();


        if(Control.getUser(this) == null)
        {


            Control.login(strUser, strPass, this);
            /*
            Log.d("LOGIN", "User Correct");
            if (strPass.equals("password")){
                Log.d("LOGIN", "Password Correct");
                    launchMainActivity();
            }
            else{
                viewWrong.setVisibility(View.VISIBLE);
            }
        }
        else {
            viewWrong.setVisibility(View.VISIBLE);
        */}
    }

    private void launchMainActivity(){
        Intent launchMain = new Intent(this, MainActivity.class);
        startActivity(launchMain);
    }
}
