package com.unibh.dcet.ccc.android.tictactoe3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.view.TTT3dBoard;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * Classe que desenha o (renderiza) o jogo da velha 3D
 * 
 */
public class TTT3dView implements GLSurfaceView.Renderer {
	// variáveis de controle do jogo
	private TTT3dBoard tabuleiro;
	public boolean iluminacao = true;
	TTT3dModel jogo;
	private int mouseX = 0;
	private int mouseY = 0;
	private float centroX;
	private float centroY;

	// variáveis de controle de luminosidade
	private FloatBuffer mLightAmbientBuffer;
	private FloatBuffer mLightDiffuseBuffer;
	private FloatBuffer mLightPositionBuffer;
	private FloatBuffer mEspecularidadeBuffer;

	
	public TTT3dView(TTT3dModel gameModel) {
		super();
		jogo = gameModel;
	}

	/**
	 * Método padrão de inicialização da renderização OpenGL ES
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		tabuleiro = new TTT3dBoard(gl, jogo);
		this.initLight(gl);
		this.configure(gl);
	}

	/**
	 * Método de configuração da renderização padrão
	 */
	public void configure(GL10 gl) {
		/*
		 * By default, OpenGL enables features that improve quality
		 * but reduce performance. One might want to tweak that
		 * especially on software renderer.
		 */
		gl.glDisable(GL10.GL_DITHER);

		/*
		 * Some one-time OpenGL initialization can be made here
		 * probably based on features of this particular context
		 */
		//gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
		//          GL10.GL_FASTEST);
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 

		gl.glClearColor(0,0,0,1.0f);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glEnable(GL10.GL_BLEND);

		// deve ativar a profundidade para evitar sobreposição de cores
		//gl.glEnable(GL10.GL_DEPTH_TEST);
		//gl.glClearDepthf(1.0f);        //Depth Buffer Setup
		gl.glDepthFunc(GL10.GL_LEQUAL);  //The Type Of Depth Testing To Do
		
		if (iluminacao) {
			gl.glEnable(GL10.GL_COLOR_MATERIAL);
			gl.glDisable(GL10.GL_NORMALIZE);
			gl.glEnable(GL10.GL_RESCALE_NORMAL);
			gl.glEnable(GL10.GL_LIGHTING);
			gl.glEnable(GL10.GL_LIGHT0);
			
		} else {
			gl.glDisable(GL10.GL_COLOR_MATERIAL);
			gl.glDisable(GL10.GL_RESCALE_NORMAL);
			gl.glDisable(GL10.GL_NORMALIZE);
			gl.glDisable(GL10.GL_LIGHTING);
			gl.glDisable(GL10.GL_LIGHT0);
		}
	
	}
	
