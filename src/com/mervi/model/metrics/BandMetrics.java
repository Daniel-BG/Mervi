package com.mervi.model.metrics;

import com.mervi.model.HyperspectralBandModel;

public class BandMetrics {
	
	private static void sanityCheck(HyperspectralBandModel roma, HyperspectralBandModel romb) {
		if (!roma.sizeEquals(romb))
			throw new IllegalArgumentException("Size must be equal");
	}
	
	public static double PSNR(HyperspectralBandModel roma, HyperspectralBandModel romb) {
		sanityCheck(roma, romb);
		return MetricUtilities.PSNR(MSE(roma, romb), roma.getRange());
	}
	
	public static double SNR(HyperspectralBandModel roma, HyperspectralBandModel romb) {
		sanityCheck(roma, romb);
		return (double) (10 * Math.log10(roma.getStatistics().variance() / MSE(roma, romb)));
	}
	
	public static double MSE(HyperspectralBandModel roma, HyperspectralBandModel romb) {
		sanityCheck(roma, romb);
		
		double acc = 0;
		for (int i = 0; i < roma.getRows(); i++) {
			for (int j = 0; j < roma.getCols(); j++) {
				double val = roma.get(i, j) - romb.get(i, j);
				acc += val * val;
			}
		}
		return acc / ((double) roma.getRows() * roma.getCols());
	}
	
	public static double maxSE (HyperspectralBandModel roma, HyperspectralBandModel romb) {
		sanityCheck(roma, romb);
		
		double res = Double.MIN_VALUE;
		for (int i = 0; i <roma.getRows(); i++) {
			for (int j = 0; j <roma.getCols(); j++) {
				double val = roma.get(i, j) - romb.get(i, j);
				val *= val;
				if (val > res)
					res = val;
			}
		}
		return res;
	}	

}
