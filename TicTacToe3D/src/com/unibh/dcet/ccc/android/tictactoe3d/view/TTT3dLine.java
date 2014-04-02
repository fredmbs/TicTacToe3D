/**
 * Classe que desenha uma camada em forma de grade ("grid") 
 * do jogo da velha 3D
 *  
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Classe que desenha a forma de uma grade de uma das camadas 
 * para o jogo da velha 3D
 * 
 */
public class TTT3dLine {

	private FloatBuffer vertexBuffer; 
	private FloatBuffer colorBuffer;  
	
	/**
	 * 
	 */
	public TTT3dLine(float lenght, float unidade, 
			         float[] startColor, float[] endColor) {
		// 
		float largura = (unidade * lenght);
        float vertices[] = {
                0, 0, 0,
                0, largura, 0
        };

        float colors[] = {
                startColor[0],  startColor[1],  startColor[2], startColor[3],
                endColor[0],  endColor[1],  endColor[2], endColor[3]
        };

        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		vertexBuffer.put(vertices);         // Copy data into buffer
		vertexBuffer.position(0);           // Rewind	

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);		
	}

	// Render the shape
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glLineWidth(1f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
