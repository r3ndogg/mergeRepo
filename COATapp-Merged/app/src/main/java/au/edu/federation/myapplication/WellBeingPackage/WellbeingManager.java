package au.edu.federation.myapplication.WellBeingPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import au.edu.federation.myapplication.GlobalClasses.GlobalOld;
import au.edu.federation.myapplication.R;

/**
 * Created by Nick on 17/09/2017.
 */

public class WellbeingManager extends AppCompatActivity {

    private TextView message;
    private Button startButton;
    private GlobalOld globalOld;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("wellbeing", "entered");

       // message = (TextView) findViewById(R.id.answerQuestionsButton);
       // startButton = (Button) findViewById(R.id.answerQuestionsButton);
       // global = global.getInstance();

        if(globalOld.questions.containsKey(false)){
            setContentView(R.layout.activity_wellbeing_manager);
        }
        else{
            setContentView(R.layout.activity_data);
        }

    }
}
