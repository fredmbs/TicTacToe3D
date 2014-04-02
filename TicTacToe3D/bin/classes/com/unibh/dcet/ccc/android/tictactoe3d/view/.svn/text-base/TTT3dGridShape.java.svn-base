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
public class TTT3dGridShape {

	private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	private int nVertices;
	
	/**
	 * 
	 */
	public TTT3dGridShape(int dimensao, float unidade) {
		// 
		float[] vertices;
		// número de vértice = (dimensao + 1) (uma linha extra para fechar)  
		//                     * 2 linhas (uma no eixo x e outra no eixo z)
		//                     * 2 pontos por linhas (pi>pf)
		//                     * 3 coordenadas por ponto p(xyz)
		nVertices = (dimensao+1)*12;
		vertices = new float[nVertices];
		
		float largura = (unidade * dimensao);
		
		int i, indiceVertice = 0;
		float posZ = 0;
		// linhas paralelas ao exio x
		for (i = 0; i <= dimensao; i++) {
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++] = posZ;  
			vertices[indiceVertice++] = largura;  
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++]   = posZ;  
			posZ += unidade;
		}
		float posX = 0;
		// linhas paralelas ao exio z
		for (i = 0; i <= dimensao; i++) {
			vertices[indiceVertice++] = posX;  
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++] = posX;  
			vertices[indiceVertice++] = 0;  
			vertices[indiceVertice++]   = largura;  
			posX += unidade;
		}
		// Setup vertex array buffer. Vertices in float. A float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		vertexBuffer.put(vertices);         // Copy data into buffer
		vertexBuffer.position(0);           // Rewind	
	}

	// Render the shape
	public void draw(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLineWidth(1f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_LINES, 0, nVertices / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
