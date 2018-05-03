package com.mervi.model;

public class HyperspectralDiffModel extends HyperspectralImageModel {
	
	HyperspectralImageModel source1, source2;
	
	public HyperspectralDiffModel(HyperspectralImageModel him1, HyperspectralImageModel him2) {
		super(him1.getBands(), him1.getRows(), him1.getCols(), him1.getDepth());
		if (!him1.sizeEquals(him2)) {
			throw new IllegalArgumentException("Both images must equal in size");
		}
		this.source1 = him1;
		this.source2 = him2;
	}

	@Override
	public int getValue(int band, int row, int col) {
		return Math.abs(source1.getValue(band, row, col) - source2.getValue(band, row, col));
	}

	
	

}
