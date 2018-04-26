package com.mervi.model.metrics;

import com.mervi.model.HyperspectralBandModel;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class HistogramUtilities {
	

	public static void getHistogramFor(HyperspectralBandModel band, ObservableList<Data<Number, Number>> target) {
		target.clear();
		
		int[] buckets = new int[band.getRange()];
		for (int i = 0; i < band.getRows(); i++) {
			for (int j = 0; j < band.getCols(); j++) {
				buckets[band.get(i, j)]++;
			}
		}
		
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] > 0)
				target.add(new XYChart.Data<Number, Number>(i, buckets[i]));
		}
	}

}
