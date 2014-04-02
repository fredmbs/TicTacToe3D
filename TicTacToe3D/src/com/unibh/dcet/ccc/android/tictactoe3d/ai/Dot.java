package com.unibh.dcet.ccc.android.tictactoe3d.ai;

public class Dot {

	private int col;
	private int plane;
	private int lin;

	public Dot(int plane, int line, int column) {
		this.plane = plane;
		this.lin = line;
		this.col = column;
	}

	public int getPlane() {
		return plane;
	}

	public int getLin() {
		return lin;
	}

	public int getCol() {
		return col;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + plane;
		result = prime * result + lin;
		result = prime * result + col;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dot other = (Dot) obj;
		if (plane != other.plane)
			return false;
		if (lin != other.lin)
			return false;
		if (col != other.col)
			return false;
		return true;
	}

}
