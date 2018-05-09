package com.mervi.view.statistics;

import com.mervi.Config;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.metrics.PixelMetrics;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class PixelMetricsLabel extends Label {
	
	private IntegerProperty selectedRow = new SimpleIntegerProperty(0);
	private IntegerProperty selectedCol = new SimpleIntegerProperty(0);
	private IntegerProperty selectedBand = new SimpleIntegerProperty(0);
	
	private SimpleObjectProperty<HyperspectralImageModel> aImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	private SimpleObjectProperty<HyperspectralImageModel> bImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	
	public PixelMetricsLabel() {
		InvalidationListener il = e -> recalculate();
		
		this.selectedBand.addListener(il);
		this.selectedCol.addListener(il);
		this.selectedRow.addListener(il);
		this.aImageProperty.addListener(il);
		this.bImageProperty.addListener(il);
	}
	
	private void recalculate() {
    	int row = selectedRow.intValue();
    	int col = selectedCol.intValue();
    	int band = selectedBand.intValue();
    	int originalPix, compressedPix;
    	try {
        	originalPix = aImageProperty.getValue().getValue(band, row, col);
        	compressedPix = bImageProperty.getValue().getValue(band, row, col);
    	} catch (Exception ex) {
    		setText("Could not load pixel metrics");
    		return; //if one image has updated and the other hasn't, avoid conflicts
    	}
    	
    	double percent = PixelMetrics.percentDifference(originalPix, compressedPix);
    	double psnr = PixelMetrics.PSNR(originalPix, compressedPix, aImageProperty.getValue().getRange());
    	int diff = PixelMetrics.difference(originalPix, compressedPix);
    	
    	
    	this.setText(
    			"PIXEL: %off: " + String.format(Config.DOUBLE_FORMAT,percent) + 
    			" PSNR: " + String.format(Config.DOUBLE_FORMAT,psnr) + 
    			" DIFF: " + diff);
	}
	

	
	public IntegerProperty selectedRowProperty() {
		return this.selectedRow;
	}
	
	public IntegerProperty selectedColProperty() {
		return this.selectedCol;
	}
	
	public IntegerProperty selectedBandProperty() {
		return this.selectedBand;
	}
	
	public SimpleObjectProperty<HyperspectralImageModel> aImageProperty() {
		return this.aImageProperty;
	}
	
	public SimpleObjectProperty<HyperspectralImageModel> bImageProperty() {
		return this.bImageProperty;
	}
	
		
}