package au.edu.federation.myapplication;

/**
 * Created by Nick on 30/09/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.Random;

public class TestActivity extends Activity implements OnInitListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(getApplicationContext(), this);
    }

    TextToSpeech tts = null;

    @Override
    public void onInit(int arg0) {
        for (int i = 0; i < 100; ++i) {
            class Irritate implements Runnable {
                Irritate(int iIn) {
                    i = iIn;
                }

                @Override
                public void run() {
                    Random r = new Random();
                    try {
                        Thread.sleep(r.nextInt(2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    tts.speak(Integer.toString(i), TextToSpeech.QUEUE_ADD, null);
                }

                int i;
            }

            Thread t = new Thread(new Irritate(i));

            t.start();
        }
    }
}