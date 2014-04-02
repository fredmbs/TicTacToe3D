/**
 * Interface para generalizar as peças  
 * do jogo da velha 3D
 *  
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view;

import javax.microedition.khronos.opengles.GL10;


/**
 * Classe abstrata para objetos visuais que possuem informação de cor
 * 
 */
public abstract class TTT3dDrawable {

	protected float red = 1f;
	protected float green = 1f;
	protected float blue = 1f;
	protected float alfa = 1f;

	/**
	 * 
	 */
	public void setColor (float[] c) {
		if (c.length >= 3) {
			this.red = c[0];
			this.green = c[1];
			this.blue = c[2];
			if (c.length >= 4)
				this.alfa = c[3];
		}
	}

	public float[] getColor() {
		float[] result = {red, green, blue, alfa}; 
		return result;
	}

	abstract public void draw(GL10 gl);
	
}
