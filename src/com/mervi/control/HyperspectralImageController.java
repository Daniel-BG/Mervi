package com.mervi.control;

import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.observers.HyperspectralImageObserver;

public class HyperspectralImageController {
	
	private HyperspectralImageModel image;
	
	public HyperspectralImageController(HyperspectralImageModel him) {
		this.image = him;
	}
	
	public void requestStatistics(HyperspectralImageObserver o) {
		this.image.requestStatistics(o);
	}
	
	public void requestBand(HyperspectralImageObserver o, int index) {
		this.image.requestBand(o, index);
	}
	
	public void addObserver(HyperspectralImageObserver o) {
		this.image.addObserver(o);
	}
	
	public void removeObserver(HyperspectralImageObserver o) {
		this.image.removeObserver(o);
	}

}
