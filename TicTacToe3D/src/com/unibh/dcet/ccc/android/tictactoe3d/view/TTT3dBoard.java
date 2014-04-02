/**
 * Classe de controle para renderização do tabuleiro 
 * do jogo da velha 3D
 *  
 * 
 */
package com.unibh.dcet.ccc.android.tictactoe3d.view;
import javax.microedition.khronos.opengles.GL10;

import com.unibh.dcet.ccc.android.tictactoe3d.TTT3dModel;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Dot;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Environment;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Line;

/**
 * @author dev
 *
 */
public class TTT3dBoard {
	
	// modelo (MVC) do jogo da velha
	TTT3dModel jogo;
	// unidade de medida mínima
	private float unidade = 1f;
	// fator de separação entre grades (camadas) 
	private float unidadeY = 1.5f;
	// dimnesão do jogo da velha
	private int dimensao; 
	// camadas do jogo em forma de grade (grid)
	private TTT3dGridShape grade;
	// linhas laterais da grade
	private TTT3dLine linha;
	// camadas do jogo em forma de plano quadrado (square)
	private TTT3dSquareShape plano;
	// graduação de cor de cada grade do jogo
	private float[] corGrade;
	// elementos visuais das peças
	//private TTT3dXShape2 pecaX;
	private TTT3dXShape pecaX;;
	private TTT3dOShape pecaO;
	// objeto que desenha as áreas para seleção pelo mouse
	public TTT3dViewSelector selecao;
	// usado para a seleção pelo mouse
	private int alturaTela;
	// destaca as camadas (compatível apenas com aparelhos mais novos)
	// ainda em teste!!!
	private boolean planoTransparente = false; 
	private boolean previewVictoryLines = true;
	
	public void setViewHeight(int height) {
		this.alturaTela = height;
	}
	
	public float getUnit() {
		return unidade;
	}

	public float getUnitY() {
		return unidadeY;
	}

	public void setUnitY(float unitY) {
		this.unidadeY = unitY;
	}

