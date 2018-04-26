package com.mervi.model;

public class HyperspectralBandModel {
	
	private HyperspectralBandStatistics statistics;
	private HyperspectralImageModel him;
	private int bandIndex;
	
	public HyperspectralBandModel(HyperspectralImageModel him, int index) {
		this.statistics = new HyperspectralBandStatistics(this);
		this.him = him;
		this.bandIndex = index;
	}
	
	/** GETTERS */
	public int getRows() {
		return him.getRows();
	}
	
	public int getCols() {
		return him.getCols();
	}
	
	public double getSize() {
		return getRows() * getCols();
	}
	
	public int get(int row, int col) {
		return him.getValue(bandIndex, row, col);
	}
	
	public int getRange() {
		return him.getRange();
	}
	
	public HyperspectralBandStatistics getStatistics() {
		return this.statistics;
	}
	/************/
	
	/** OTHERS */
	public boolean sizeEquals(HyperspectralBandModel other) {
		return this.getRows() == other.getRows() 
				&& this.getCols() == other.getCols()
				&& this.getRange() == other.getRange();
	}
	/***********/

}
