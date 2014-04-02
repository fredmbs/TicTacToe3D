package com.unibh.dcet.ccc.android.tictactoe3d.ai;

public class Line {

    private DotAI[] lines;

    public DotAI[] getLines() {
		return lines;
	}

	private int numDots = 0;
    private int hasPiece = Environment.TTT__;
	private int markedPosition = 0;

    public int getHasPiece() {
		return hasPiece;
	}

    public int getMarkedPosition() {
		return markedPosition;
	}

	public Line(int dimension) {
    	this.lines  = new DotAI[dimension];
    }
    
    public void addDot (DotAI dot) {
    	if (numDots < lines.length) {
    		lines[numDots] = dot;
    		numDots++;
    	}
    }
    
    public int checkLine() {
    	int piece;
    	hasPiece = Environment.TTT__;
    	markedPosition = 0;
    	for (DotAI dotAI: lines) {
    		piece = dotAI.getPiece();
    		if (piece != Environment.TTT__) {
    			markedPosition++;
    			if (hasPiece == Environment.TTT__) {
    				hasPiece = piece;
    			} else if (hasPiece != piece) {
    				hasPiece = Environment.TTT_BOTH;
    			}
    		}
    	}
    	return hasPiece;
    }

    public DotAI getMaxLinesFreeDot() {
    	int maxCountLine = -1;
    	int countLine;
    	DotAI result = null;
    	for (DotAI dotAI: lines) {
    		if (dotAI.getPiece() == Environment.TTT__) {
    			countLine = dotAI.getLinesChanceCount();
    			if (countLine > maxCountLine) {
    				result = dotAI;
    				maxCountLine = countLine; 
    			}
    		}
    	}
    	return result;
    }
}
