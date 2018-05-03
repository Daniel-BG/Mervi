package com.mervi.model;

import com.mervi.model.observers.HyperspectralImageObserver;
import com.mervi.model.observers.ParametrizedObservable;
import com.mervi.util.exceptions.DataNotInitializedException;
import com.mervi.util.exceptions.ObserverNotFoundException;

public abstract class HyperspectralImageModel extends ParametrizedObservable<HyperspectralImageObserver>{
	
	private int bands, rows, cols, range;
	private HyperspectralImageStatistics statistics;
	
	
	public HyperspectralImageModel(int bands, int rows, int cols, int range) {
		this.bands = bands;
		this.rows = rows;
		this.cols = cols;
		this.range = range;
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
		return this.range;
	}
	
	public int getBitSize() {
		return getSize() * getRange();
	}
	
	public abstract int getValue(int band, int row, int col);
	
	public HyperspectralImageStatistics getStatistics() {
		return this.statistics;
	}
	/****************************/
	
	
	/** OBSERVER STUFF */
	public void requestStatistics(HyperspectralImageObserver o) {
		if (this.observers.contains(o))
			o.statisticsUpdate(this.statistics);
		else
			throw new ObserverNotFoundException();
	}

	public void requestBand(HyperspectralImageObserver o, int index) {
		if (this.observers.contains(o))
			o.bandRequested(this.getBand(index), index);
		else
			throw new ObserverNotFoundException();
	}
	/*******************/
	
	
	
	
	
	/** OTHERS */
	public boolean sizeEquals(HyperspectralImageModel other) {
		return this.getRows() == other.getRows()
				&& this.getCols() == other.getCols()
				&& this.getBands() == other.getBands()
				&& this.getRange() == other.getRange();
	}
	/***********/
	
}
