package com.unibh.dcet.ccc.android.tictactoe3d.ai;

import java.util.ArrayList;

public class Environment {

	private DotAI matrizIA[][][];
	private int dimension;
	private int pieceAI;
	private ArrayList<Line> possibleVictoryLines = null;

	public static final int TTT__ = 0;
	public static final int TTT_X = 1;
	public static final int TTT_O = 2;
	public static final int TTT_BOTH = 3;

	public Environment(int dimension, int pieceAI) {
		super();
		this.pieceAI = pieceAI;
		this.dimension = dimension;
		this.matrizIA = new DotAI[dimension][dimension][dimension];
		for (int plane = 0; plane < dimension; plane++) 
			for (int lin = 0; lin < dimension; lin++) 
				for (int col = 0; col < dimension; col++) 
					matrizIA[plane][lin][col] = new DotAI(plane, lin, col);
		createVictoryLines();
		for (int plane = 0; plane < dimension; plane++) 
			for (int lin = 0; lin < dimension; lin++) 
				for (int col = 0; col < dimension; col++) 
					matrizIA[plane][lin][col].checkChances(pieceAI);
	}

	public int getDimension() {
		return dimension;
	}

	public DotAI getDotAI(int plane, int lin, int col) {
		return matrizIA[plane][lin][col];
	}
	
	public DotAI getDotAI(Dot dot) {
		return matrizIA[dot.getPlane()][dot.getLin()][dot.getCol()];
	}
	
	public int getPiece(Dot dot) {
		return matrizIA[dot.getPlane()][dot.getLin()][dot.getCol()].getPiece();
	}

	public void markPosition(Dot dot, int piece) {
		this.matrizIA[dot.getPlane()][dot.getLin()][dot.getCol()].setPiece(piece, pieceAI);
	}

	public Line hasVictory(Dot dot) {
		int piece = getPiece(dot);
		for (Line linha: possibleVictoryLines) {
			if(linha.getHasPiece() == piece && linha.getMarkedPosition() == dimension)
				return linha;
		}
		return null;
	}

	private int maxMarkedPosition(int piece) {
		// escolhe a linha de maior chance
		int maxCountPerLine = 0;
		int countPiecePerLine;
		for (Line linha: possibleVictoryLines) {
			if(linha.getHasPiece() == piece) {
				countPiecePerLine = linha.getMarkedPosition();
				if (countPiecePerLine > maxCountPerLine) 
					maxCountPerLine = countPiecePerLine;
			}
		}
		return maxCountPerLine;
	}

	private int countMaxMarkedPosition(int piece, DotAI dot) {
		// escolhe a linha de maior chance dentre as linhas de possível vitória 
		// que passam pelo ponto (dot)
		int maxCountPerLine = 0;
		int countPiecePerLine;
		int result = 0;
		for (Line linha: dot.getPossibleLines()) {
			if(linha.getHasPiece() == piece) {
				countPiecePerLine = linha.getMarkedPosition();
				if (countPiecePerLine > maxCountPerLine) { 
					maxCountPerLine = countPiecePerLine;
					result = 1;
				} else if (countPiecePerLine == maxCountPerLine) {
					result++;
				}
			}
		}
		return result;
	}

	public Dot maxChanceVictory(int piece) {
		// escolhe a linha de maior chance
		int maxCountPerLine = maxMarkedPosition(piece);
		// dentre as linhas de maior chance
		// escolhe o ponto com maior chance de vitórias
		int maxCountMarkedDotPerLine = -1;
		int countMarkedDotPerLine;
		DotAI result = null;
		for (Line linha: possibleVictoryLines) {
			if((linha.getHasPiece() == piece || 
				linha.getHasPiece() == Environment.TTT__) && 
				maxCountPerLine == linha.getMarkedPosition()) 
			{
				for (DotAI dot: linha.getLines()) {
					if (dot.getPiece() == Environment.TTT__) {
						countMarkedDotPerLine = countMaxMarkedPosition(piece, dot);
						if (countMarkedDotPerLine >= maxCountMarkedDotPerLine) {
							result = dot;
							maxCountMarkedDotPerLine = countMarkedDotPerLine;
						}
					}
				}
			}
		}
		return result;
	}

