package com.mervi.view;

import com.mervi.control.ObjectChangedProperty;
import com.mervi.model.ReadOnlyMatrix;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class NumberMatrix  {
	
	ReadOnlyMatrix matrix;
	
	private final IntegerProperty cols = new SimpleIntegerProperty(0);
	private final IntegerProperty rows = new SimpleIntegerProperty(0);
	private final ObjectChangedProperty change = new ObjectChangedProperty();
	
	public void set(ReadOnlyMatrix m) {
		this.matrix = m;
		this.maxVal = null;
		this.minVal = null;
		this.maxMinDiff = null;
		this.bwImage = null;
		this.cols.set(m.getCols());
		this.rows.set(m.getRows());
		this.change.update();
		
	}
	
	private boolean scaled = true;
	
	public void setScaled(boolean scaled) {
		this.scaled = scaled;
	}
	
	public double get(int row, int col) {
		if (!scaled)
			return ((double) this.matrix.get(row, col)) / ((double) this.matrix.range());
		else {
			double val = this.matrix.get(row, col);
			val -= this.getMin();
			val /= (double) this.maxMinDiff;
			return val;
		}
			
	}
	
	public IntegerProperty rowsProperty() {
		return this.rows;
	}
	
	public IntegerProperty colsProperty() {
		return this.cols;
	}
	
	public ObjectChangedProperty changedProperty() {
		return this.change;
	}
	
	
	
	/**
	 * @param other
	 * @return true if both matrices are of the same size
	 */
	public boolean sizeEquals(NumberMatrix other) {		
		return this.rowsProperty().intValue() == other.rowsProperty().intValue()
				&& this.colsProperty().intValue() == other.colsProperty().intValue();
	}

	
	private Integer maxVal;
	private Integer minVal;
	private Integer maxMinDiff;
	private Image bwImage; //cached image for this number matrix
	
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
	
	private void recalculateMinMax() {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int row = 0; row < this.rowsProperty().intValue(); row++) {
			for (int col = 0; col < this.colsProperty().intValue(); col++) {
				int data = this.matrix.get(row, col);
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

	
	public Image getImage() {
		if (this.bwImage != null)
			return this.bwImage;
		
		WritableImage wi = new WritableImage(cols.intValue(), rows.intValue());
		PixelWriter pw = wi.getPixelWriter();
				
		for (int col = 0; col < cols.intValue(); col++) {
			for (int row = 0; row < rows.intValue(); row++) {
				pw.setColor(col, row, new Color(this.get(row, col), this.get(row, col), this.get(row, col), 1));
			}
		}
		
		return this.bwImage = wi;
	}
	
}

/**
public void setAll(Number[][] matrix) {
	this.matrix = new Number[matrix.length][matrix[0].length];
	for (int i = 0; i < matrix.length; i++) {
		for (int j = 0; j < matrix[0].length; j++) {
			this.matrix[i][j] = new Double(matrix[i][j].doubleValue()); 
		}
	}
	this.lastUpdatedCol = -1;
	this.lastUpdatedRow = -1;
	this.cols.set(matrix.length);
	this.rows.set(matrix[0].length);
	this.change.update();
}

public void setAll(ReadOnlyMatrix band) {
	this.matrix = new Number[band.getRows()][band.getCols()];
	for (int i = 0; i < band.getRows(); i++) {
		for (int j = 0; j < band.getCols(); j++) {
			this.matrix[i][j] = new Double(band.get(i, j)); 
		}
	}
	this.lastUpdatedCol = -1;
	this.lastUpdatedRow = -1;
	this.cols.set(band.getRows());
	this.rows.set(band.getCols());
	this.change.update();
}

public void setOne(int row, int col, Number val) {
	this.matrix[row][col] = new Double(val.doubleValue());
	this.lastUpdatedCol = col;
	this.lastUpdatedRow = row;
	this.change.update();
}

private int lastUpdatedRow, lastUpdatedCol;
	
public int lastUpdatedRow() {
	return this.lastUpdatedRow;
}


public int lastUpdatedCol() {
	return this.lastUpdatedCol;
}
	
*/
