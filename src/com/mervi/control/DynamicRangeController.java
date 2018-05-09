package com.mervi.control;

import com.mervi.model.properties.BandViewProperties;

public class DynamicRangeController {
	
	private BandViewProperties[] bvp;

	public DynamicRangeController(BandViewProperties... bvp) {
		this.bvp = bvp;
	}

	public void setLow(Double newVal) {
		for (BandViewProperties bvp: bvp)
			bvp.selectedLowProperty().set(newVal);
	}

	public void setHigh(Double newVal) {
		for (BandViewProperties bvp: bvp)
			bvp.selectedHighProperty().set(newVal);
	}

}
