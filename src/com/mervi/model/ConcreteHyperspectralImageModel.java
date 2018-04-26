package com.mervi.model;

import com.jypec.img.HyperspectralImage;
import com.jypec.img.HyperspectralImageData;

public class ConcreteHyperspectralImageModel extends HyperspectralImageModel {
	
	private int[][][] values;
	public ConcreteHyperspectralImageModel(HyperspectralImage image) {
		super(image.getData().getNumberOfBands(), 
				image.getData().getNumberOfLines(), 
				image.getData().getNumberOfSamples(), 
				image.getData().getDataType().getDynamicRange());
		
		this.values = new int[getBands()][getRows()][getCols()];
		
		this.setFromImage(image);
	}
	
	private void setFromImage(HyperspectralImage image) {
		HyperspectralImageData data = image.getData();
		
		for (int band = 0; band < getBands(); band++) {
			for (int row = 0; row < getRows(); row++) {
				for (int col = 0; col < getCols(); col++) {
					this.values[band][row][col] = data.getDataAt(band, row, col);
				}
			}
		}
	}


	@Override
	public int getValue(int band, int row, int col) {
		return this.values[band][row][col];
	}




}
