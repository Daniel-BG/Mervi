package com.mervi.model;

public abstract class ReadOnlyMatrix {

	public abstract int getRows();
	
	public abstract int getCols();
	
	public abstract float get(int row, int col);
	
	public boolean sizeEquals(ReadOnlyMatrix other) {
		return this.getRows() == other.getRows() 
				&& this.getCols() == other.getCols();
	}
}
