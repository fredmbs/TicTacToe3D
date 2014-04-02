/**
 * Contexto da aplicação de Jogo da Velha 3D (Tic Tac Toe 3D)
 * 
 */

package com.unibh.dcet.ccc.android.tictactoe3d;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.unibh.dcet.ccc.android.tictactoe3d.ai.ArtificialInteligence;

/**
 * Classe de controle principal do jogo
 * NOTA: é praticamente igual na maioria das aplicações ANDROID
 */
public class TTT3dActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// cria o jogo
		int dim = 4;
		ArtificialInteligence aiPlayer = new ArtificialInteligence(dim, TTT3dModel.TTT_O);
		TTT3dModel jogo = new TTT3dModel(aiPlayer);
		int startPiece = getIntent().getIntExtra("EXTRA_START_PLAYER", TTT3dModel.TTT_O);
		jogo.starts(startPiece);
		// angulos iniciais
		jogo.setAxisXAngle(-18);
		jogo.setAxisYAngle(-28);
		// Create our Preview view and set it as the content of our
		// Activity
		mGLSurfaceView = new TTT3dController(this, jogo);
		setContentView(mGLSurfaceView);
		mGLSurfaceView.requestFocus();
		mGLSurfaceView.setFocusableInTouchMode(true);
	}

	@Override
	protected void onResume() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// Ideally a game should implement onResume() and onPause()
		// to take appropriate action when the activity looses focus
		super.onPause();
		mGLSurfaceView.onPause();
	}

	private GLSurfaceView mGLSurfaceView;
}

