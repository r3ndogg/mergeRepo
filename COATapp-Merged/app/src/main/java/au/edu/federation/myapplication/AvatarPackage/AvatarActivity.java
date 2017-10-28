package au.edu.federation.myapplication.AvatarPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import au.edu.federation.myapplication.OpenGLPackage.OpenGLView;
import au.edu.federation.myapplication.R;
import au.edu.federation.myapplication.GlobalClasses.SharedPreferencesHandler;

/**
 * Created by Nick on 31/05/2017.
 */

public class AvatarActivity extends AppCompatActivity {

    private OpenGLView openGLView;
    public SharedPreferencesHandler prefHand;
    private Toolbar toolbar;
    private ProgressBar levelBar;

    private float[] vertices = {  // Vertices of the 6 faces

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        //openGLView = (OpenGLView) findViewById(R.id.openGLView);

        //ObjLoader objLoader = new ObjLoader(this, "Mug.obj");

        //numFaces = objLoader.numFaces;

        // Initialize the buffers.
       // positions = ByteBuffer.allocateDirect(objLoader.positions.length * mBytesPerFloat)
       //         .order(ByteOrder.nativeOrder()).asFloatBuffer();
       // positions.put(objLoader.positions).position(0);

       // normals = ByteBuffer.allocateDirect(objLoader.normals.length * mBytesPerFloat)
        //        .order(ByteOrder.nativeOrder()).asFloatBuffer();
       // normals.put(objLoader.normals).position(0);

       // textureCoordinates = ByteBuffer.allocateDirect(objLoader.textureCoordinates.length * mBytesPerFloat)
      //          .order(ByteOrder.nativeOrder()).asFloatBuffer();
      //  textureCoordinates.put(objLoader.textureCoordinates).position(0);

        levelBar = (ProgressBar) findViewById(R.id.levelBar);

        levelBar.setProgress(40);

        prefHand = new SharedPreferencesHandler();
        int coins = prefHand.getBalance(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Avatar");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitle("Coins: " + coins);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
