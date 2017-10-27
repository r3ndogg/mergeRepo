package au.edu.federation.myapplication;

/**
 * Created by Victor on 28/05/2017.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AvatarRenderer implements GLSurfaceView.Renderer
{
    //private SnowParticle particleArray[];
    private GLTexQuad background;

        //private Pyramid pyramid;    // (NEW)
        private Avatar avatar;          // (NEW)

        private static float anglePyramid = 0; // Rotational angle in degree for pyramid (NEW)
        private static float angleCube = 0;    // Rotational angle in degree for cube (NEW)
        private static float speedPyramid = 2.0f; // Rotational speed for pyramid (NEW)
        private static float speedCube = -1.5f;   // Rotational speed for cube (NEW)

        // Constructor
        public AvatarRenderer(Context context) {
            // Set up the buffers for these shapes
            //pyramid = new Pyramid();   // (NEW)
            avatar = new Avatar();         // (NEW)
        }

        // Call back when the surface is first created or re-created.
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
            gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
            gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
            gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
            gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
            gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

            // You OpenGL|ES initialization code here
            // ......
        }

        // Call back after onSurfaceCreated() or whenever the window's size changes.
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            if (height == 0) height = 1;   // To prevent divide by zero
            float aspect = (float)width / height;



            // Set the viewport (display area) to cover the entire window
            gl.glViewport(0, 0, width, height);

            // Setup perspective projection, with aspect ratio matches viewport
            gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
            gl.glLoadIdentity();                 // Reset projection matrix
            // Use perspective projection
            GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

            gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
            gl.glLoadIdentity();                 // Reset

            // You OpenGL|ES display re-sizing code here
            // ......

            //Set texture to use for background
            //background = new GLTexQuad(gl, WellbeingActivity.getContext(), R.drawable.mountain_background, 200, 200);
            background = new GLTexQuad(gl, WellbeingActivity.getContext(), R.drawable.very_happy_face, width, height);
        }

        // Call back to draw the current frame.
        @Override
        public void onDrawFrame(GL10 gl) {
            // Clear color and depth buffers
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            //Draw background
            background.draw(gl);

            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
/*
            // ----- Render the Pyramid -----
            gl.glLoadIdentity();                 // Reset the model-view matrix
            gl.glTranslatef(-1.5f, 0.0f, -6.0f); // Translate left and into the screen
            gl.glRotatef(anglePyramid, 0.1f, 1.0f, -0.1f); // Rotate (NEW)
            pyramid.draw(gl);                              // Draw the pyramid (NEW)
*/
            // ----- Render the Color Cube -----
            gl.glLoadIdentity();                // Reset the model-view matrix
            gl.glTranslatef(1.5f, 0.0f, -6.0f); // Translate right and into the screen
            gl.glScalef(0.8f, 0.8f, 0.8f);      // Scale down (NEW)
            gl.glRotatef(angleCube, 1.0f, 1.0f, 1.0f); // rotate about the axis (1,1,1) (NEW)
            avatar.draw(gl);                      // Draw the cube (NEW)

            // Update the rotational angle after each refresh (NEW)
            //anglePyramid += speedPyramid;   // (NEW)
            angleCube += speedCube;         // (NEW)
        }
    }

