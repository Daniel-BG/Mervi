package com.mervi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class HyperspectralBandStatistics {
	
	private HyperspectralBandModel hbm;
	
	public HyperspectralBandStatistics(HyperspectralBandModel hbm) {
		this.hbm = hbm;
	}
	
	/** precalculated values for metrics */
	private Double average;
	private Double variance;
	
	public double variance() {
		if (variance != null)
			return variance;
		
		double localavg = this.average();

		double acc = 0;
		for (int j = 0; j < hbm.getRows(); j++) {
			for (int k = 0; k < hbm.getCols(); k++) {
				double val = (double) hbm.get(j, k) - localavg;
				acc += val * val;
			}
		}
		
		return variance = acc / ((double) hbm.getSize());
	}
	
	public double average() {
		if (average != null)
			return average;
		
		double acc = 0;
		for (int j = 0; j < hbm.getRows(); j++) {
			for (int k = 0; k < hbm.getCols(); k++) {
				acc += (double) hbm.get(j, k);
			}
		}

		return average = acc / ((double) hbm.getSize());
	}
	
	
	/** Precalculated values for image drawing */
	private Integer maxVal;
	private Integer minVal;
	private Integer maxMinDiff;
	private Image bwImage;
	
	private void recalculateMinMax() {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int row = 0; row < hbm.getRows(); row++) {
			for (int col = 0; col < hbm.getCols(); col++) {
				int data = hbm.get(row, col);
				if (data > max)
					max = data;
				if (data < min)
					min = data;
			}
		}
		maxVal = max;
		minVal = min;
		maxMinDiff = max - min;
	}
	
	public int getMax() {
		if (maxVal == null)
			recalculateMinMax();
		return maxVal;
	}
	
	public int getMin() {
		if (minVal == null) 
			recalculateMinMax();
		return minVal;
	}
	
	public int getMaxMinDiff() {
		if (maxMinDiff == null)
			recalculateMinMax();
		return maxMinDiff;
	}
	
	private Double dynLow, dynHigh;
	private boolean dynRangeChanged = false;
	private boolean dynRangeEnabled = false;
	
	/**
	 * Set the dyn range of the shown image to eliminate outliers and have a more visible image
	 * If both parameters are set to NULL, postprocessing is disabled
	 * @param low
	 * @param high
	 */
	public void setDynRange(Double low, Double high) {
		if (low < 0.0 || high > 1.0 || low > high || low == null && high != null || high == null && low != null)
			throw new IllegalArgumentException();
		dynLow = low;
		dynHigh = high;
		dynRangeChanged = true;
		if (dynLow == null && dynHigh == null)
			dynRangeEnabled = false;
		else
			dynRangeEnabled = true;
	}
	
	private double getNormalized(int row, int col) {
		double val = hbm.get(row, col);
		val -= this.getMin();
		val /= (double) getMaxMinDiff();
		if (dynRangeEnabled) {
			val -= this.dynLow;
			val /= this.dynHigh - this.dynLow;
			//clamp pixels
			if (val < 0)
				val = 0;
			if (val > 1)
				val = 1;
		}
		return val;
	}
	
	public Image getImage() {
		if (this.bwImage != null && !this.dynRangeChanged)
			return this.bwImage;
		
		WritableImage wi = new WritableImage(hbm.getCols(), hbm.getRows());
		PixelWriter pw = wi.getPixelWriter();
				
		for (int col = 0; col < hbm.getCols(); col++) {
			for (int row = 0; row < hbm.getRows(); row++) {
				double value = this.getNormalized(row, col);
				int color = (int) (value * 255.0);
				pw.setArgb(col, row, 0xff000000 | (color << 16) | (color << 8) | color);
				//pw.setColor(col, row, new Color(value, value, value, 1));
			}
		}
		
		this.dynRangeChanged = false;
		return this.bwImage = wi;
	}
	
	List<Data<Number, Number>> bandData;
	int maxY = Integer.MIN_VALUE, minY = 0, maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE;
	
	private void generateHistogram() {
		if (bandData == null) {
			int diff = 0;
			int[] buckets = new int[hbm.getRange()];
			for (int i = 0; i < hbm.getRows(); i++) {
				for (int j = 0; j < hbm.getCols(); j++) {
					int index = hbm.get(i,  j);
					minX = Math.min(minX, index);
					maxX = Math.max(maxX, index);
					int newValue = buckets[index] + 1;
					if (newValue == 1)
						diff++;
					buckets[index] = newValue;
					maxY = Math.max(maxY, newValue);
				}
			}
			bandData = new ArrayList<Data<Number, Number>>(diff);
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] > 0)
					bandData.add(new XYChart.Data<Number, Number>(i, buckets[i]));
			}
		}
	}

	public List<Data<Number, Number>> getHistogram() {
		generateHistogram();
		return Collections.unmodifiableList(this.bandData);
	}
	
	public int getMaxX() {
		generateHistogram();
		return this.maxX;
	}
	
	public int getMinX() {
		generateHistogram();
		return this.minX;
	}
	
	public int getMinY() {
		generateHistogram();
		return this.minY;
	}
	
	public int getMaxY() {
		generateHistogram();
		return this.maxY;
	}

}
