package com.mervi.model;

import java.util.Random;

import com.mervi.control.ObjectChangedProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HyperspectralImageModel {

	private float[][][] values;
	private float low = Float.MIN_VALUE, high = Float.MAX_VALUE;
	
	private IntegerProperty bands, rows, cols;
	private ObjectChangedProperty modelChanged; //updated when the values change
	
	{
		//this is used for colors that need to be between 0 and 1
		//hence the range force
		this.setRange(0.0f, 1.0f);
		this.bands = new SimpleIntegerProperty();
		this.rows = new SimpleIntegerProperty();
		this.cols = new SimpleIntegerProperty();
	
		this.modelChanged = new ObjectChangedProperty();
	}

	
	public void setSize(int bands, int rows, int cols) {
		this.values = new float[bands][rows][cols];
		this.bands.set(bands);
		this.rows.set(rows);
		this.cols.set(cols);
		this.modelChanged.update();
	}
	
	private void setRange(float low, float high) {
		this.low = low;
		this.high = high;
	}
	
	public void randomize(long seed) {
		Random r = new Random(seed);
		for (int i = 0; i < bands.intValue(); i++) {
			for (int j = 0; j < rows.intValue(); j++) {
				for (int k = 0; k < cols.intValue(); k++) {
					this.unsafeSetValue(i, j, k, r.nextFloat());
				}
			}
		}
		this.modelChanged.update();
	}
	
	/**
	 * Keep this private so that modelChanged does not have to update here
	 * Instead, update the whole matrix in a new function, using this only
	 * as a helper. Same goes for unsafeSetValue
	 */
	private void safeSetValue(int band, int row, int col, float value) {
		checkBounds(band, row, col);
		checkValue(value);
		unsafeSetValue(band, row, col, value);
	}
	
	private void unsafeSetValue(int band, int row, int col, float value) {
		this.values[band][row][col] = value;
	}
	
	public float safeGetValue(int band, int row, int col) {
		checkBounds(band, row, col);
		return unsafeGetValue(band, row, col);
	}
	
	private float unsafeGetValue(int band, int row, int col) {
		return this.values[band][row][col];
	}
	
	private void checkBounds(int band, int row, int col) {
		if (band < 0 || row < 0 || col < 0 || 
				band >= bands.intValue() || row >= rows.intValue() || col >= cols.intValue())
			throw new IllegalArgumentException("Index out of bounds");
	}
	
	private void checkValue(float value) {
		if (value < low || value > high)
			throw new IllegalArgumentException("Value outside of default range");
	}
	
	
	public IntegerProperty rowsProperty() {
		return this.rows;
	}
	
	public IntegerProperty colsProperty() {
		return this.cols;
	}
	
	public IntegerProperty bandsProperty() {
		return this.bands;
	}
	
	public ObjectChangedProperty modelChangedProperty() {
		return this.modelChanged;
	}
	
	public float[][] getBand(int index) {
		return this.values[index];
	}

	public boolean available() {
		return values != null;
	}
	
}
