/**
 * Classe que desenha as posições do jogo da velha 3D
 *  com cores diferentes para viabilizar a 
 *  seleção pelo mouse.
 *  
 *  Também possibilita a visulização durante a seleção
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.ai.DotAI;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Environment;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Line;

/**
 * Classe que faz a transformação entre um ponto na tela (VIEW)
 * e uma posição do tabuleiro (MODEL)
 * 
 *  Também faz o desenho do destaque durante a seleção.
 * 
 */
public class TTT3dViewSelector {

	private FloatBuffer vertexBuffer;  // Buffer for vertex-array
	private int nVertices;
	private int dimensao;
	private float unidade;
	private float unidadeY;
	// margem de erro na conversão de cores (infelizmente, valor empírico)
	static final private int margemErroCor = 10;
	// Máxima dimensão baseada na identificação de cores
	static final public int maxDim = (255 / (margemErroCor * 2));

	// mostra linhas diagonais
	private boolean showDiagonalLines = false;
	
	/**
	 * 
	 */
	public TTT3dViewSelector(int dimension, float unit, float unitY) {
		this.dimensao = dimension;
		this.unidade = unit;
		this.unidadeY = unitY;
		// 
		nVertices = 4*3;
		float[] vertices;
		vertices = new float[nVertices];

		float u = unidade;
		
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
	
	public void setShowDiagonalLines(boolean showDiagonalLines) {
		this.showDiagonalLines = showDiagonalLines;
	}

	/**
	 * Seleciona uma posição do modelo na projeção da imagem 
	 */
	public int[] pick(GL10 gl, int pX, int pY, int screenHeight) {

		// transforma a coordenada do buffer na coordenada da tela
		// pois a imagem do buffer openGL começa (posição 0,0) 
		// pelo lado inferior e a tela começa pelo lado superior
		pY = (int)(screenHeight - pY);
	
		// garante as configurações do OpenGL 
		// deve ativar a profundidade para evitar sobreposição de cores
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glClearDepthf(1.0f);          //Depth Buffer Setup
		gl.glDepthFunc(GL10.GL_LEQUAL);  //The Type Of Depth Testing To Do
		
        gl.glDisable(GL10.GL_BLEND);
        gl.glDisable(GL10.GL_DITHER);                   
        gl.glDisable(GL10.GL_FOG);
        gl.glDisable(GL10.GL_LIGHTING);                         
        gl.glDisable(GL10.GL_TEXTURE_2D);               
        gl.glShadeModel(GL10.GL_FLAT);

        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_COLOR_MATERIAL);
        
        // limpa o buffer
        gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Enable vertex-array and define its buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// inicia a trasnferencia dos buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // prepara as variáveis
		float red, green, blue;
		int dx, dy, dz;
		int fator = (int)(255f/(float)dimensao);
		for (dx = 0; dx < dimensao; dx++) {
			for (dz = 0; dz < dimensao; dz++) {
				for (dy = 0; dy < dimensao; dy++) {
					// calcula a cor para identificação única
					/*
					//int fatorBinario = 0xFFFF/dimensao;
					gl.glColor4x((dx+1) * fatorBinario, 
							     (dy+1) * fatorBinario, 
							     (dz+1) * fatorBinario, 0xFFFF);
					*/
					red   = ((float)(dx+1) / (float)dimensao);
					green = ((float)(dy+1) / (float)dimensao);
					blue  = ((float)(dz+1) / (float)dimensao);
					gl.glColor4f(red, green, blue, 1f);
					// desenha a posição na cor de identificação
					drawAt(gl, dx, dy, dz);
				}
			}
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// aloca o buffer para receber os dados das cores (apenas um pixel RGBA)
		ByteBuffer pixel = ByteBuffer.allocateDirect(4);
		pixel.order(ByteOrder.nativeOrder());
		// lê o buffer
		gl.glReadPixels((int)pX, (int)pY, 1, 1, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixel);
		// tansfere para um vetor de bytes
		byte[] b = new byte[4];
		pixel.get(b);
	    // faz a conversão para inteiro
		int[] pos = new int[3];
    	int i;
    	for (i = 0; i < 3; i++ ) {
    		// transforma byte para inteiro garantindo apenas os 8 primeros bits
    		pos[i] = (int)b[i] & 0xff;
    		// se o número for negativo, gera uma rodada de bits
    		if (pos[i] < 0) pos[i] += 256;
    		// retira a imprecisão das cores geradas (float->int)
    		pos[i] -= margemErroCor;
    	}
	    // converte para os índices de cores originais (índice -1 significa sem cor)
    	for (i = 0; i < 3; i++ ) {
    		// se a cor é zero ou menor
    		if (pos[i] <= 0) {
    			// não está no tabuleiro
    			pos[i] = -1; 
    		} else {
    			// obtém o índice da cor
        		pos[i] = (pos[i]/fator);
        		// se passou do limite, é a máxima dimensão
        		if (pos[i] >= dimensao)
        			pos[i] = (dimensao - 1);
    		}
    	}
		// limpa o buffer atual (usado apenas para seleção)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		return pos;
	}

	
	/**
	 * Desenha uma posição do tabuleiro 
	 */
	private void drawAt(GL10 gl, int x, int y, int z) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// salva as matrizes do openGL
		gl.glPushMatrix();  
		// translada para o ponto inicial do plano
		gl.glTranslatef((x * unidade), (y * unidadeY), (z * unidade));
		// Draw the primitives from the vertex-array directly
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, nVertices / 3);
		// restaura as matrizes
		gl.glPopMatrix();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	
	/**
	 * desenha uma diaginal no meio 
	 */
	public void drawDiaginalMeio(GL10 gl, int cx, int cz) {
		int dz = (cz == 1 ? 0: dimensao - 1);
		int dx = (cx == 1 ? 0: dimensao - 1);
		for (int dy = 0; dy < dimensao; dy++) {
			drawAt(gl, dx, dy, dz);
			dz += cz;
			dx += cx;
		}
	}
	
