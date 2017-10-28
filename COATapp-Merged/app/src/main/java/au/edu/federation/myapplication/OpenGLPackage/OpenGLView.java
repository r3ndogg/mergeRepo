package au.edu.federation.myapplication.OpenGLPackage;

/**
 * Created by Nick on 10/09/2017.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Nick on 10/09/2017.
 */

public class OpenGLView extends GLSurfaceView {
    public OpenGLView(Context context) {
        super(context);
        init();
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        setRenderer(new OpenGLRenderer());
    }
}
