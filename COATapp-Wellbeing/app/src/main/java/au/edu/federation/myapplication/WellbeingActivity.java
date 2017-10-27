package au.edu.federation.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by Victor on 15/04/2017.
 */

public class WellbeingActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private AvatarGLSurfaceView avatarGLSurfaceView;
    private AvatarRenderer avatarRenderer;
    //static float avatarWindowWidth, avatarWindowHeight;
    static float screenWidth, screenHeight;
    private static Context context;

    //private Control control;

    private ImageView sleepImage;
    private ImageView moodImage;
    private ImageView energyImage;

    private SeekBar sleepSeekBar;
    private SeekBar moodSeekBar;
    private SeekBar energySeekBar;

    private LinearLayout sleepLayout;
    private LinearLayout moodLayout;
    private LinearLayout energyLayout;

    private EditText feedbackEditText;
    private TextView textView;



    private String informationTypeSleep = "Sleep Information Saved!";
    private String informationTypeMood = "Mood Information Saved!";
    private String informationTypeEnergy = "Energy Information Saved!";
    private String informationTypeFeedback = "Feedback Information Saved!";

    private String informationTypeFeedbackLarge = "Feedback cannot be over 255 characters";
    private String informationTypeFeedbackSmall = "No feedback entered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////////////////////////////////////////////////////////////

        context = getApplicationContext();

        //Get the screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.heightPixels;
        screenHeight = displaymetrics.widthPixels;



        //Instantiate GLSurfaceView
        avatarGLSurfaceView = new AvatarGLSurfaceView(this);
        //avatarGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);


        //Instantiate renderer
        avatarRenderer = new AvatarRenderer(getContext());

        //Must use setContentView before using findViewById(R.id.avatarGLSurfaceView)

        setContentView(R.layout.activity_wellbeing);

        //Get instance of AvatarGLSurfaceView from activity_wellbeing
        avatarGLSurfaceView = (AvatarGLSurfaceView) findViewById(R.id.avatarGLSurfaceView);


        //Use avatarRenderer to draw
        avatarGLSurfaceView.setRenderer(avatarRenderer);

        screenHeight = avatarGLSurfaceView.getHeight();
        screenWidth = avatarGLSurfaceView.getWidth();
