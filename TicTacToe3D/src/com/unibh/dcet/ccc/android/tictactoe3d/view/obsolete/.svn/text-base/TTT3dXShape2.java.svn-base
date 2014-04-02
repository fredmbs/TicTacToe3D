/**
 * Classe que desenha um TTT3dXShape como uma peça 
 * do jogo da velha 3D
 *  
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view.obsolete;

import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.view.Cylinder;
import com.unibh.dcet.ccc.android.tictactoe3d.view.TTT3dDrawable;

/**
 * Classe que desenha a peça X do jogo da velha  
 * 
 */
public class TTT3dXShape2 extends TTT3dDrawable {

	private Cylinder cilindro;

	/**
	 * 
	 */
	public TTT3dXShape2(GL10 gl, float r, float height) {
		//cilindro = new Cylinder(r, height, 60);
		cilindro = new Cylinder(0.1f, 1f, 4);
		cilindro.createBufferObjects(gl);
	}
	
	public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_CULL_FACE);
		gl.glColor4f(red, green, blue, alfa);
		gl.glPushMatrix();
			gl.glRotatef(90, 1, 0, 0);
			gl.glPushMatrix();
				gl.glRotatef(45, 0, 0, 1);
				cilindro.draw(gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glRotatef(135, 0, 0, 1);
				cilindro.draw(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