	public Dot eminentVictory(int piece) {
		for (Line linha: possibleVictoryLines) {
			if(linha.getHasPiece() == piece &&
				linha.getMarkedPosition() == (this.dimension - 1)) 
			{
				return linha.getMaxLinesFreeDot();
			}
		}
		return null;
	}

	public boolean isDraw() {
		for (Line linha: possibleVictoryLines) {
			if(linha.getHasPiece() != Environment.TTT_BOTH)
				return false;
		}
		return true;
	}

	public void addDot(Line objLine, int p, int l, int c) {
		DotAI dot = getDotAI(p, l, c);
		objLine.addDot(dot);
		dot.addLine(objLine);
	}
	
	/**
	 * adiciona uma diaginal do meio 
	 */
	private void addDiagonal(int cLin, int cCol) {
		int dLin = (cLin == 1 ? 0: dimension - 1);
		int dCol = (cCol == 1 ? 0: dimension - 1);
		Line objLinha = new Line(dimension);
		possibleVictoryLines.add(objLinha);
		for (int dPlane = 0; dPlane < dimension; dPlane++) {
			addDot(objLinha, dPlane, dLin, dCol);
			dLin += cLin;
			dCol += cCol;
		}
	}
	
	private void createVictoryLines() {
		// cria a lista de linhas
		possibleVictoryLines = new ArrayList<Line>();
		
		// inicia as configurações 
		int plano; // plano
		int linha;   // linha
		int coluna;  // coluna
		
		// ----- Linhas -----
		// varre as colunas
		int dCol;
		for (plano = 0; plano < dimension; plano++) {
			for (linha = 0; linha < dimension; linha++) {
				Line objLinha = new Line(dimension);
				possibleVictoryLines.add(objLinha);
				for (dCol = 0; dCol < dimension; dCol++) {
					addDot(objLinha, plano, linha, dCol);
				}
			}
		}
		// varre as linhas 
		int dLine;
		for (plano = 0; plano < dimension; plano++) {
			for (coluna = 0; coluna < dimension; coluna++) {
				Line objLinha = new Line(dimension);
				possibleVictoryLines.add(objLinha);
				for (dLine = 0; dLine < dimension; dLine++) {
					addDot(objLinha, plano, dLine, coluna);
				}
			}
		}
		// varre as camadas (linhas de planos)
		int dPlane;
		for (linha = 0; linha < dimension; linha++) {
			for (coluna = 0; coluna < dimension; coluna++) {
				Line objLinha = new Line(dimension);
				possibleVictoryLines.add(objLinha);
				for (dPlane = 0; dPlane < dimension; dPlane++) {
					addDot(objLinha, dPlane, linha, coluna);
				}
			}
		}

		
		// ----- Diagonais -----

		// varre as diagonais de coluna
		int d;
		for (coluna = 0; coluna < dimension; coluna++) {
			Line objLinha = new Line(dimension);
			possibleVictoryLines.add(objLinha);
			Line objLinha2 = new Line(dimension);
			possibleVictoryLines.add(objLinha2);
			dLine = dimension;
			for (d = 0; d < dimension; d++) {
				dLine--;
				addDot(objLinha, d, dLine, coluna);
				addDot(objLinha2, d, d, coluna);
			}
		}
		
		// varre as diagonais de linha
		for (linha = 0; linha < dimension; linha++) {
			Line objLinha = new Line(dimension);
			possibleVictoryLines.add(objLinha);
			Line objLinha2 = new Line(dimension);
			possibleVictoryLines.add(objLinha2);
			dCol = dimension;
			for (d = 0; d < dimension; d++) {
				dCol--;
				addDot(objLinha, d, linha, dCol);
				addDot(objLinha2, d, linha, d);
			}
		}

		// varre as diagonais de plano
		for (plano = 0; plano < dimension; plano++) {
			Line objLinha = new Line(dimension);
			possibleVictoryLines.add(objLinha);
			Line objLinha2 = new Line(dimension);
			possibleVictoryLines.add(objLinha2);
			dCol = dimension;
			for (d = 0; d < dimension; d++) {
				dCol--;
				addDot(objLinha, plano, d, dCol);
				addDot(objLinha2, plano, d, d);
			}
		}

		// varre as diagonais de centro
		addDiagonal( 1,  1);
		addDiagonal(-1,  1);
		addDiagonal( 1, -1);
		addDiagonal(-1, -1);
	}
	
}
