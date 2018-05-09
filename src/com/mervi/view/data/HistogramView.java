package com.mervi.view.data;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.RangeSlider;

import com.mervi.util.EventBinder;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HistogramView extends HBox {
	
	private AreaChart<Number, Number> chart;
	private List<SeriesData> seriesData = new ArrayList<SeriesData>();
	private NumberAxis axx, axy;
	private RangeSlider horizontalSlider;
	private RangeSlider verticalSlider;
	private InvalidationListener updateSliderOnDataChange = e -> fitSliderToData(); //listener that updates slider on data change
	
	public HistogramView() {
		//create axes
		axx = new NumberAxis();
		axy = new NumberAxis();
		axx.setAutoRanging(false);
		axy.setAutoRanging(false);
		
		//create chart
		chart = new AreaChart<Number, Number>(axx, axy);
		chart.setLegendVisible(false);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		chart.setCache(true);
		chart.setPrefWidth(2000);
		chart.setMaxWidth(2000);
		chart.setPrefHeight(2000);
		chart.setMaxHeight(2000);
		
		//create sliders
		horizontalSlider = new RangeSlider();
		horizontalSlider.setShowTickLabels(false);
		horizontalSlider.setShowTickMarks(false);
		verticalSlider = new RangeSlider();
		verticalSlider.setShowTickLabels(false);
		verticalSlider.setShowTickMarks(false);
		verticalSlider.setOrientation(Orientation.VERTICAL);
		//slider.setMajorTickUnit(50);
		//slider.setMinorTickCount(5);
		//slider.setBlockIncrement(10);
		//slider.setMinHeight(30);

		VBox helper = new VBox(chart, horizontalSlider);
		
		this.getChildren().addAll(verticalSlider, helper);

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
			for (Series<Number, Number> series: currSeries.subList(newSize, currSize)) {
				series.getData().removeListener(updateSliderOnDataChange);
				seriesData.remove(seriesData.size() - 1);
			}
				
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
			seriesData.add(new SeriesData());
		}
	}
	
	
	private void createClickCtrl() {
		horizontalSlider.setOnMouseClicked(e -> {if (e.isShiftDown()) fitSliderToData();});
		verticalSlider.setOnMouseClicked(e -> {if (e.isShiftDown()) fitSliderToData();});
	}
	
	private void createSliderUpdaters() {
		InvalidationListener ilHor = e -> {
			int low = horizontalSlider.lowValueProperty().intValue();
			int high = horizontalSlider.highValueProperty().intValue();
			
			axx.setLowerBound(low);
			axx.setUpperBound(high);
			axx.setTickUnit((double) (high - low) / 10.0);	
		};
		
		horizontalSlider.lowValueProperty().addListener(ilHor);
		horizontalSlider.highValueProperty().addListener(ilHor);
		
		InvalidationListener ilVer = e -> {
			int low = verticalSlider.lowValueProperty().intValue();
			int high = verticalSlider.highValueProperty().intValue();
			
			axy.setLowerBound(low);
			axy.setUpperBound(high);
			axy.setTickUnit((double) (high - low) / 10.0);	
		};
		
		verticalSlider.lowValueProperty().addListener(ilVer);
		verticalSlider.highValueProperty().addListener(ilVer);
		
		EventBinder.addSwipeWhenScrolling(horizontalSlider);
		EventBinder.addSwipeWhenScrolling(verticalSlider);
		EventBinder.moveIntervalWhenSwipe(horizontalSlider);
		EventBinder.moveIntervalWhenSwipe(verticalSlider);
	}
	
	
	private void fitSliderToData() {
		if (this.chart.getData().isEmpty())
			return;
		
		//just in case series are created but not filled in, avoid errors and such
		boolean found = false;
		for (XYChart.Series<Number,	Number> series: this.chart.getData()) {
			if (series.getData().isEmpty())
				continue;
			found = true;
		}
		if (!found)
			return;
		
		//calculate values
		double minXVal = Double.MAX_VALUE, minYVal = Double.MAX_VALUE;
		double maxXVal = Double.MIN_VALUE, maxYVal = Double.MIN_VALUE;
		
		for (SeriesData data: this.seriesData) {
			minXVal = Math.min(minXVal, data.minX);
			minYVal = Math.min(minYVal, data.minY);
			maxXVal = Math.max(maxXVal, data.maxX);
			maxYVal = Math.max(maxYVal, data.maxY);
		}
		
		
		horizontalSlider.setMin(minXVal);
		horizontalSlider.setMax(maxXVal);
		horizontalSlider.setLowValue(minXVal);
		horizontalSlider.setHighValue(maxXVal);
		
		verticalSlider.setMin(minYVal);
		verticalSlider.setMax(maxYVal);
		verticalSlider.setLowValue(minYVal);
		verticalSlider.setHighValue(maxYVal);
	}
	
	public void clearSeries(int index) {
		Series<Number, Number> old = this.chart.getData().remove(index);
		old.getData().removeListener(updateSliderOnDataChange);
		
		XYChart.Series<Number, Number> newSeries = new XYChart.Series<Number, Number>();
		newSeries.getData().addListener(updateSliderOnDataChange);
		this.chart.getData().add(index, newSeries);
		
		this.seriesData.remove(index);
		this.seriesData.add(index, new SeriesData());
	}
	
	/**
	 * @return the selected series that is shown on screen
	 */
	public XYChart.Series<Number, Number> getSeries(int index) {
		return this.chart.getData().get(index);
	}
	
	public void setSeries(int index, List<Data<Number, Number>> histogram, int minX, int maxX, int minY, int maxY) {
		this.clearSeries(index);
		SeriesData data = this.seriesData.get(index);
		data.minX = minX;
		data.minY = minY;
		data.maxX = maxX;
		data.maxY = maxY;
		this.getSeries(index).getData().addAll(histogram);
	}
	
	/**
	 * @return the property tied to the lower selected bound
	 * if set, changes the graph accordingly
	 */
	public DoubleProperty lowValueProperty() {
		return this.horizontalSlider.lowValueProperty();
	}
	
	
	/**
	 * @return the property tied to the upper selected bound
	 * if set, changes the graph accordingly
	 */
	public DoubleProperty highValueProperty() {
		return this.horizontalSlider.highValueProperty();
	}

	
	
	private class SeriesData {
		public int minX = Integer.MAX_VALUE;
		public int minY = Integer.MAX_VALUE;
		public int maxX = Integer.MIN_VALUE;
		public int maxY = Integer.MIN_VALUE;
	}


}
