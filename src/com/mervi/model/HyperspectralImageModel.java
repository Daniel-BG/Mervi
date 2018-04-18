package com.mervi.model;

import java.util.Random;

public class HyperspectralImageModel extends AbstractHyperspectralImageModel {

	private int[][][] values;
	private int range = Integer.MAX_VALUE;
	private boolean available = false;
	
	
	{
		//this is used for colors that need to be between 0 and 1
		//hence the range force
		this.setRange(0x1 << 16);
	}

	
	public void setSize(int bands, int rows, int cols) {
		this.values = new int[bands][rows][cols];
		this.bandsProperty().set(bands);
		this.rowsProperty().set(rows);
		this.colsProperty().set(cols);
		this.available = true;
		this.modelChangedProperty().update();
	}
	
	private void setRange(int range) {
		this.range = range;
	}
	
	public void randomize(long seed) {
		Random r = new Random(seed);
		for (int i = 0; i < bandsProperty().intValue(); i++) {
			for (int j = 0; j < rowsProperty().intValue(); j++) {
				for (int k = 0; k < colsProperty().intValue(); k++) {
					this.unsafeSetValue(i, j, k, r.nextInt(range));
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
	private void safeSetValue(int band, int row, int col, int value) {
		checkBounds(band, row, col);
		checkValue(value);
		unsafeSetValue(band, row, col, value);
	}
	
	private void unsafeSetValue(int band, int row, int col, int value) {
		this.values[band][row][col] = value;
	}
	
	@Override
	public int getValue(int band, int row, int col) {
		checkBounds(band, row, col);
		return unsafeGetValue(band, row, col);
	}
	
	private int unsafeGetValue(int band, int row, int col) {
		return this.values[band][row][col];
	}
	
	private void checkBounds(int band, int row, int col) {
		if (band < 0 || row < 0 || col < 0 || 
				band >= bandsProperty().intValue() || row >= rowsProperty().intValue() || col >= colsProperty().intValue())
			throw new IllegalArgumentException("Index out of bounds");
	}
	
	private void checkValue(int value) {
		if (value < 0 || value > range)
			throw new IllegalArgumentException("Value outside of default range");
	}
	
	@Override
	public ReadOnlyMatrix doGetBand(int index) {
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
			public int get(int row, int col) {
				return getValue(index, row, col);
			}

			@Override
			public int range() {
				return range;
			}
		};
	}

	public boolean available() {
		return this.available;
	}

	@Override
	public int getRange() {
		return range;
	}
	
}
