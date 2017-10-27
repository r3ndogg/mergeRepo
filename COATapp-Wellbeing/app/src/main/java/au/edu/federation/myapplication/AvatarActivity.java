package au.edu.federation.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import au.edu.federation.myapplication.OpenGLPackage.OpenGLView;

/**
 * Created by Nick on 31/05/2017.
 */

public class AvatarActivity extends AppCompatActivity {

    private OpenGLView openGLView;

    private float[] vertices = {  // Vertices of the 6 faces

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        openGLView = (OpenGLView) findViewById(R.id.openGLView);

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
