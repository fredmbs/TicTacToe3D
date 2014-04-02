package com.unibh.dcet.ccc.android.tictactoe3d;

import android.util.Log;

import com.unibh.dcet.ccc.android.tictactoe3d.ai.ArtificialInteligence;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Dot;
import com.unibh.dcet.ccc.android.tictactoe3d.ai.Line;
import com.unibh.dcet.ccc.android.tictactoe3d.view.TTT3dViewSelector;

/**
 * Classe de modelo do jogo da velha 3D
 * 
 */
public class TTT3dModel {

	int[][][] pecas;
	// índices das peças
	public final static int TTT__ = 0;
	public final static int TTT_X = 1;
	public final static int TTT_O = 2;
	public final static int TTT_DRAW = 3;
	
	// dimnesão do jogo da velha
	private int dimensao; 

	// inteligência artificial
	private ArtificialInteligence aiPlayer;
	private int pecaIA = TTT3dModel.TTT_O; 
	
	// estados do jogo 
	// ver diagrama de estados
	static final public int STARTING = -1;
	static final public int WAITING = 0;
	static final public int PICK = 1;
	static final public int ROTATION = 2;
	static final public int SELECT = 3;
	static final public int PLAY = 4;
	static final public int AI_PLAYING = 5;
	static final public int GAME_OVER = 6;
	private int estado = STARTING; 
	
	// 
	private int winner = TTT3dModel.TTT__;
	private Line victoryLine = null;
	
	// outros controles
	private int atualPeca = TTT3dModel.TTT_O;
	private float rotacaoEixoX;
	private float rotacaoEixoY;
	private int[] posicaoTabuleiro = {-1,-1,-1};
	
	public TTT3dModel(ArtificialInteligence aiPlayer) 
	{
		super();
		// verifica as pré-condições
		// o máximo de dimensões é definido pelo objeto seletor
		if (dimensao < 3 || dimensao > TTT3dViewSelector.maxDim)
			dimensao = 4;  // usa o default para evitar exeção
		this.aiPlayer = aiPlayer;
		this.pecaIA = aiPlayer.getPiece();	
		this.dimensao = aiPlayer.getDimension();
		// inicia a matriz do jogo
		pecas = new int[dimensao][dimensao][dimensao];
    	int x, y, z;
		for (x = 0; x < dimensao; x++) {
			for (y = 0; y < dimensao; y++) {
				for (z = 0; z < dimensao; z++) {
					pecas[x][y][z] = TTT3dModel.TTT__;
				}
			}
		}
	}

	public int getWinner() {
		return winner;
	}

	public ArtificialInteligence getAiPlayer() {
		return aiPlayer;
	}

	public Line getVictoryLine() {
		return victoryLine;
	}

