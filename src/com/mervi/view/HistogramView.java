package com.mervi.view;

import org.controlsfx.control.RangeSlider;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class HistogramView extends VBox {
	
	private AreaChart<Number, Number> chart;
	private NumberAxis axx, axy;
	private RangeSlider slider;
	private XYChart.Series<Number, Number> series;
	
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
		//create series object. When modified, this will change what appears on screen
		series = new XYChart.Series<Number, Number>();
		chart.getData().add(series);
		
		//create control slider
		slider = new RangeSlider();
		slider.setShowTickLabels(false);
		slider.setShowTickMarks(false);
		//slider.setMajorTickUnit(50);
		//slider.setMinorTickCount(5);
		//slider.setBlockIncrement(10);
		//slider.setMinHeight(30);

		this.getChildren().addAll(chart, slider);

		createDataUpdaters();
		createSliderUpdaters();
		createClickCtrl();
	}
	
	
	private void createClickCtrl() {
		slider.setOnMouseClicked(e -> {if (e.isShiftDown()) fitSliderToData();});
	}

	private void createDataUpdaters() {
		this.getSeries().getData().addListener((ListChangeListener<XYChart.Data<Number, Number>>) e -> fitSliderToData());
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
		if (this.getSeries().getData().isEmpty())
			return; //do not update if empty
		
		double minVal = this.getSeries().getData().get(0).getXValue().doubleValue();
		double maxVal = this.getSeries().getData().get(this.getSeries().getData().size() - 1).getXValue().doubleValue(); 
		
		slider.setMin(minVal);
		slider.setMax(maxVal);
		slider.setLowValue(minVal);
		slider.setHighValue(maxVal);
	}
	
	
	/**
	 * @return the inner observable list that is to be modified if 
	 * the histogram is required to change
	 */
	public XYChart.Series<Number, Number> getSeries() {
		return this.series;
	}

	/**
	 * @return the property tied to the lower selected bound
	 */
	public DoubleProperty lowValueProperty() {
		return this.slider.lowValueProperty();
	}
	
	
	/**
	 * @return the property tied to the upper selected bound
	 */
	public DoubleProperty highValueProperty() {
		return this.slider.highValueProperty();
	}

}
