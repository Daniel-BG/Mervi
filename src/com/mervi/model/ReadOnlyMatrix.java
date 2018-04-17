package com.mervi.model;

public abstract class ReadOnlyMatrix {

	public abstract int getRows();
	
	public abstract int getCols();
	
	public abstract int get(int row, int col);
	
	public abstract int range();
	
	public boolean sizeEquals(ReadOnlyMatrix other) {
		return this.getRows() == other.getRows() 
				&& this.getCols() == other.getCols();
	}
}
