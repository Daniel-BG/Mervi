package com.mervi.model;

import java.util.Random;

public class HyperspectralImageModel extends AbstractHyperspectralImageModel {

	private float[][][] values;
	private float low = Float.MIN_VALUE, high = Float.MAX_VALUE;
	
	
	{
		//this is used for colors that need to be between 0 and 1
		//hence the range force
		this.setRange(0.0f, 1.0f);
	}

	
	public void setSize(int bands, int rows, int cols) {
		this.values = new float[bands][rows][cols];
		this.bandsProperty().set(bands);
		this.rowsProperty().set(rows);
		this.colsProperty().set(cols);
		this.modelChangedProperty().update();
	}
	
	private void setRange(float low, float high) {
		this.low = low;
		this.high = high;
	}
	
	public void randomize(long seed) {
		Random r = new Random(seed);
		for (int i = 0; i < bandsProperty().intValue(); i++) {
			for (int j = 0; j < rowsProperty().intValue(); j++) {
				for (int k = 0; k < colsProperty().intValue(); k++) {
					this.unsafeSetValue(i, j, k, r.nextFloat());
				}
			}
		}
		modelChangedProperty().update();
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
	
	@Override
	public float getValue(int band, int row, int col) {
		checkBounds(band, row, col);
		return unsafeGetValue(band, row, col);
	}
	
	private float unsafeGetValue(int band, int row, int col) {
		return this.values[band][row][col];
	}
	
	private void checkBounds(int band, int row, int col) {
		if (band < 0 || row < 0 || col < 0 || 
				band >= bandsProperty().intValue() || row >= rowsProperty().intValue() || col >= colsProperty().intValue())
			throw new IllegalArgumentException("Index out of bounds");
	}
	
	private void checkValue(float value) {
		if (value < low || value > high)
			throw new IllegalArgumentException("Value outside of default range");
	}
	
	@Override
	public ReadOnlyMatrix getBand(int index) {
		return new ReadOnlyMatrix() {
			@Override
			public int getRows() {
				return rowsProperty().intValue();
			}

			@Override
			public int getCols() {
				return colsProperty().intValue();
			}

			@Override
			public float get(int row, int col) {
				return getValue(index, row, col);
			}
		};
	}

	@Override
	public boolean available() {
		return values != null;
	}
	
}
