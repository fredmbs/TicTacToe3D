package com.unibh.dcet.ccc.android.tictactoe3d.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Classe que desenha uma área transparente para uma das camadas 
 * do jogo da velha 3D
 * 
 *   Em teste. Não usar em produção.
 * 
 */
public class TTT3dSquareShape {

	private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	private int nVertices;

	/**
	 * 
	 */
	public TTT3dSquareShape(int dimension, float unit) {
		// 
		nVertices = 4*3;
		float[] vertices;
		vertices = new float[nVertices];

		float u = unit * dimension;
		
		vertices[0] = 0; vertices[1]  = 0; vertices[2]  = 0;
		vertices[3] = u; vertices[4]  = 0; vertices[5]  = 0;
		vertices[6] = 0; vertices[7]  = 0; vertices[8]  = u;
		vertices[9] = u; vertices[10] = 0; vertices[11] = u;
		
		
		// Setup vertex array buffer. Vertices in float. A float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		vertexBuffer.put(vertices);         // Copy data into buffer
		vertexBuffer.position(0);           // Rewind	

	}
	
	// desenha um destaque na posição do modelo
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// define o buffer de váertices
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// Draw the primitives from the vertex-array directly
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, nVertices / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
