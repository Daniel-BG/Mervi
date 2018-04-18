package com.mervi.model.metrics;

import com.mervi.model.AbstractHyperspectralImageModel;

public class ImageMetrics {
	
	private static void sanityCheck(AbstractHyperspectralImageModel ahima, AbstractHyperspectralImageModel ahimb) {
		if (!ahima.sizeEquals(ahimb))
			throw new IllegalArgumentException("Size must be equal");
	}
	
	public static double PSNR(AbstractHyperspectralImageModel ahima, AbstractHyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		return MetricUtilities.PSNR(MSE(ahima, ahimb), ahima.getRange());
	}
	
	public static double SNR(AbstractHyperspectralImageModel ahima, AbstractHyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		return (double) (10 * Math.log10(ahima.variance() / MSE(ahima, ahimb)));
	}
	
	public static double MSE(AbstractHyperspectralImageModel ahima, AbstractHyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		
		double acc = 0;
		
		for (int band = 0; band < ahima.bandsProperty().intValue(); band++) {
			for (int row = 0; row < ahima.rowsProperty().intValue(); row++) {
				for (int col = 0; col < ahima.colsProperty().intValue(); col++) {
					double val = ahima.getValue(band, row, col) - ahimb.getValue(band, row, col);
					acc += val * val;
				}
			}	
		}

		return acc / ((double) ahima.bandsProperty().intValue() * ahima.rowsProperty().intValue() * ahima.colsProperty().intValue());
	}
	
	public static double maxSE (AbstractHyperspectralImageModel ahima, AbstractHyperspectralImageModel ahimb) {
		sanityCheck(ahima, ahimb);
		
		double res = Double.MIN_VALUE;
		
		for (int band = 0; band < ahima.bandsProperty().intValue(); band++) {
			for (int row = 0; row < ahima.rowsProperty().intValue(); row++) {
				for (int col = 0; col < ahima.colsProperty().intValue(); col++) {
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
