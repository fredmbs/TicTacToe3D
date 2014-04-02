/**
 * Classe que desenha um TTT3dXShape como uma peça 
 * do jogo da velha 3D
 *  
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view.obsolete;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.view.TTT3dDrawable;

/**
 * Classe que desenha a peça X do jogo da velha  
 * 
 */
public class TTT3dXShape extends TTT3dDrawable {

	private int nVertices;
	private FloatBuffer vertexBuffer;  // Buffer for vertex-array

	/**
	 * 
	 */
	public TTT3dXShape(float r) {

		    float theta, x, z;
			float[] vertices;
			nVertices = 12;
			vertices = new float[nVertices];

			theta = (float) (45 * (Math.PI/180)); 
			x = (float) (r * Math.cos(theta)); 
			z = (float) (r * Math.sin(theta)); 
			vertices[0] = x;
			vertices[1] = 0;
			vertices[2] = z;
			theta = (float) (225 * (Math.PI/180)); 
			x = (float) (r * Math.cos(theta)); 
			z = (float) (r * Math.sin(theta)); 
			vertices[3] = x;
			vertices[4] = 0;
			vertices[5] = z;

			theta = (float) (135 * (Math.PI/180)); 
			x = (float) (r * Math.cos(theta)); 
			z = (float) (r * Math.sin(theta)); 
			vertices[6] = x;
			vertices[7] = 0;
			vertices[8] = z;
			theta = (float) (315 * (Math.PI/180)); 
			x = (float) (r * Math.cos(theta)); 
			z = (float) (r * Math.sin(theta)); 
			vertices[9]  = x;
			vertices[10] = 0;
			vertices[11] = z;

			// Setup vertex array buffer. Vertices in float. A float has 4 bytes
			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
			vbb.order(ByteOrder.nativeOrder()); // Use native byte order
			vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
			vertexBuffer.put(vertices);         // Copy data into buffer
			vertexBuffer.position(0);           // Rewind	
	}
	
	public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(red, green, blue, alfa);
		gl.glLineWidth(4f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// Draw the primitives from the vertex-array directly
		gl.glDrawArrays(GL10.GL_LINES, 0, nVertices / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
