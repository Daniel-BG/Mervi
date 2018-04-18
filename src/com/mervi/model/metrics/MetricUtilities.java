package com.mervi.model.metrics;

public class MetricUtilities {
	
	public static double PSNR (double mse, double max) {
		if (mse == 0d) {
			return Float.POSITIVE_INFINITY;
		} else {
			return (double) (20 * Math.log10(max) - 10 * Math.log10(mse));
		}
	}

}