	public int getDimension() {
		return dimensao;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public TTT3dBoard(GL10 gl, TTT3dModel gameModel){
		this(gl, gameModel, 0.3f);
	}

	/**
	 *  
	 * 
	 */
	public TTT3dBoard(GL10 gl, TTT3dModel gameModel, float unit) {
		// verifica as pré-condições 
		if (unit <= 0) unit = 1;  // usa default para evitar exceção
		// associação com o modelo
		this.jogo = gameModel;
		// prepara as variáveis
		this.dimensao = gameModel.getDimension();
		this.unidade = 1f / ((float)dimensao - 1f);
		this.unidadeY = 1.5f * unidade;
		// o raio é metadade da unidade menos um pouco para espaçamento
		float raioX = this.unidade * 0.9f;
		float raioO = this.unidade * 0.32f;
		float larguraPeca = raioO / 5f;
		
		// cria a grade de peças (visual)  
		grade = new TTT3dGridShape(dimensao, unidade);
		// cria o plano das peças (visual)
		plano = new TTT3dSquareShape(dimensao, unidade);
		float[] branco = {1f, 1f, 1f, 1f};
		float[] azul = {0f, 0f, 1f, 1f};
		//
		linha = new TTT3dLine(dimensao - 1, unidadeY, azul, branco);
		// cria objeto de seleção pelo mouse
		selecao = new TTT3dViewSelector(dimensao, unidade, unidadeY);
		// cria as formas das peças do jogo
		//pecaX = new TTT3dXShape2(gl, larguraPeca, raioX);
		pecaX = new TTT3dXShape(gl, larguraPeca, raioX, 60, 60);
		float[] amarelo = {1f, 1f, 0f, 1f};
		pecaX.setColor(amarelo);
		pecaO = new TTT3dOShape(gl, raioO, larguraPeca, 60);
		float[] vermelho = {1f, 0f, 0f, 1f};
		pecaO.setColor(vermelho);
		// gera as cores das grades
		// divide as 255 graduações de cores pelo número de dimensões
		// para gerar uma graduação para as cores das grades
		float fatorCor = 255f / (float)this.dimensao;
		corGrade = new float[dimensao];
    	int i;
		for (i = 0; i < dimensao; i++) {
			// define a graduação para a cor das camadas
			corGrade[i] = ((i+1) * fatorCor)/255;
		}
	}
	
	
	/**
	 * Calcula a coordenada Y no modelo considerando 
	 * um fator de distanciamento 
	 * 
	 */
	//private float getY (Integer ord) {
	//	Float d = (((float)dimensao - 1) / 2);
	//	return  (ord - d) * unidade * factorDistanciaEntreCamadas;
	//}
	
	/**
	 * Calcula a largura do tebuleiro (para perspectiva) 
	 * 
	 */
	public float getWidth() {
		return (unidade * dimensao); 
	}

	/**
	 * Calcula a altura do tebuleiro (para perspectiva) 
	 * 
	 */
	public float getHeight() {
		return (unidadeY * (dimensao - 1)); 
	}

	public float getOffsetY() {
		return (unidadeY) / 2f;
	}

	public void testeO(GL10 gl) {
    	pecaO.draw(gl);
	}
	public void testeX(GL10 gl) {
    	pecaX.draw(gl);
	}
	
	private void drawLine(GL10 gl, float x, float y, float z) {
		gl.glPushMatrix();
			gl.glFrontFace(GL10.GL_CCW);
			gl.glTranslatef(x * unidade, y * unidadeY, z * unidade);
			linha.draw(gl);
		gl.glPopMatrix();
	}
	/**
	 * Renderiza o tabuleiro 
	 * 
	 */
    public void draw(GL10 gl) {

    	int x, y, z;
    	int type;
    	float px, py, pz;
    	
    	// desenha linhas laterais das grades
    	drawLine(gl, 0, 0, 0);
    	drawLine(gl, dimensao, 0, 0);
    	drawLine(gl, 0, 0, dimensao);
    	drawLine(gl, dimensao, 0, dimensao);
    	// desenha a sugestão de movimento
    	if (jogo.getWinner() == TTT3dModel.TTT__) {
    		Dot dot = jogo.getAiPlayer().getBestMoveOpponet();
    		if (dot != null) {
    			TTT3dDrawable peca;
    			if (jogo.getAiPlayer().getPiece() == Environment.TTT_O) {
    				peca = pecaX;
    			} else {
    				peca = pecaO;
    			}
    			gl.glPushMatrix();
    			gl.glTranslatef((dot.getCol() + 0.5f) * unidade, 
    					         dot.getPlane() * unidadeY, 
    					        (dot.getLin() + 0.5f) * unidade);
    			gl.glColor4f(0,1,0,1);
    			float[] cor = {1f, 1f, 1f, 1f};
    			peca.setColor(cor);
    			peca.draw(gl);
    			float[] cor2 = {1f, 1f, 0f, 1f};
    			peca.setColor(cor2);
    			gl.glPopMatrix();
    		}
    	}
    			
    	// varre todos os planos (camadas)
    	// dependendo da ordem de varredura,
    	// o efeito de transparência modifica de lado.
    	// REF: http://www.opengl.org/archives/resources/faq/technical/transparency.htm#blen0025
    	//for (y = (dimensao - 1); y >= 0; y--) {
		for (y = 0; y < dimensao; y++) {
			py = y * unidadeY;
			
			if (planoTransparente) {
				// posiciona e desenha o plano
				// desenha ambos os lados
				gl.glDisable(GL10.GL_CULL_FACE);  
				// define a função de transparência
				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				// define a cor com o fator alfa de transparência
				gl.glColor4f(corGrade[y], corGrade[y], 1f, 0.25f);
				// posiciona e desenha a grade
				gl.glPushMatrix();
					gl.glFrontFace(GL10.GL_CCW);
					gl.glTranslatef(0, py, 0);
					plano.draw(gl);
				gl.glPopMatrix();
				// restaura as configurações alteradas
				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ZERO);
				gl.glEnable(GL10.GL_CULL_FACE);  
			}

			// posiciona e desenha a grade
			gl.glPushMatrix();
			gl.glTranslatef(0, py, 0);
			gl.glColor4f(corGrade[y], corGrade[y], 1f, 1f);
			grade.draw(gl);
			gl.glPopMatrix();
			// verifica as posições do plano (camada) em busca de peças 
			for (x = 0; x < dimensao; x++) {
				for (z = 0; z < dimensao; z++) {
					// se existe peça na posição
					if ((type = jogo.getPiece(x, y, z)) > 0) {
						// posiciona para o desenho da peça
						gl.glPushMatrix();
						// soma (0.5 * unidade) para colocar no centro
						px = (x + 0.5f) * unidade; 
						pz = (z + 0.5f) * unidade;
						gl.glTranslatef(px, py, pz);
						// verifica o tipo e desenha
						if (type == TTT3dModel.TTT_O) {
							pecaO.draw(gl);
						} else if (type == TTT3dModel.TTT_X) {
							pecaX.draw(gl);
						}
						gl.glPopMatrix();
					}
				}
			}
		}
    	// se terminou, destaca  a linha de vitória
    	Line line = jogo.getVictoryLine();
    	if (line != null) {
    		float[] color = {1,1,1};
    		selecao.previewLine(gl, color, line);
    	}
    }
    
