package com.mervi.model;

import java.util.Random;

import com.jypec.img.HyperspectralImage;
import com.jypec.img.HyperspectralImageData;

public class HyperspectralImageModel extends AbstractHyperspectralImageModel {

	private int[][][] values;
	private int range = Integer.MAX_VALUE;
	private boolean available = false;
	
	
	{
		//this is used for colors that need to be between 0 and 1
		//hence the range force
		this.setRange(0x1 << 16);
	}
	
	@Override
	protected void doSetSize() {
		this.values = new int[getBands()][getRows()][getCols()];
		this.available = true;
	}

	private void setRange(int range) {
		this.range = range;
	}
	
	public void randomize(long seed) {
		Random r = new Random(seed);
		for (int i = 0; i < getBands(); i++) {
			for (int j = 0; j < getRows(); j++) {
				for (int k = 0; k < getCols(); k++) {
					this.unsafeSetValue(i, j, k, r.nextInt(range));
				}
			}
		}
		modelChangedProperty().update();
	}
	
	
	public void setFromImage(HyperspectralImage image) {
		HyperspectralImageData data = image.getData();
		this.setSize(data.getNumberOfBands(), data.getNumberOfLines(), data.getNumberOfSamples(), false);
		for (int band = 0; band < getBands(); band++) {
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col < getCols(); col++) {
					this.values[band][row][col] = data.getDataAt(band, row, col);
				}
			}
		}
		this.modelChangedProperty().update();
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
				band >= getBands() || row >= getRows() || col >= getCols())
			throw new IllegalArgumentException("Index out of bounds");
	}
	
	private void checkValue(int value) {
		if (value < 0 || value > range)
			throw new IllegalArgumentException("Value outside of default range");
	}
	
	@Override
	public HyperspectralBandModel doGetBand(int index) {
		return new HyperspectralBandModel() {
			@Override
			public int getRows() {
				return HyperspectralImageModel.this.getRows();
			}

			@Override
			public int getCols() {
				return HyperspectralImageModel.this.getCols();
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
