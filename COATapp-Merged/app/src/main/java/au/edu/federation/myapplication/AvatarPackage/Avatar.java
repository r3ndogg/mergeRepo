package au.edu.federation.myapplication.AvatarPackage;

/**
 * Created by Victor on 1/06/2017.
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Avatar {

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private int numFaces = 6;

    private float[] vertices = {
            // FRONT

            -2.242601f, -1.000000f, -3.797877f,
            2.242601f, -1.000000f, 3.797877f,
            -2.242601f, -1.000000f, 3.797876f,
            -2.242600f, -1.000000f, -3.797878f,
            2.242602f, 1.177370f, -3.797875f,
            2.242600f, 1.177370f, 3.797879f,
            -2.242601f, 1.177370f, -3.797877f,
            -2.242602f, 1.177370f, 3.797875f,
            0.000000f, -1.000000f, -3.797877f,
            0.000000f, -1.000000f, 3.797876f,
            0.000001f, 2.542001f, -3.797876f,
            -0.000001f, 2.542001f, 3.797877f,
            -1.121300f, -1.000000f, -3.797878f,
            -1.121301f, -1.000000f, 3.797876f,
            -1.121300f, 2.293459f, -3.797876f,
            -1.121302f, 2.293460f, 3.797876f,
            1.121301f, -1.000000f, -3.797877f,
            1.121301f, -1.000000f, 3.797877f,
            1.121301f, 2.293459f, -3.797875f,
            1.121299f, 2.293460f, 3.797878f,
            1.681951f, -1.000000f, -3.797877f,
            1.681951f, -1.000000f, 3.797877f,
            1.681952f, 1.866538f, -3.797875f,
            1.681949f, 1.866538f, 3.797879f,
            0.560651f, -1.000000f, -3.797877f,
            0.560650f, -1.000000f, 3.797876f,
            0.560651f, 2.484304f, -3.797876f,
            0.560649f, 2.484304f, 3.797878f,
            -0.560650f, -1.000000f, -3.797878f,
            -0.560650f, -1.000000f, 3.797876f,
            -0.560650f, 2.484304f, -3.797876f,
            -0.560651f, 2.484304f, 3.797877f,
            -1.681950f, -1.000000f, -3.797878f,
            -1.681951f, -1.000000f, 3.797876f,
            -1.681951f, 1.866538f, -3.797877f,
            -1.681952f, 1.866538f, 3.797876f

    };

    // Constructor - Set up the buffers
    public Avatar() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    // Draw the shape
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Render all the faces
        //for (int face = 0; face < numFaces; face++) {
            // Set the color for each of the faces
        //    gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
            // Draw the primitive from the vertex-array directly
        //    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
        //}
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}