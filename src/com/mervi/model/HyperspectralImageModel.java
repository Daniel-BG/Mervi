package com.mervi.model;

import com.mervi.util.exceptions.DataNotInitializedException;

public abstract class HyperspectralImageModel {
	
	private int bands, rows, cols, depth;
	private HyperspectralImageStatistics statistics;
	
	
	public HyperspectralImageModel(int bands, int rows, int cols, int depth) {
		this.bands = bands;
		this.rows = rows;
		this.cols = cols;
		this.depth = depth;
		this.statistics = new HyperspectralImageStatistics(this);
		this.bandCache = new HyperspectralBandModel[this.bands];
	}
	
	
	
	/** BAND CACHE SYSTEM */
	private HyperspectralBandModel[] bandCache;
	
	public  HyperspectralBandModel getBand(int index) {
		if (this.bandCache == null)
			throw new DataNotInitializedException();
			
		if (this.bandCache[index] == null)
			this.bandCache[index] = createBand(index);
		
		return this.bandCache[index];
	}
	
	/**
	 * Create the band corresponding to that index
	 * When this is called a NEW band must be created, and (if existent)
	 * the other one invalidated
	 * @param index
	 * @return
	 */
	private HyperspectralBandModel createBand(int index) {
		return new HyperspectralBandModel(this, index);
	}
	/********************/
	
	
	/** NORMAL GETTERS AND STUFF */
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public int getBands() {
		return this.bands;
	}
	
	public int getSize() {
		return getBands() * getCols() * getRows();
	}
	
	public int getRange() {
		return 0x1 << getDepth();
	}
	
	public int getDepth() {
		return this.depth;
	}
	
	public int getBitSize() {
		return getSize() * getRange();
	}
	
	public abstract int getValue(int band, int row, int col);
	
	public HyperspectralImageStatistics getStatistics() {
		return this.statistics;
	}
	/****************************/
	
	
	
	
	/** OTHERS */
	public boolean sizeEquals(HyperspectralImageModel other) {
		return this.getRows() == other.getRows()
				&& this.getCols() == other.getCols()
				&& this.getBands() == other.getBands()
				&& this.getRange() == other.getRange();
	}
	/***********/
	
}
