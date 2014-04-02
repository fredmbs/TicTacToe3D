package com.unibh.dcet.ccc.android.tictactoe3d.ai;

import java.util.ArrayList;

public class DotAI extends Dot {

	private ArrayList<Line> possibleVictoryLines = null;
	private int piece;
	private int linesChanceCount = 0;

	public int getLinesChanceCount() {
		return linesChanceCount;
	}

	public DotAI(int plane, int lin, int col) {
		super(plane, lin, col);
		piece = Environment.TTT__;
		// cria a lista de linhas vazia
		possibleVictoryLines = new ArrayList<Line>(); 
	}

	public ArrayList<Line> getPossibleLines() {
		return possibleVictoryLines;
	}

	public int getPiece() {
		return piece;
	}

	public void checkChances(int pieceAI) {
		//varrer as poosíveis linhas de vitória e atualizar a probabilidade
		linesChanceCount = 0;
		int linePiece;
		for (Line l: possibleVictoryLines) {
			linePiece = l.checkLine();
			if (linePiece == Environment.TTT__ || linePiece == pieceAI)
				linesChanceCount++;
		}
	}

	public void setPiece(int piece, int pieceAI) {
		this.piece = piece;
		checkChances(pieceAI);
	}

	public void addLine(Line line) {
		possibleVictoryLines.add(line);
	}
	
}