	/**
	 * desenha um destaque na posição do modelo com todas as possíveis linhas de vitória
	 */
	public void previewVictoryLines(GL10 gl, int[] pos, float[] color, boolean previewLines, Environment env) {
        // define o buffer de váertices
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// desenha ambos os lados
		gl.glDisable(GL10.GL_CULL_FACE);
		// define a função de transparência
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// define a cor com o fator alfa de transparência
		gl.glColor4f(color[0],color[1],color[2],0.5f);
		// se deve desenhar linhas de destaque
		if (previewLines) {
			gl.glColor4f(0,1,0,0.5f);
			DotAI dotBase = env.getDotAI(pos[1], pos[2], pos[0]);
			for (Line l: dotBase.getPossibleLines()) {
				for (DotAI dot: l.getLines()) {
					drawAt(gl, dot.getCol(), dot.getPlane(), dot.getLin());
				}
			}
		} else {
			// ou senão, detaca apenas a posição selecionada
			drawAt(gl, pos[0], pos[1], pos[2]);
		}
		
		// restaura a função de transparência
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);
	}
	
	/**
	 * desenha um destaque na linha informada
	 */
	public void previewLine(GL10 gl, float[] color, Line line) {
        // define o buffer de váertices
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// desenha ambos os lados
		gl.glDisable(GL10.GL_CULL_FACE);
		// define a função de transparência
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// define a cor com o fator alfa de transparência
		gl.glColor4f(color[0],color[1],color[2],0.5f);
		// se deve desenhar linhas de destaque
		for (DotAI dot: line.getLines()) {
			drawAt(gl, dot.getCol(), dot.getPlane(), dot.getLin());
		}
		// restaura a função de transparência
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);
	}

	/**
	 * desenha um destaque na posição do modelo 
	 */
	public void preview(GL10 gl, int[] pos, float[] color, boolean previewLines) {
        // define o buffer de váertices
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// desenha ambos os lados
		gl.glDisable(GL10.GL_CULL_FACE);
		// define a função de transparência
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// define a cor com o fator alfa de transparência
		gl.glColor4f(color[0],color[1],color[2],0.5f);
		// se deve desenhar linhas de destaque
		if (previewLines) {
			// varre as colunas da posição selecionada
			int dx;
			for (dx = 0; dx < dimensao; dx++) {
				drawAt(gl, dx, pos[1], pos[2]);
			}
			// varre as linhas da posição selecionada
			int dz;
			for (dz = 0; dz < dimensao; dz++) {
				drawAt(gl, pos[0], pos[1], dz);
			}
			// varre as camadas da posição selecionada
			int dy;
			for (dy = 0; dy < dimensao; dy++) {
				drawAt(gl, pos[0], dy, pos[2]);
			}
			if (showDiagonalLines) {
				// verifica as diagonais 
				int c = pos[0];
				int p = pos[1];
				int l = pos[2];
				boolean diagColCre = (p == l);
				boolean diagColDec = ((p + l) == (dimensao - 1));
				//boolean diagCol = diagColCre || diagColDec;
				boolean diagLinCre = (p == c);
				boolean diagLinDec = ((p + c) == (dimensao - 1));
				//boolean diagLin = diagLinCre || diagLinDec;
				boolean diagPlaCre = (l == c);
				boolean diagPlaDec = ((l + c)  == (dimensao - 1));
				//boolean diagPla = (diagPlaCre || diagPlaDec);
				boolean diagMeio1 = (diagLinCre && diagPlaCre); 
				boolean diagMeio2 = (diagLinCre && diagColDec); 
				boolean diagMeio3 = (diagLinDec && diagColCre); 
				boolean diagMeio4 = (diagColDec && diagPlaCre); 
				// desenhar eventual diagonal de coluna
				int d;
				gl.glColor4f(0,1,0,0.5f);
				if (diagColDec) {
					dz = dimensao;
					for (dy = 0; dy < dimensao; dy++) {
						dz--;
						drawAt(gl, pos[0], dy, dz);
					}
				}
				if (diagColCre) {
					for (d = 0; d < dimensao; d++) {
						drawAt(gl, pos[0], d, d);
					}
				}
				// verifica as diagonais de linha
				if (diagLinDec) {
					dx = dimensao;
					for (dy = 0; dy < dimensao; dy++) {
						dx--;
						drawAt(gl, dx, dy, pos[2]);
					}
				}
				if (diagLinCre) {
					for (d = 0; d < dimensao; d++) {
						drawAt(gl, d, d, pos[2]);
					}
				}
				// verifica as diagonais de plano
				if (diagPlaDec) {
					dx = dimensao;
					for (dz = 0; dz < dimensao; dz++) {
						dx--;
						drawAt(gl, dx, pos[1], dz);
					}
				}
				if (diagPlaCre) {
					for (d = 0; d < dimensao; d++) {
						drawAt(gl, d, pos[1], d);
					}
				}
				// verifica as diagonais de centro
				gl.glColor4f(0,1,0,0.5f);
				if (diagMeio1 && diagMeio2 && diagMeio3 && diagMeio4) {
					drawDiaginalMeio(gl,  1,  1);
					drawDiaginalMeio(gl, -1,  1);
					drawDiaginalMeio(gl,  1, -1);
					drawDiaginalMeio(gl, -1, -1);
				} else {
					if (diagMeio1 || diagMeio2 || diagMeio3 || diagMeio4) {
						int cz = (diagMeio1 || diagMeio3? 1: -1);
						int cx = (diagMeio1 || diagMeio2? 1: -1);
						dz = (cz == 1 ? 0: dimensao - 1);
						dx = (cx == 1 ? 0: dimensao - 1);
						for (dy = 0; dy < dimensao; dy++) {
							drawAt(gl, dx, dy, dz);
							dz += cz;
							dx += cx;
						}
					}
				}
			}
		} else {
			// ou senão, detaca apenas a posição selecionada
			drawAt(gl, pos[0], pos[1], pos[2]);
		}
		
		// restaura a função de transparência
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);
	}
	
	/**
	 * Informa os angulos de rotação para o seletor
	 * 
	 * Em testes, não usar em produção.
	 *  
	 */
	public void setAngles (GL10 gl, float aX, float aY) {
    	/* O clipping por CULL_FACE, na maioria das circunstâncias,
    	 *  otimiza o processamento gráfico. Esse teste depende do vetor normal
    	 *  do plano (face) que pode ser informado ou calculado.
    	 *  Se for calculado, seu vaor dependerá da orientação dos 
    	 *  vértices, se em direção horária (CW) ou anti horária (CCW). 
    	 * O teste desse método é experimental.
    	 *  Ele verifica a orientação do vetor normal das faces para 
    	 *  o caso do CULL_FACE estiver acionado quando for desenhar as 
    	 *  superfícies de seleção.
    	 * O problema é que glIsEnabled não está implementada 
    	 * no SDK versão 2.3.3! 
    	 * Assim, se este método for chamado, ele irá definir
    	 *  glFrontFace independente do GL_CULL_FACE estar ativado ou não.
    	 * Não retire os comentários relacionados com GL11!!!
    	 */
    	//if (gl instanceof GL11) {
    	//	GL11 gl11 = (GL11)gl;
    	//	if (gl11.glIsEnabled(GL10.GL_CULL_FACE))  {
    			aX = (aX % 360f);
    			aY = (aY % 360f);
    			if (aX < 0) aX += 360;
    			if (aY < 0) aY += 360;
    			boolean faceXFrente = ((aX >=   0) && (aX <  90)) || 
    					              ((aX >= 270) && (aX < 360));
    			boolean faceYFrente = ((aY >= 0) && (aY < 180));
    			if (( faceXFrente &&  faceYFrente) || 
    				(!faceXFrente && !faceYFrente)) {
    				gl.glFrontFace(GL10.GL_CCW);
    			} else {
    				gl.glFrontFace(GL10.GL_CW);
    			}
    		//}
    	//}
	}
	
}
