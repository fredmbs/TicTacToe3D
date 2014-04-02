/**
 * Classe que desenha um círculo como uma peça 
 * do jogo da velha 3D
 *  
 * Referência:
 * http://slabode.exofire.net/circle_draw.shtml
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

/**
 * Classe que desenha a peça O do jogo da velha  
 * 
 */
public class TTT3dOShape extends TTT3dDrawable {
	
    private Grid mGrid;

    /**
	 *  
	 * 
	 */
	public TTT3dOShape(GL10 gl, float majorR, float minorR, int num_segments) {

		checkGLError(gl);

		mGrid = generateTorusGrid(gl, num_segments, num_segments, 
				                      majorR, minorR);
		checkGLError(gl);

	}
	
    private Grid generateTorusGrid(GL gl, int uSteps, int vSteps, float majorRadius, float minorRadius) {
        Grid grid = new Grid(uSteps + 1, vSteps + 1);
        for (int j = 0; j <= vSteps; j++) {
            double angleV = Math.PI * 2 * j / vSteps;
            float cosV = (float) Math.cos(angleV);
            float sinV = (float) Math.sin(angleV);
            for (int i = 0; i <= uSteps; i++) {
                double angleU = Math.PI * 2 * i / uSteps;
                float cosU = (float) Math.cos(angleU);
                float sinU = (float) Math.sin(angleU);
                float d = majorRadius+minorRadius*cosU;
                float x = d*cosV;
                float y = d*(-sinV);
                float z = minorRadius * sinU;

                float nx = cosV * cosU;
                float ny = -sinV * cosU;
                float nz = sinU;

                //float length = (float) Math.sqrt(nx*nx + ny*ny + nz*nz);
                //nx /= length;
                //ny /= length;
                //nz /= length;

                grid.set(i, j, x, y, z, nx, ny, nz);
            }
        }
        grid.createBufferObjects(gl);
        return grid;
    }
    
	public void draw(GL10 gl) {
		gl.glPushMatrix();
			gl.glColor4f(red, green, blue, alfa);
			gl.glRotatef(-90, 1, 0, 0);
			gl.glRotatef(180, 0, 1, 0);
			mGrid.draw(gl);
		gl.glPopMatrix();
	}

	  static void checkGLError(GL gl) {
	      int error = ((GL10) gl).glGetError();
	      if (error != GL10.GL_NO_ERROR) {
	  		Log.d("Erro", "TTT3dOShape: GLError 0x" + Integer.toHexString(error));
	        //throw new RuntimeException("TTT3dOShape: GLError 0x" 
	        //                            + Integer.toHexString(error));
	      }
	  }
}
