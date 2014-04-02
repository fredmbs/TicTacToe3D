/**
 * Classe que desenha um círculo como uma peça 
 * do jogo da velha 3D
 *  
 * Referência:
 * http://slabode.exofire.net/circle_draw.shtml
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view.obsolete;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.view.TTT3dDrawable;

/**
 * Classe que desenha a peça O do jogo da velha  
 * 
 */
public class TTT3dOShape extends TTT3dDrawable {
	
	private int nVertices;
	private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	private boolean fill = false;
	
	/**
	 *  
	 * 
	 */
	public TTT3dOShape(float r, int num_segments) {
			float theta = (float) ((2 * Math.PI) / num_segments); 
			float c = (float) Math.cos(theta);  //precalculate the sine and cosine
			float s = (float) Math.sin(theta);
			float t;

			float x = r;  //we start at angle = 0 
			float z = 0; 
		    
			float[] vertices;
			nVertices = num_segments;
			// lembre-se, * 3 por causa das coordenadas por vértice (xyz)
			vertices = new float[nVertices*3];

			int nV = 0;
			for(int ii = 0; ii < num_segments; ii++) 
			{ 
				vertices[nV++] = x;
				vertices[nV++] = 0;
				vertices[nV++] = z;
	
				//apply the rotation matrix
				t = x;
				x = c * x - s * z;
				z = s * t + c * z;
			} 
			// Setup vertex array buffer. Vertices in float. A float has 4 bytes
			ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
			vbb.order(ByteOrder.nativeOrder()); // Use native byte order
			vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
			vertexBuffer.put(vertices);         // Copy data into buffer
			vertexBuffer.position(0);           // Rewind	
	}
	
	public void setFillCircle(boolean flag) {
		this.fill = flag;
	}
	
	public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(red, green, blue, alfa);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		if (fill) {
			// Preenche o círculo
			gl.glDisable(GL10.GL_CULL_FACE);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, nVertices);
		} else {
			gl.glLineWidth(4f);
			gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, nVertices);
		}
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
