package com.unibh.dcet.ccc.android.tictactoe3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Implement a simple rotation control.
 *
 */
class TTT3dController extends GLSurfaceView {

	// variáveis de controle do jogo
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private final float TRACKBALL_SCALE_FACTOR = 36.0f;
	private TTT3dView mRenderer;
	private TTT3dModel jogo;
	private float mPreviousX;
	private float mPreviousY;
	
	public TTT3dController(Context context, TTT3dModel gameModel) {
		super(context);
		jogo = gameModel;
		// cria objeto de renderização
		mRenderer = new TTT3dView(jogo);
		// configura objeto de renderização
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override public boolean onTrackballEvent(MotionEvent e) {
		jogo.addAxisXAngle(e.getX() * TRACKBALL_SCALE_FACTOR);
		jogo.addAxisYAngle(e.getY() * TRACKBALL_SCALE_FACTOR);
		requestRender();
		return true;
	}

	/**
	 * Modifica os angulos de rotação pela entrada de eventos
	 */
	private void rotate(float x, float y) {
		float dx = x - mPreviousX;
		float dy = y - mPreviousY;
		jogo.addAxisXAngle(dy * TOUCH_SCALE_FACTOR);
		jogo.addAxisYAngle(dx * TOUCH_SCALE_FACTOR);
		requestRender();
	}

	/**
	 * Controle de eventos de mudança de estado do jogo
	 */
	@Override public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		mRenderer.setMousePos((int)x, (int)y);
		switch (e.getAction()) {
		case MotionEvent.ACTION_UP:
			if (jogo.setPlayMode()) {
				requestRender();
			};
			break;
		case MotionEvent.ACTION_DOWN:
			jogo.setPickMode();
			requestRender();
			break;
		case MotionEvent.ACTION_MOVE:
			int gameState = jogo.getMode();
			if (gameState == TTT3dModel.ROTATION) {
				rotate(x, y);
				requestRender();
			} else if (gameState == TTT3dModel.SELECT) {
				requestRender();
			}
			break;
		}
		mPreviousX = x;
		mPreviousY = y;
		return true;
	}


}