///////////////////////////////////////////////////////////

        sleepImage = (ImageView) findViewById(R.id.sleepImage);
        moodImage = (ImageView) findViewById(R.id.moodImage);
        energyImage = (ImageView) findViewById(R.id.energyImage);

        sleepSeekBar = (SeekBar) findViewById(R.id.sleepSeekbar);
        moodSeekBar = (SeekBar) findViewById(R.id.moodSeekbar);
        energySeekBar = (SeekBar) findViewById(R.id.energySeekbar);
        feedbackEditText = (EditText) findViewById(R.id.feedbackEditText);

        sleepSeekBar.setOnSeekBarChangeListener(this);
        moodSeekBar.setOnSeekBarChangeListener(this);
        energySeekBar.setOnSeekBarChangeListener(this);

        sleepSeekBar.setProgress(0);
        moodSeekBar.setProgress(0);
        energySeekBar.setProgress(0);

        setImage(sleepSeekBar.getProgress(), sleepImage);
        setImage(moodSeekBar.getProgress(), moodImage);
        setImage(energySeekBar.getProgress(), energyImage);

        sleepLayout = (LinearLayout) findViewById(R.id.sleepLayout);
        moodLayout = (LinearLayout) findViewById(R.id.moodLayout);
        energyLayout = (LinearLayout) findViewById(R.id.energyLayout);

        //control = new Control();

        Control.inspectWellbeingTable(this);

        //control.setUser(this, 9999, "Someone");
        //control.addWellbeing(this, 9999);
        //display();

        checkAnsweredQuestions();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        avatarGLSurfaceView.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        avatarGLSurfaceView.onResume();
    }

    public void handleButtonPress(View v) {

        Control.inspectWellbeingTable(this);

        switch (v.getId()) {

            case R.id.saveQuestion1Button: {
                Control.updateWellbeingTableColumn(this, sleepSeekBar.getProgress(), UserRecordMySQLiteHelper.COLUMN_QUESTION_1);

                displayInformationSavedToast(informationTypeSleep);
                clearAnsweredQuestion(sleepLayout);
                break; }

            case R.id.saveQuestion2Button: {
                Control.updateWellbeingTableColumn(this, moodSeekBar.getProgress(), UserRecordMySQLiteHelper.COLUMN_QUESTION_2);

                displayInformationSavedToast(informationTypeMood);
                clearAnsweredQuestion(moodLayout);
                break; }

            case R.id.saveQuestion3Button: {
                Control.updateWellbeingTableColumn(this, energySeekBar.getProgress(), UserRecordMySQLiteHelper.COLUMN_QUESTION_3);

                displayInformationSavedToast(informationTypeEnergy);
                clearAnsweredQuestion(energyLayout);
                break; }

            /*case R.id.saveQuestion4Button: {
                Control.updateWellbeingTableColumn(this, energySeekBar.getProgress(), UserRecordMySQLiteHelper.COLUMN_QUESTION_3);

                displayInformationSavedToast(informationTypeEnergy);
                clearAnsweredQuestion(energyLayout);
                break; }

            case R.id.saveQuestion5Button: {
                Control.updateWellbeingTableColumn(this, energySeekBar.getProgress(), UserRecordMySQLiteHelper.COLUMN_QUESTION_3);

                displayInformationSavedToast(informationTypeEnergy);
                clearAnsweredQuestion(energyLayout);
                break; }*/

            case R.id.saveFeedbackButton: {

                String feedback = feedbackEditText.getText().toString();
                if(feedback.length() > 255)
                {
                    displayInformationSavedToast(informationTypeFeedbackLarge);
                }
                if(feedback.length() == 0)
                {
                    displayInformationSavedToast(informationTypeFeedbackSmall);
                }
                if(feedback.length() <= 255 && feedback.length() > 0)
                {
                    Control.insertWellbeingCommentsTableRow(this, feedback);
                    feedbackEditText.setText("");
                    displayInformationSavedToast(informationTypeFeedback);
                }

                break; }

            case R.id.loadTableButton: {
                display();

                break; }
        }
    }

    public void clearAnsweredQuestion(LinearLayout ll)
    {
        ll.removeAllViews();
    }

    public void checkAnsweredQuestions()
    {
        Wellbeing wb;
        wb = Control.getWellbeingTableCurrentDay(this);

        if (wb.getQuestion1() != -1)
        {
            clearAnsweredQuestion(sleepLayout);
        }

        if (wb.getQuestion2() != -1)
        {
            clearAnsweredQuestion(moodLayout);
        }

        if (wb.getQuestion3() != -1)
        {
            clearAnsweredQuestion(energyLayout);
        }
    }

    public void displayInformationSavedToast(String informationType)
    {
        Context context = getApplicationContext();
        CharSequence text = informationType;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void display()
    {
        TextView userIDTextView = (TextView) findViewById(R.id.dbTestTextView);
        User user = Control.getUser(this);
        List<Wellbeing> wellbeingList = Control.getWellbeingTable(this);
        List<WellbeingComment> wellbeingCommentList = Control.getWellbeingCommentsTable(this);
        String rows = "";

        rows += "Current Date: " + Time.getCurrentDate() + "\n"
                + "User Table " + "\n"
                + "UserID: " + user.getUserID() + "\n"
                + "AP: " + user.getAchievementPoints() + "\n"
                + "Gender: " + user.getGender() + "\n"
                + "Name: " + user.getName() + "\n\n"
                + "Wellbeing Table " + "\n\n";

        for (Wellbeing w : wellbeingList)
        {
            rows += "UserID: " + w.getUserID() + "\n"
                    + "Question1: " + w.getQuestion1() + "\n"
                    + "Question2: " + w.getQuestion2() + "\n"
                    + "Question3: " + w.getQuestion3() + "\n"
                    + "Question4: " + w.getQuestion4() + "\n"
                    + "Question5: " + w.getQuestion5() + "\n"
                    + "Date: " + w.getDate() + "\n\n";
        }


        rows += "WellbeingComments Table " + "\n\n";

        for (WellbeingComment w : wellbeingCommentList)
        {
            rows += "UserID: " + w.getUserID() + "\n"
                    + "Comment: " + w.getComment() + "\n"
                    + "DateTime: " + w.getDate() + "\n\n";
        }
        userIDTextView.setText(rows);

    }

    public void setImage(int progress, ImageView imageView)
    {
        switch (progress) {

            case 0: {
                imageView.setImageResource(R.drawable.very_happy_face);
                break; }

            case 1: {
                imageView.setImageResource(R.drawable.very_happy_face);

                break; }
            case 2: {
                imageView.setImageResource(R.drawable.slightly_happy_face);
                break; }
            case 3: {
                imageView.setImageResource(R.drawable.slightly_happy_face);

                break; }
            case 4: {
                imageView.setImageResource(R.drawable.neutral_face);

                break; }
            case 5: {
                imageView.setImageResource(R.drawable.neutral_face);

                break; }
            case 6: {
                imageView.setImageResource(R.drawable.slightly_sad_face);

                break; }
            case 7: {
                imageView.setImageResource(R.drawable.slightly_sad_face);

                break; }
            case 8: {
                imageView.setImageResource(R.drawable.very_sad_face);

                break; }
            case 9: {
                imageView.setImageResource(R.drawable.very_sad_face);

                break; }
        }
    }
    /*public void createQuestion(String question, LinearLayout questionsLayout)
    {
        //Create TextView
        TextView textView = new TextView(this);
        textView.setText(question);
        //textView.setLayoutParams(params);

        //Create SeekBar
        SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(9);
        seekBar.setOnSeekBarChangeListener(this);
        //seekBar.setLayoutParams(params);

        //Create ImageView
        ImageView imageView = new ImageView(this);
        //imageView.setLayoutParams(params);

        //Create Button
        Button buttonView=new Button(this);
        buttonView.setText("Save");
        //button.setLayoutParams(params);

        //Add objects to questionsLayout container
        questionsLayout.addView(textView);
        questionsLayout.addView(seekBar);
        questionsLayout.addView(imageView);
        questionsLayout.addView(buttonView);
    }*/

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // Log the progress
        Log.d("DEBUG", "Progress is: "+progress);
        //set textView's text
        //yourTextView.setText(""+progress);

        switch (seekBar.getId()) {

            case R.id.sleepSeekbar: {
                setImage(progress, (ImageView) findViewById(R.id.sleepImage));
                break; }

            case R.id.moodSeekbar: {
                setImage(progress, (ImageView) findViewById(R.id.moodImage));

                break; }

            case R.id.energySeekbar: {
                setImage(progress, (ImageView) findViewById(R.id.energyImage));

                break; }
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    // Method to return the application context
    public static Context getContext()
    {
        return context;
    }
}
