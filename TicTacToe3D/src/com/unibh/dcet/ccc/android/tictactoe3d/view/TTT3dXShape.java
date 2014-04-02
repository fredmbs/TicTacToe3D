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
public class TTT3dXShape extends TTT3dDrawable {
	
    private Grid mGrid;
    private Circle mCircle;
    float mRadius;
    float mHeight;
    final int muSteps;
    final int mvSteps;

    /**
	 *  
	 * 
	 */
	public TTT3dXShape(GL10 gl, float radius, float height,
    		int uSteps, int vSteps) {
		checkGLError(gl); 
        mRadius = radius;
        mHeight = height;
        muSteps = uSteps;
        mvSteps = vSteps;
		mGrid = generateCylinderGrid(gl, mRadius, mHeight, muSteps, mvSteps);
		mCircle = new Circle(mRadius, muSteps);
		checkGLError(gl);
	}
	
    private Grid generateCylinderGrid(GL gl, float radius, float height,
    		int uSteps, int vSteps) {

        Grid grid = new Grid(uSteps + 1, vSteps + 1);

        for (int j = 0; j <= vSteps; j++) {
            for (int i = 0; i <= uSteps; i++) {
                double angle = Math.PI * 2 * i / uSteps;
                float nx = (float) Math.cos(angle);
                float ny = 0f;
                float nz = (float) Math.sin(angle);
                float x = radius * nx;
                float y = height * ((float) j / vSteps - 0.5f);
                float z = radius * nz;
                grid.set(i, j, x, y, z, nx, ny, nz);
            }
        }

        grid.createBufferObjects(gl);
        return grid;
    }
    
    private void drawFullCylinder(GL10 gl) {
		mGrid.draw(gl);
		gl.glPushMatrix();
			gl.glNormal3f(1, 0, 0);
			gl.glTranslatef(0f, mHeight/2, 0f);
			gl.glRotatef(180, 1, 0, 0);
			mCircle.draw(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glNormal3f(1, 0, 0);
			gl.glTranslatef(0f, -mHeight/2, 0f);
			mCircle.draw(gl);
		gl.glPopMatrix();
    	
    }
    
	public void draw(GL10 gl) {
		gl.glPushMatrix();
			gl.glColor4f(red, green, blue, alfa);
			gl.glRotatef(90, 0, 0, 1);
			gl.glPushMatrix();
				gl.glRotatef(45, 1, 0, 0);
				drawFullCylinder(gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glRotatef(135, 1, 0, 0);
				drawFullCylinder(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
	}

	  static void checkGLError(GL gl) {
	      int error = ((GL10) gl).glGetError();
	      if (error != GL10.GL_NO_ERROR) {
	  		Log.d("Erro", "TTT3dXShape: GLError 0x" + 
	  				Integer.toHexString(error));
	      }
	  }
}
