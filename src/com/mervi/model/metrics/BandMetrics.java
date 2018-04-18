package com.mervi.model.metrics;

import com.mervi.model.ReadOnlyMatrix;

public class BandMetrics {
	
	private static void sanityCheck(ReadOnlyMatrix roma, ReadOnlyMatrix romb) {
		if (!roma.sizeEquals(romb))
			throw new IllegalArgumentException("Size must be equal");
	}
	
	public double PSNR(ReadOnlyMatrix roma, ReadOnlyMatrix romb) {
		sanityCheck(roma, romb);
		return MetricUtilities.PSNR(MSE(roma, romb), roma.range());
	}
	
	public static float SNR(ReadOnlyMatrix roma, ReadOnlyMatrix romb) {
		sanityCheck(roma, romb);
		return (float) (10 * Math.log10(roma.variance() / MSE(roma, romb)));
	}
	
	public static double MSE(ReadOnlyMatrix roma, ReadOnlyMatrix romb) {
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
	
	public static double maxSE (ReadOnlyMatrix roma, ReadOnlyMatrix romb) {
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
