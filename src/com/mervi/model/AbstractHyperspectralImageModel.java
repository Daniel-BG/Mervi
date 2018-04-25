package com.mervi.model;

import com.mervi.control.ObjectChangedProperty;

public abstract class AbstractHyperspectralImageModel {
	
	private int bands, rows, cols;
	private ObjectChangedProperty modelChanged; //updated when the values change
	private HyperspectralBandModel[] bandCache;
	
	{	
		this.modelChanged = new ObjectChangedProperty();
		
		this.modelChanged.addListener(e -> {
			bandCache = new HyperspectralBandModel[this.bands];
			this.average = null;
			this.variance = null;
		});
	}
	
	public void setSize(int bands, int rows, int cols, boolean update) {
		this.bands = bands;
		this.rows = rows;
		this.cols = cols;
		this.doSetSize();
		if (update)
			this.modelChangedProperty().update();
	}
	
	/**
	 * called after changing bands, rows, and cols, but before updating modelChangedProperty()
	 */
	protected abstract void doSetSize();
	
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public int getBands() {
		return this.bands;
	}
	
	public ObjectChangedProperty modelChangedProperty() {
		return this.modelChanged;
	}
	
	public abstract int getValue(int band, int row, int col);
	
	public abstract int getRange();
	
	public final HyperspectralBandModel getBand(int index) {
		if (this.bandCache == null)
			throw new NullPointerException();
			
		if (this.bandCache[index] == null)
			this.bandCache[index] = doGetBand(index);
		
		return this.bandCache[index];
	}
	
	public abstract HyperspectralBandModel doGetBand(int index);
	
	public abstract boolean available();
	
	
	public boolean sizeEquals(AbstractHyperspectralImageModel other) {
		return this.getRows() == other.getRows()
				&& this.getCols() == other.getCols()
				&& this.getBands() == other.getBands()
				&& this.getRange() == other.getRange();
	}
	
	/** precalculated values for metrics */
	private Double average;
	private Double variance;
	
	public double variance() {
		if (variance != null)
			return variance;
		
		double localavg = this.average();

		double acc = 0;
		for (int band = 0; band < getBands(); band++) {
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col < getCols(); col++) {
					double val = (double) this.getValue(band, row, col) - localavg;
					acc += val * val;
				}
			}	
		}
		
		return variance = acc / ((double) getBands() * getCols() * getRows());
	}
	
	public double average() {
		if (average != null)
			return average;
		
		double acc = 0;
		
		for (int band = 0; band < getBands(); band++) {
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col <getCols(); col++) {
					acc += (double) this.getValue(band, row, col);
				}
			}	
		}
		
		return average = acc / ((double) getBands() * getCols() * getRows());
	}
	
}
