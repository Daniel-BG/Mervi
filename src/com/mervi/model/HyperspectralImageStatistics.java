package com.mervi.model;

public class HyperspectralImageStatistics {
	
	private HyperspectralImageModel image;
	
	public HyperspectralImageStatistics(HyperspectralImageModel image) {
		this.image = image;
	}
	
	
	
	/** precalculated values for metrics */
	private Double average;
	private Double variance;
	
	
	public double variance() {
		if (variance != null)
			return variance;
		
		double localavg = this.average();

		double acc = 0;
		for (int band = 0; band < image.getBands(); band++) {
			for (int row = 0; row < image.getRows(); row++) {
				for (int col = 0; col < image.getCols(); col++) {
					double val = (double) image.getValue(band, row, col) - localavg;
					acc += val * val;
				}
			}	
		}
		
		return variance = acc / ((double) image.getSize());
	}
	
	public double average() {
		if (average != null)
			return average;
		
		double acc = 0;
		
		for (int band = 0; band < image.getBands(); band++) {
			for (int row = 0; row < image.getRows(); row++) {
				for (int col = 0; col < image.getCols(); col++) {
					acc += (double) image.getValue(band, row, col);
				}
			}	
		}
		
		return average = acc / ((double) image.getSize());
	}

}
