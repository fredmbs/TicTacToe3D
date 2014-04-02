package com.unibh.dcet.ccc.android.tictactoe3d.ai;

import java.util.List;

public class ArtificialInteligence {

	//private Judge judge;
	Environment environment;
	private int aiPiece; 
	private int opponentPiece;
	private int dimension;
	private Dot bestMoveOpponet = null;
	private int round = 0; 
	private int roundBestOpponetMove = -1; 
	

	public ArtificialInteligence(int dim, int piece) {

		environment = new Environment(dim, piece);
		this.dimension = dim;
		this.aiPiece = piece; 
		this.opponentPiece = (piece == Environment.TTT_O ? 
				Environment.TTT_X : Environment.TTT_O );
		//this.judge = new Judge();
	}

	public int getDimension() {
		return dimension;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public int getPiece() {
		return aiPiece;
	}

	public boolean isValidPosition(Dot dot) {
		if (environment.getPiece(dot) == Environment.TTT__) {
			return true;
		}
		return false;
	}	

	private Dot getBestMove(int piece) {
		int opponent = (piece == Environment.TTT_O ? 
				Environment.TTT_X : Environment.TTT_O );
		// vitória eminente do jogador
		Dot move = this.environment.eminentVictory(piece);
		if (move != null)
			return move;
		// vitória eminente do oponente 
		Dot opponetVictory = this.environment.eminentVictory(opponent);
		if (opponetVictory != null)
			return opponetVictory;
		// melhor jogada do oponente 
		int numEminentVictory = 0;
		Dot bestMoveOpponet = this.environment.maxChanceVictory(opponent);
		if (bestMoveOpponet != null) {
			for (Line l: ((DotAI)bestMoveOpponet).getPossibleLines()) {
				if (l.getHasPiece() == opponent && 
						l.getMarkedPosition() >= (dimension - 2))
					numEminentVictory++;
			}
			if (numEminentVictory >= 2)
				return bestMoveOpponet;
		}
		Dot resp = this.environment.maxChanceVictory(piece);
		if (resp == null)
			return aiDummyMove();
		return resp;
	}
	
	public Dot aiMove() {
		return getBestMove(aiPiece);
	}
	
	public Dot getBestMoveOpponet() {
		if (round != roundBestOpponetMove) {			
			bestMoveOpponet = this.getBestMove(opponentPiece);
			roundBestOpponetMove = round;
		}
		return bestMoveOpponet;
	}

	public DotAI aiDummyMove() {
		DotAI dot;
		this.environment.maxChanceVictory(aiPiece);
		for (int plane = 0; plane < dimension; plane++) 
			for (int lin = 0; lin < dimension; lin++) 
				for (int col = 0; col < dimension; col++) {
					dot = environment.getDotAI(plane, lin, col);
					if (dot.getPiece() == Environment.TTT__) {
						markAction(dot, aiPiece);
						return dot;
					}
				}
		return null;
	}

	public boolean markAction(Dot dot, int playerPiece) {
		if (isValidPosition(dot)) {
			round++;
			this.environment.markPosition(dot, playerPiece);
			return true;
		}
		return false;
	}

	public Line getVictoryLine(Dot lastChoosePosition) {
		/*
		 * nao e necessario varrer toda a matriz para verificar se existe um vencedor, sera feita a analise de uma vitoria a partir do ultimo ponto
		 * marcado no tabuleiro, se nenhuma das possiveis vitorias a partir do ultimo ponto marcado foi alcancada retorna null
		 */
		return this.environment.hasVictory(lastChoosePosition);
	}
    
    public Dot chooseBestAction() {
    	
		return null;
	}

	public int countVictoryPossibility(Dot dot) {

		return 0;
	}

	public List<Dot> findAdjacentsDots(Dot dot, int eixo) {

		return null;
	}
	
}
