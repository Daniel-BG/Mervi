package com.mervi.model.metrics;

public class PixelMetrics {
	
	public static double percentDifference(int a, int b) {
		return (double) a / (double) b - 1.0f;
	}
	
	public static int absoluteDifference(int a, int b) {
		return Math.abs(a - b);
	}
	
	public static int squareErr(int a, int b) {
		int err = a - b;
		return err * err;
	}
	
	public static double PSNR(int a, int b, int range) {
		return MetricUtilities.PSNR(squareErr(a, b), range);
	}

}
