package com.mervi.model.metrics;

public class PixelMetrics {
	
	public static double percentDifference(int a, int b) {
		return 100.0 * ((double) a / (double) b - 1.0);
	}
	
	public static int absoluteDifference(int a, int b) {
		return Math.abs(a - b);
	}
	
	public static long squareErr(int a, int b) {
		long err = a - b;
		return err * err;
	}
	
	public static double PSNR(int a, int b, int range) {
		return MetricUtilities.PSNR(squareErr(a, b), range);
	}

	public static int difference(int originalPix, int compressedPix) {
		return originalPix - compressedPix;
	}

}
