package com.mervi.model.metrics;

import com.mervi.model.HyperspectralImageModel;

public class ImageMetrics {
	
	private static void sanityCheck(HyperspectralImageModel ahima, HyperspectralImageModel ahimb) {
		if (!ahima.sizeEquals(ahimb))
			throw new IllegalArgumentException("Size must be equal");
	}
	
	public static double PSNR(HyperspectralImageModel ahima, HyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		return MetricUtilities.PSNR(MSE(ahima, ahimb), ahima.getRange());
	}
	
	public static double SNR(HyperspectralImageModel ahima, HyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		return (double) (10 * Math.log10(ahima.getStatistics().variance() / MSE(ahima, ahimb)));
	}
	
	public static double MSE(HyperspectralImageModel ahima, HyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		
		double acc = 0;
		
		for (int band = 0; band < ahima.getBands(); band++) {
			for (int row = 0; row < ahima.getRows(); row++) {
				for (int col = 0; col < ahima.getCols(); col++) {
					double val = ahima.getValue(band, row, col) - ahimb.getValue(band, row, col);
					acc += val * val;
				}
			}	
		}

		return acc / ((double) ahima.getBands() * ahima.getRows() * ahima.getCols());
	}
	
	public static double maxSE (HyperspectralImageModel ahima, HyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		
		double res = Double.MIN_VALUE;
		
		for (int band = 0; band < ahima.getBands(); band++) {
			for (int row = 0; row < ahima.getRows(); row++) {
				for (int col = 0; col < ahima.getCols(); col++) {
					double val = ahima.getValue(band, row, col) - ahimb.getValue(band, row, col);
					val *= val;
					if (val > res)
						res = val;
				}
			}	
		}
		
		return res;
	}	

}
