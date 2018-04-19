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
			val /= (double) this.matrix.getMaxMinDiff();
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

	

	
	public int getMax() {
		return this.matrix.getMax();
	}
	
	public int getMin() {
		return this.matrix.getMin();
	}
	
	public int getMaxMinDiff() {
		return this.matrix.getMaxMinDiff();
	}
	
	public Image getImage() {
		return this.matrix.getImage();
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