	/**
	 * retorna a posição do tabuleiro do jogo (MODEL)
	 * baseada na sua posição na tela (VIEW)
	 * qualquer resultado negativo (inx[i] < 0, para qualquer i) indica que o 
	 * o ponto da tela está fora do desenho do tabuleiro
	 */
    public int[] boardPosition(GL10 gl, int pX, int pY) {
    	// setAngles é opcional, desde que GL_CULL_FACE esteja desabilitada
    	// durante a seleção por posição da tela.
    	//selecao.setAngles(gl, jogo.getAxisXAngle(), jogo.getAxisYAngle());
    	int[] pos = selecao.pick(gl, pX, pY, alturaTela);
    	return pos;
    }
	
	/**
	 * Renderiza um preview durante a seleção
	 * 
	 */
    public void preview(GL10 gl) {
    	int[] pos = jogo.getPosition();
    	int type = jogo.getCurrentPlayer();
    	// verifica as pré-condições
    	if (pos.length == 3) {
    		if (jogo.isValidPosition(pos)) {
    			int tipoPeca = jogo.getPiece(pos);
    			// define a cor padrão do destaque das posições
    			float[] cor = {1,1,1};
    			// se não existe peça na posição
    			if (tipoPeca == TTT3dModel.TTT__) {
    				// desenha uma
					float px = (pos[0] + 0.5f) * unidade;
					float py = pos[1] * unidadeY;
					float pz = (pos[2] + 0.5f) * unidade;
					gl.glPushMatrix();
					gl.glTranslatef(px, py, pz);
					// verifica o tipo e desenha
					if (type == TTT3dModel.TTT_O) {
						pecaO.draw(gl);
					} else if (type == TTT3dModel.TTT_X) {
						pecaX.draw(gl);
					}
					gl.glPopMatrix();
    			} else {
    				// se já existe peça na posição, 
    				// usa a cor da peça para o destaque
					if (tipoPeca == TTT3dModel.TTT_O) {
						cor = pecaO.getColor();
					} else if (tipoPeca == TTT3dModel.TTT_X) {
						cor = pecaX.getColor();
					}
    			}
				// desenha o(s) detaque(s) da posição selecionada
    			if (previewVictoryLines) {
    				selecao.previewVictoryLines(gl, pos, cor, true, 
    						this.jogo.getAiPlayer().getEnvironment());
    			} else {
    				selecao.preview(gl, pos, cor, true);
    			}
    		}
    	}
    }
	
}
