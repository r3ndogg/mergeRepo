package au.edu.federation.myapplication.WellBeingPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

import au.edu.federation.myapplication.AvatarPackage.AvatarActivity;
import au.edu.federation.myapplication.DatabasePackage.Control;
import au.edu.federation.myapplication.DatabasePackage.WellbeingActivity;
import au.edu.federation.myapplication.GlobalClasses.Global;
import au.edu.federation.myapplication.R;
import au.edu.federation.myapplication.Settings;
import au.edu.federation.myapplication.TestingBench;

/**
 * Created by Nick on 18/09/2017.
 */

public class WellbeingPopUp extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar questionAnswer;
    private ImageView questionImageView;
    private TextView questionTextView;
    private Button speakQuestionButton, submitQuestionButton;
    private Toolbar toolbar;
    private String currentQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellbeing_popup);

        toolbar = (Toolbar) findViewById(R.id.wellbeing_toolbar);
        toolbar.setTitle("COATApp");

        setSupportActionBar(toolbar);

        questionImageView = (ImageView) findViewById(R.id.questionImageView);

        questionAnswer = (SeekBar) findViewById(R.id.questionSeekBar);
        questionAnswer.setOnSeekBarChangeListener(this);
        questionAnswer.setProgress(4);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        questionTextView.setText(getCurrentQuestion());

        speakQuestionButton = (Button) findViewById(R.id.speakQuestionButton);
        speakQuestionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Global.makeSpeech(questionTextView.getText().toString());
            }
        });

        submitQuestionButton = (Button) findViewById(R.id.submitQuestionButton);
        submitQuestionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            submitQuestion();
            //Global.setWellbeingAlarm();
            finish();
            }
        });

    }

    public void setImage(int progress)
    {
        switch (progress) {

            case 0: {
                questionImageView.setImageResource(R.drawable.neutral_face);
                break; }

            case 1: {
                questionImageView.setImageResource(R.drawable.neutral_face);

                break; }
            case 2: {
                questionImageView.setImageResource(R.drawable.neutral_face);
                break; }
            case 3: {
                questionImageView.setImageResource(R.drawable.slightly_happy_face);

                break; }
            case 4: {
                questionImageView.setImageResource(R.drawable.slightly_happy_face);

                break; }
            case 5: {
                questionImageView.setImageResource(R.drawable.slightly_happy_face);

                break; }
            case 6: {
                questionImageView.setImageResource(R.drawable.slightly_happy_face);

                break; }
            case 7: {
                questionImageView.setImageResource(R.drawable.very_happy_face);

                break; }
            case 8: {
                questionImageView.setImageResource(R.drawable.very_happy_face);
                break;
            }
            case 9: {
                questionImageView.setImageResource(R.drawable.very_happy_face);
                break; }
        }
    }

    private String getCurrentQuestion(){

        Iterator it = Global.questions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (!(Boolean)pair.getValue()) {
                currentQuestion = (String) pair.getKey();
                return (String) pair.getKey();
            }
            it.remove(); //To avoid ConcurrentModificationException, just trust me and leave this here
        }
        return "You've answered all the questions, Good Job!";
    }

    private void submitQuestion(){

        switch (currentQuestion){

            case "I feel tired":
                Control.updateWellbeingTableColumn(this, questionAnswer.getProgress(), Control.columnSleep);
                Global.questions.put(currentQuestion, true);
                break;
            case "My Energy Level Is":
                Control.updateWellbeingTableColumn(this, questionAnswer.getProgress(), Control.columnEnergy);
                Global.questions.put(currentQuestion, true);
                break;
            case "My Mood is":
                Control.updateWellbeingTableColumn(this, questionAnswer.getProgress(), Control.columnMood);
                Global.questions.put(currentQuestion, true);
                break;
            default:
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setImage(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

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
