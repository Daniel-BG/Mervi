package com.mervi.view.images;

import com.mervi.model.HyperspectralBandModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;

public class BandView extends ImageView {

	private SimpleObjectProperty<HyperspectralBandModel> band = new SimpleObjectProperty<HyperspectralBandModel>();
	
	public BandView() {
		this.setCache(true);
		this.bandProperty().addListener((o, oldVal, newVal) -> this.setImage(newVal.getStatistics().getImage()));
	}
	
	public ObjectProperty<HyperspectralBandModel> bandProperty() {
		return this.band;
	}
}
