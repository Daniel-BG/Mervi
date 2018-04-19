package com.mervi.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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
	private Integer maxVal;
	private Integer minVal;
	private Integer maxMinDiff;
	
	
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
	
	private void recalculateMinMax() {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				int data = this.get(row, col);
				if (data > max)
					max = data;
				if (data < min)
					min = data;
			}
		}
		maxVal = max;
		minVal = min;
		maxMinDiff = max - min;
	}
	
	public int getMax() {
		if (maxVal == null)
			recalculateMinMax();
		return maxVal;
	}
	
	public int getMin() {
		if (minVal == null) 
			recalculateMinMax();
		return minVal;
	}
	
	public int getMaxMinDiff() {
		if (maxMinDiff == null)
			recalculateMinMax();
		return maxMinDiff;
	}
	
	public double getDbl(int row, int col) {
		double val = get(row, col);
		val -= this.getMin();
		val /= (double) getMaxMinDiff();
		return val;
	}
	

	/** Precalculated image for displaying purposes */
	private Image bwImage;
	
	public Image getImage() {
		if (this.bwImage != null)
			return this.bwImage;
		
		WritableImage wi = new WritableImage(getCols(), getRows());
		PixelWriter pw = wi.getPixelWriter();
				
		for (int col = 0; col < getCols(); col++) {
			for (int row = 0; row < getRows(); row++) {
				pw.setColor(col, row, new Color(this.getDbl(row, col), this.getDbl(row, col), this.getDbl(row, col), 1));
			}
		}
		
		return this.bwImage = wi;
	}
	

}
