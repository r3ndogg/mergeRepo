package au.edu.federation.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import au.edu.federation.myapplication.GlobalClasses.Global;
import au.edu.federation.myapplication.PedometerService.SensorListener;
import au.edu.federation.myapplication.WellBeingPackage.WellbeingPopUp;

public class TestingBench extends AppCompatActivity {

    private Button speakButton, notifyButton, startPedometerButton, stopPedometerButton;
    private EditText talkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_bench);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        talkText = (EditText)  findViewById(R.id.speakText);
        speakButton = (Button) findViewById(R.id.speakButton);

        speakButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Global.makeSpeech(talkText.getText().toString());
            }
        });

        notifyButton = (Button) findViewById(R.id.notifyButton);

        notifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(TestingBench.this)
                        .setContentText("Please answer a question")
                        .setContentTitle("Hey!")
                        .setSmallIcon(R.drawable.gamelogo_light);

                Intent myIntent = new Intent(TestingBench.this , WellbeingPopUp.class);
                PendingIntent contentIntent = PendingIntent.getActivity(TestingBench.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(contentIntent);
                builder.setVibrate(new long[] { 1000, 1000});
                builder.setAutoCancel(true);

                int notiId = 001;

                NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notifyManager.notify(notiId, builder.build());

            }
        });

        startPedometerButton = (Button) findViewById(R.id.startPedomButton);
        startPedometerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestingBench.this, SensorListener.class);
                startService(intent);
            }
        });

        stopPedometerButton = (Button) findViewById(R.id.stopPedomButton);
        stopPedometerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestingBench.this, SensorListener.class);
                stopService(intent);
            }
        });
    }

}
