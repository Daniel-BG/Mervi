package com.mervi.model;

public abstract class ReadOnlyMatrix {
	
	

	public abstract int getRows();
	
	public abstract int getCols();
	
	public abstract int get(int row, int col);
	
	public abstract int range();
	
	public boolean sizeEquals(ReadOnlyMatrix other) {
		return this.getRows() == other.getRows() 
				&& this.getCols() == other.getCols()
				&& this.range() == other.range();
	}
	
	/** precalculated values for metrics */
	private Double average;
	private Double variance;
	
	public double variance() {
		if (variance != null)
			return variance;
		
		double localavg = this.average();

		double acc = 0;
		for (int j = 0; j < getRows(); j++) {
			for (int k = 0; k < getCols(); k++) {
				double val = (double) this.get(j, k) - localavg;
				acc += val * val;
			}
		}
		
		return variance = acc / ((double) getRows() * getCols());
	}
	
	public double average() {
		if (average != null)
			return average;
		
		double acc = 0;
		for (int j = 0; j < getRows(); j++) {
			for (int k = 0; k < getCols(); k++) {
				acc += (double) this.get(j, k);
			}
		}

		return average = acc / ((double) getRows() * getCols());
	}
}
