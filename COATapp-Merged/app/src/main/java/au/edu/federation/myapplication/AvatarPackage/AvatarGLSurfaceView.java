package au.edu.federation.myapplication.AvatarPackage;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Victor on 30/05/2017.
 */

public class AvatarGLSurfaceView extends GLSurfaceView
{
    private AvatarRenderer avatarRenderer;

    public AvatarGLSurfaceView(Context context)
    {
        super(context);
    }

    public AvatarGLSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }


}

