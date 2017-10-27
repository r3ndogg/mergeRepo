package au.edu.federation.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;
import static android.opengl.GLES10.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES10.GL_FLOAT;
import static android.opengl.GLES10.GL_LINEAR;
import static android.opengl.GLES10.GL_MODELVIEW;
import static android.opengl.GLES10.GL_TEXTURE_2D;
import static android.opengl.GLES10.GL_TEXTURE_COORD_ARRAY;
import static android.opengl.GLES10.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES10.GL_TRIANGLE_STRIP;
import static android.opengl.GLES10.GL_VERTEX_ARRAY;

// Import all GLES10 static members so we don't have to prefix everything with gl.<blah> or GL10.<blah>

public class GLTexQuad
{
	// X and y for 2D
	public final int COORDS_PER_VERTEX = 2;

	// Float.SIZE gives the size of a float in bits, so we divide by 8 to get the size in bytes
	public final int BYTES_PER_FLOAT = Float.SIZE / 8;

	// Buffers to store our vertex and texture coordinates
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;

	// Each texture must have a unique Id value
	private int textureId;

	// Initial array to hold vertices (we won't know the width or height values to use until
	// we instantiate a GLTexQuad!)
	private float vertices[];

	// 2D Texture coordinates (u, v)
	private static float texCoord[] = { 0.0f, 0.0f,   // Bottom left
			                            1.0f, 0.0f,   // Bottom right
			                            0.0f, 1.0f,   // Top left
			                            1.0f, 1.0f }; // Top right

	// Constructor
	public GLTexQuad(GL10 gl, Context context, int resourceId, int width, int height)
	{
		// Construct the vertices of the quad based on the width and height of the display
		vertices = new float[] { 0.0f,  0.0f,  // Top Left
				                 width, 0.0f,  // Top Right
				                 0.0f, height, // Bottom Left
				                 width, height // Bottom Right
		};

		// Load the texture to gets its Id value
		textureId = loadGLTexture(gl, context, resourceId);

		// Set up the vertex coordinates buffer
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.flip();

		// Set up the texture coordinates buffer
		byteBuf = ByteBuffer.allocateDirect(texCoord.length * BYTES_PER_FLOAT);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texCoord);
		textureBuffer.flip();
	}

	// Method to draw the textured quad
	public void draw(GL10 gl)
	{
		// Reset the ModelView matrix
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();

		// Bind to our texture so that we can sample from it
		gl.glBindTexture(GL_TEXTURE_2D, textureId);

		// Enable 2D textures
		gl.glEnable(GL_TEXTURE_2D);

		// Point to our vertex buffer and texture buffer. Params: Size (i.e. num
		// of values), type, stride, buffer
		gl.glVertexPointer(2, GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL_FLOAT, 0, textureBuffer);

		// Draw in white so the texture comes out "normally" (i.e. not tinted in any way)
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		// Enable vertex and texture arrays
		gl.glEnableClientState(GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

			// Draw the vertices as triangle strip
			gl.glDrawArrays(GL_TRIANGLE_STRIP, 0, vertices.length / COORDS_PER_VERTEX);

		// Disable the client state before leaving
		gl.glDisableClientState(GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);

		// Disable 2D textures
		gl.glDisable(GL_TEXTURE_2D);
	}

	public static int loadGLTexture(GL10 gl, Context context, int resourceID)
	{
		// Even though we are only generating a single texture id, we have to use
		// an array with a single element rather than just an int as the OpenGL
		// texture handling methods require an array of ints for input!
		int[] textureId = new int[1];

		// Get the texture from the Android resource directory
		try
		{
			// ----- File Reading Section -----
			// Get the file to open as an InputStream
			InputStream is = context.getResources().openRawResource(resourceID);

			// Create a Bitmap object
			Bitmap bitmap = null;
			// Attempt to decode the input stream into the bitmap
			try
			{
				bitmap = BitmapFactory.decodeStream(is);
			}
			finally
			{
				// Always clear and close the input stream
				try
				{
					is.close();
					is = null;
				}
				catch (IOException e) {	Log.e(TAG, "Failed to close input stream!"); }
			}

			// ----- OpenGL Texture Generation Section -----
			// Generate a new ID...
			gl.glGenTextures(1, textureId, 0);

			// ...and bind it to our texture
			gl.glBindTexture(GL_TEXTURE_2D, textureId[0]);

			// Specify texture filtering
			gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			// Specify texture wrapping should be to clamp to the edge of the texture
			gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

			// Create a 2D texture from our loaded bitmap
			GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

			// Free the memory used by the bitmap
			bitmap.recycle();
		}
		catch (Resources.NotFoundException nfe) { Log.e(TAG, nfe.getMessage()); }

		// Return the texture handle
		return textureId[0];

	} // End of loadGLTexture method

} // End of GLTexQuad class
