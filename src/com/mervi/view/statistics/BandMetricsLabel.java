package com.mervi.view.statistics;

import com.mervi.Config;
import com.mervi.model.HyperspectralBandModel;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.metrics.BandMetrics;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class BandMetricsLabel extends Label {
	private IntegerProperty selectedBand = new SimpleIntegerProperty(0);
	
	private SimpleObjectProperty<HyperspectralImageModel> aImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	private SimpleObjectProperty<HyperspectralImageModel> bImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	
	public BandMetricsLabel() {
		InvalidationListener il = e -> recalculate();
		
		this.selectedBand.addListener(il);
		this.aImageProperty.addListener(il);
		this.bImageProperty.addListener(il);
	}

	
	private void recalculate() {
    	int band = selectedBand.intValue();
    	HyperspectralBandModel romOrig, romComp;
    	try {
        	romOrig = aImageProperty.getValue().getBand(band);
        	romComp = bImageProperty.getValue().getBand(band);
    	} catch (Exception ex) {
    		setText("Could not load band metrics");
    		return; //if one image has updated and the other hasn't, avoid conflicts
    	}
    	if (!romOrig.sizeEquals(romComp))
    		return; //two different sizes loaded
    	
    	double maxse = BandMetrics.maxSE(romOrig, romComp);
    	double mse = BandMetrics.MSE(romOrig, romComp);
    	double snr = BandMetrics.SNR(romOrig, romComp);
    	double psnr = BandMetrics.PSNR(romOrig, romComp);
    	
    	this.setText( 
    			"BAND: mse: " + (long) mse + 
    			" snr: " + String.format(Config.DOUBLE_FORMAT, snr) + 
    			" psnr: " + String.format(Config.DOUBLE_FORMAT, psnr) + 
    			" maxse: " + (long) maxse);
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
