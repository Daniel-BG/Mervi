package com.mervi.view.statistics;

import com.mervi.Config;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.metrics.ImageMetrics;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class ImageMetricsLabel extends Label  {

	private SimpleObjectProperty<HyperspectralImageModel> aImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	private SimpleObjectProperty<HyperspectralImageModel> bImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	
	public ImageMetricsLabel() {
		aImageProperty.addListener(e -> recalculate());
		bImageProperty.addListener(e -> recalculate());
	}
	
	private void recalculate() {
		HyperspectralImageModel himA = aImageProperty.getValue();
		HyperspectralImageModel himB = bImageProperty.getValue();
		
    	if (himA == null || himB == null || !himA.sizeEquals(himB)) {
    		setText("Could not load image metrics");
    		return;
    	}
    		
    	
    	double maxse = ImageMetrics.maxSE(himA, himB);
    	double mse = ImageMetrics.MSE(himA, himB);
    	double snr = ImageMetrics.SNR(himA, himB);
    	double psnr = ImageMetrics.PSNR(himA, himB);
    	
    	this.setText( 
    			"IMAGE: mse: " + (long) mse + 
    			" snr: " + String.format(Config.DOUBLE_FORMAT, snr) + 
    			" psnr: " + String.format(Config.DOUBLE_FORMAT, psnr) + 
    			" maxse: " + (long) maxse);
	}
	
	public SimpleObjectProperty<HyperspectralImageModel> aImageProperty() {
		return this.aImageProperty;
	}
	
	public SimpleObjectProperty<HyperspectralImageModel> bImageProperty() {
		return this.bImageProperty;
	}
}