	public boolean boardIsFull() {
    	int x, y, z;
		for (x = 0; x < dimensao; x++) {
			for (y = 0; y < dimensao; y++) {
				for (z = 0; z < dimensao; z++) {
					if (pecas[x][y][z] == TTT3dModel.TTT__) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public int getDimension() {
		return dimensao;
	}
	
	public float getAxisXAngle() {
		return rotacaoEixoX;
	}
	
	public void setAxisXAngle(float angle) {
		rotacaoEixoX = angle;
	}
	
	public void addAxisXAngle(float angle) {
		rotacaoEixoX += angle;
	}
	
	public float getAxisYAngle() {
		return rotacaoEixoY;
	}
	
	public void setAxisYAngle(float angle) {
		rotacaoEixoY = angle;
	}
	
	public void addAxisYAngle(float angle) {
		rotacaoEixoY += angle;
	}
	
	public boolean setPlayMode() {
		if (estado == SELECT || estado == PICK) {
			estado = PLAY;
			return true;
		} else if (estado == TTT3dModel.ROTATION) {
			if (winner == TTT3dModel.TTT__)
				estado = TTT3dModel.WAITING;
			else
				estado = TTT3dModel.GAME_OVER;
			return true;
		}
		return false;
	}
	
	public void setPickMode() {
		if (estado == TTT3dModel.WAITING)
			estado = PICK;
		else if (estado == TTT3dModel.GAME_OVER)
			estado = ROTATION;
	}
	
	public int getMode() {
		return estado;
	}
	
	public int getCurrentPlayer() {
		return atualPeca;
	}

	public int getPiece(int x, int y, int z) {
		if (x >= 0 && x < dimensao &&
    		y >= 0 && y < dimensao &&
    		z >= 0 && z < dimensao) {
			return pecas[x][y][z];
		}
		return -1;
	}
	
	public int getPiece(int[] pos) {
		if (this.isValidPosition(pos)) {
			return pecas[pos[0]][pos[1]][pos[2]];
		}
		return -1;
	}

	public void starts(int piece) {
		if (estado == TTT3dModel.STARTING) {
			atualPeca = piece;
			estado = ((piece == pecaIA) ? TTT3dModel.AI_PLAYING: TTT3dModel.WAITING); 
		}
		if (estado == TTT3dModel.AI_PLAYING) {
			Dot aiMove = aiPlayer.aiMove();
			if (!play(aiMove.getCol(), aiMove.getPlane(), aiMove.getLin())) {
				Log.e("Stupid AI", "AI bad move!");
			}
		}
	}

	/**
	 * realiza uma jogada de uma peça em uma posição  
	 * 
	 */
    public boolean play(int x, int y, int z) {
    	if (estado == TTT3dModel.PLAY || estado == TTT3dModel.AI_PLAYING) {
    		if (x >= 0 && x < dimensao &&
   				y >= 0 && y < dimensao &&
   				z >= 0 && z < dimensao) {
    			if (pecas[x][y][z] == TTT3dModel.TTT__) {
    				pecas[x][y][z] = atualPeca;
    				Dot move = new Dot(y, z, x);
    				if (!aiPlayer.markAction(move, atualPeca)) {
    					Log.e("Stupid AI", "AI lost control!");
    				}
    				victoryLine = aiPlayer.getVictoryLine(move);
    				if (victoryLine != null) {
    					estado = TTT3dModel.GAME_OVER;
    					winner = atualPeca;
    					if (winner == TTT3dModel.TTT_O)
    						Log.d("Game Over", "O Victory!");
    					else
    						Log.d("Game Over", "X Victory!");
    					return true;
    				}
    				if (aiPlayer.getEnvironment().isDraw()){
    					estado = TTT3dModel.GAME_OVER;
    					winner = TTT3dModel.TTT_DRAW;
    					Log.d("Game Over", "DRAW!");;
    					return true;
    				}
    				if (boardIsFull()) {
    					estado = TTT3dModel.GAME_OVER;
    					winner = TTT3dModel.TTT_DRAW;
    					return true;
    				}
    				atualPeca = (atualPeca == TTT3dModel.TTT_O ? 
    						TTT3dModel.TTT_X : TTT3dModel.TTT_O );
    				if (atualPeca == pecaIA) {
    					estado = TTT3dModel.AI_PLAYING;
    					Dot aiMove = aiPlayer.aiMove();
    					if (!play(aiMove.getCol(), aiMove.getPlane(), aiMove.getLin())) {
    						Log.e("Stupid AI", "AI bad move!");
    					}
    				}
    				if (estado != TTT3dModel.GAME_OVER)
    					estado = TTT3dModel.WAITING;
    				return true;
    			}
    		}
   			estado = TTT3dModel.WAITING;
    	}
    	return false;
    }
		
	/**
	 * Verifica se a posição é válida para o tabuleiro
	 * 
	 */
    public boolean isValidPosition(int[] pos) {
    	if (pos.length == 3) {
    		for (int i: pos)
    			if ((i < 0) || (i >= dimensao))
    				return false;
    		return true;
    	}
    	return false;
    }
    
	/**
	 * realiza uma jogada de uma peça em uma posição (usando vetor)  
	 * 
	 */
    public boolean play(int[] pos) {
    	return play(pos[0],pos[1],pos[2]);
    }

	/**
	 * Informa uma área da tela que foi selecionada (click ou MOUSE_DOWN)  
	 * 
	 */
    public void pick(int[] pos) {
    	// se o jogo ainda está ativo
    	if (estado != TTT3dModel.GAME_OVER) {
    		// se o ponto estiver no tabuleiro
    		if (isValidPosition(pos)) {
    			// se já não está no modo jogar
    			if (estado != TTT3dModel.PLAY)
    				// passa para o modo de seleção
    				estado = TTT3dModel.SELECT;
    			return;
    		}
    	};
    	// no último caso, rotaciona
    	estado = TTT3dModel.ROTATION;
    }

	/**
	 * Informa a necessidade de receber uma posição da tela  
	 * 
	 */
    public boolean needPosition() {
    	return (estado == TTT3dModel.PICK) ||
    		   (estado == TTT3dModel.PLAY) ||
    		   (estado == TTT3dModel.SELECT);
    }
    
	/**
	 * Recebe a informação da posição da tela selecionada  
	 * 
	 */
    public void setPosition(int[] pos) {
    	posicaoTabuleiro[0] = pos[0];
    	posicaoTabuleiro[1] = pos[1];
    	posicaoTabuleiro[2] = pos[2];
    	switch (estado) {
		case TTT3dModel.PICK:
		case TTT3dModel.GAME_OVER:
			pick(pos);
			break;
		case TTT3dModel.PLAY:
			play(pos);
			break;
		}; 
    }
    
	/**
	 * Informa se existe necessidade de desenhar um preview  
	 * 
	 */
    public boolean needPreview() {
		if (estado == TTT3dModel.SELECT) {
			return isValidPosition(posicaoTabuleiro);
		}
		return false;
    }
    
	/**
	 * Informa a última posição selecionada  
	 * 
	 */
    public int[] getPosition() {
    	int[] r = new int[3];
    	r[0] = posicaoTabuleiro[0];
    	r[1] = posicaoTabuleiro[1];
    	r[2] = posicaoTabuleiro[2];
    	return r;
    }

}
