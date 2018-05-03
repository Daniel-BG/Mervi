package com.mervi.view;

import org.controlsfx.control.RangeSlider;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;

public class HistogramView extends VBox {
	
	private AreaChart<Number, Number> chart;
	private NumberAxis axx, axy;
	private RangeSlider slider;
	private InvalidationListener updateSliderOnDataChange = e -> fitSliderToData(); //listener that updates slider on data change
	
	public HistogramView() {
		//create axes
		axx = new NumberAxis();
		axy = new NumberAxis();
		axx.setAutoRanging(false);
		axy.setAutoRanging(true);
		
		//create chart
		chart = new AreaChart<Number, Number>(axx, axy);
		chart.setLegendVisible(false);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		chart.setCache(true);
		chart.setPrefWidth(2000);
		chart.setMaxWidth(2000);
		
		//create control slider
		slider = new RangeSlider();
		slider.setShowTickLabels(false);
		slider.setShowTickMarks(false);
		//slider.setMajorTickUnit(50);
		//slider.setMinorTickCount(5);
		//slider.setBlockIncrement(10);
		//slider.setMinHeight(30);

		this.getChildren().addAll(chart, slider);

		createSliderUpdaters();
		createClickCtrl();
	}
	
	public HistogramView(int numberOfSeries) {
		this();
		this.setNumberOfSeries(numberOfSeries);
	}
	
	
	public void setNumberOfSeries(int newSize) {
		ObservableList<Series<Number, Number>> currSeries = chart.getData();
		int currSize = currSeries.size();
		
		//if size is reduced simply remove
		if (newSize < currSize) {
			for (Series<Number, Number> series: currSeries.subList(newSize, currSize))
				series.getData().removeListener(updateSliderOnDataChange);
			
			currSeries.remove(newSize, currSize);
			return;
		}
		//if size is not changed ignore
		if (newSize == currSize)
			return;
		//if size is increased create and add new elements
		for (int i = currSize; i < newSize; i++) {
			XYChart.Series<Number, Number> newSeries = new XYChart.Series<Number, Number>();
			newSeries.getData().addListener(updateSliderOnDataChange);
			currSeries.add(newSeries);
		}
	}
	
	
	private void createClickCtrl() {
		slider.setOnMouseClicked(e -> {if (e.isShiftDown()) fitSliderToData();});
	}
	
	private void createSliderUpdaters() {
		InvalidationListener il = e -> {
			int low = slider.lowValueProperty().intValue();
			int high = slider.highValueProperty().intValue();
			
			axx.setLowerBound(low);
			axx.setUpperBound(high);
			axx.setTickUnit((double) (high - low) / 10.0);	
		};
		
		slider.lowValueProperty().addListener(il);
		slider.highValueProperty().addListener(il);
	}
	
	
	private void fitSliderToData() {
		if (this.chart.getData().isEmpty())
			return;
		
		double minVal = Double.MAX_VALUE;
		double maxVal = Double.MIN_VALUE;
		boolean found = false;
		for (XYChart.Series<Number,	Number> series: this.chart.getData()) {
			if (series.getData().isEmpty())
				continue;
			found = true;
			minVal = Math.min(minVal,  series.getData().get(0).getXValue().doubleValue());
			maxVal = Math.max(maxVal, series.getData().get(series.getData().size() - 1).getXValue().doubleValue());
		}
		//just in case series are created but not filled in, avoid errors and such
		if (!found)
			return;
		
		slider.setMin(minVal);
		slider.setMax(maxVal);
		slider.setLowValue(minVal);
		slider.setHighValue(maxVal);
	}
	
	public void clearSeries(int index) {
		Series<Number, Number> old = this.chart.getData().remove(index);
		old.getData().removeListener(updateSliderOnDataChange);
		
		XYChart.Series<Number, Number> newSeries = new XYChart.Series<Number, Number>();
		newSeries.getData().addListener(updateSliderOnDataChange);
		this.chart.getData().add(index, newSeries);
	}
	
	/**
	 * @return the selected series that is shown on screen
	 */
	public XYChart.Series<Number, Number> getSeries(int index) {
		return this.chart.getData().get(index);
	}
	
	/**
	 * @return the property tied to the lower selected bound
	 * if set, changes the graph accordingly
	 */
	public DoubleProperty lowValueProperty() {
		return this.slider.lowValueProperty();
	}
	
	
	/**
	 * @return the property tied to the upper selected bound
	 * if set, changes the graph accordingly
	 */
	public DoubleProperty highValueProperty() {
		return this.slider.highValueProperty();
	}

}