	/**
	 * inicializa os buffers de luminosidade
	 * Deve ser chamada apenas na inicialização do objeto
	 * NOTA: Fica em escala de cinza no ANDROID...
	 */
	private void initLight(GL10 gl) {

		float[] mLightAmbient   = {0.3f, 0.3f, 0.3f, 1f};
		float[] mLightDiffuse   = {0.7f, 0.7f, 0.7f, 1f};
		float[] mLightPosition  = {1f, 1f, 1f, 0f};
		float[] mEspecularidade = {0.9f, 0.9f, 0.9f, 1f};

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(mLightAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightAmbientBuffer = byteBuf.asFloatBuffer();
		mLightAmbientBuffer.put(mLightAmbient);
		mLightAmbientBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(mLightDiffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightDiffuseBuffer = byteBuf.asFloatBuffer();
		mLightDiffuseBuffer.put(mLightDiffuse);
		mLightDiffuseBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(mLightPosition.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mLightPositionBuffer = byteBuf.asFloatBuffer();
		mLightPositionBuffer.put(mLightPosition);
		mLightPositionBuffer.position(0);        

		byteBuf = ByteBuffer.allocateDirect(mEspecularidade.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mEspecularidadeBuffer = byteBuf.asFloatBuffer();
		mEspecularidadeBuffer.put(mEspecularidade);
		mEspecularidadeBuffer.position(0);        

		// Define a refletância do material
		gl.glMaterialfv(GL10. GL_FRONT_AND_BACK,GL10.GL_SPECULAR, mEspecularidadeBuffer);
		// Define a concentração do brilho
		gl.glMaterialf(GL10. GL_FRONT_AND_BACK,GL10.GL_SHININESS,100f);

		//gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mLightDiffuseBuffer);
	    //gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mLightAmbientBuffer);

		// Ativa o uso da luz ambiente
		//gl.glLightModelfv(GL10.GL_LIGHT_MODEL_TWO_SIDE, mLightAmbientBuffer);
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, mLightAmbientBuffer);
	    
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, mLightAmbientBuffer);		//Setup The Ambient Light 
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, mLightDiffuseBuffer);		//Setup The Diffuse Light 
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, mLightPositionBuffer);	//Position The Light 
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, mEspecularidadeBuffer);	 
		
	}

	int frame = 0; 
	/**
	 * Atividade padrão de desenho de cada frame
	 */
	public void onDrawFrame(GL10 gl) {
		/*
		 * Limpa o frame buffer (se houver apenas um, limpa direto na tela).
		 */

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		/*
		 * Inicia a renderização e aplicando a rotação
		 */
		//
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//gl.glTranslatef(centroX, centroY, centroX);
		//gl.glRotatef(jogo.getAxisXAngle(), 1, 0, 0);
		//gl.glRotatef(jogo.getAxisYAngle(), 0, 1, 0);
		//tabuleiro.testeX(gl);
		
		gl.glLoadIdentity();

		gl.glTranslatef(centroX, centroY, centroX);
		gl.glRotatef(jogo.getAxisXAngle(), 1, 0, 0);
		gl.glRotatef(jogo.getAxisYAngle(), 0, 1, 0);

		// teste que desenho o ponto central do tabuleiro
		//TTT3dOShape c = new TTT3dOShape(0.03f, 35);
		//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glColor4f(1,1,1,1);
		//c.draw(gl);
		//gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glTranslatef(-centroX, -centroY, -centroX);

		// verifica posicionamento do mouse (se for necessário)
		if (jogo.needPosition()) {
			int[] posicaoTabuleiro = {-1,-1,-1};
			// faz a seleção
			posicaoTabuleiro = tabuleiro.boardPosition(gl, mouseX, mouseY);
			// restaura as configurações 
			this.configure(gl);
			jogo.setPosition(posicaoTabuleiro);
		}
		
		// inicia a transferência de dados das arestas para o acelerador gráfico
		// rendriza o tabuleiro
		tabuleiro.draw(gl);
		
		//Log.d("angulos","X="+jogo.getAxisXAngle()+" Y="+jogo.getAxisYAngle());
		// faz o preview, se for necessário
		if (jogo.needPreview()) {
			tabuleiro.preview(gl);
		}

	}

	/**
	 * Atividade padrão de mudança da tela (giro ou outra qualquer)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		// registra a altura da tela para seleção pelo mouse
		tabuleiro.setViewHeight(height);

		/*
		 * Set our projection matrix. This doesn't have to be done
		 * each time we draw, but usually a new projection needs to
		 * be set when the viewport is resized.
		 */
		gl.glMatrixMode(GL10.GL_PROJECTION);

		// ajusta a área de visualizaçãoo
		//float ratio = (float) width / height;
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

		// ajusta a projeção do modelo na tela
		float largura = tabuleiro.getWidth();
		float altura = tabuleiro.getHeight();
		// float meiaUnideY = tabuleiro.getOffsetY();
		centroX = largura / 2f;
		centroY = altura / 2f;
		gl.glOrthof(-centroX * 1.5f, centroX * 1.5f, 
				    -altura * 0.75f, altura * 0.75f, 
				    1f, largura * 1000f);
		GLU.gluLookAt(gl, centroX, centroY, -largura * 10, 
				          centroX, centroY, largura, 
				          0, 1, 0);
	}

	
	/**
	 * registra a posição do mouse
	 *  
	 */
	public void setMousePos(int x, int y) {
		mouseX = x;
		mouseY = y;
	}
	
	
	/**
	 * verifica ocorrência de erro na execução de comandos OpnGL
	 *  
	 */
    static void checkGLError(GL gl) {
        int error = ((GL10) gl).glGetError();
        if (error != GL10.GL_NO_ERROR) {
            throw new RuntimeException("GLError 0x" + Integer.toHexString(error));
        }
    }
	
}
