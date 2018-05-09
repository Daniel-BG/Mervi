package com.mervi.model.properties;

import com.mervi.model.HyperspectralBandModel;
import com.mervi.model.HyperspectralImageModel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class BandViewProperties {
	
	private final SimpleObjectProperty<HyperspectralBandModel> selectedBand = new SimpleObjectProperty<HyperspectralBandModel>();
	
	private final SimpleObjectProperty<Image> selectedImage = new SimpleObjectProperty<Image>();
	
	private final DoubleProperty selectedLow = new SimpleDoubleProperty(0.0);
	private final DoubleProperty selectedHigh = new SimpleDoubleProperty(1.0);
	
	
	
	public BandViewProperties(ObjectProperty<HyperspectralImageModel> him, IntegerProperty selectedBand) {
		InvalidationListener updateListener = e -> {
			
			int value = selectedBand.intValue();
			HyperspectralImageModel image = him.get();
			
			if (image == null)
				return; //TODO this gets here in the cases where the image has not yet been loaded (e.g viewing original but comp view is opened). Should not get here
			
			this.selectedBand.set(image.getBand(value)); //if its the same it wont update so thats good
			this.selectedBand.get().getStatistics().setDynRange(selectedLow.doubleValue(), selectedHigh.doubleValue());
			this.selectedImage.set(this.selectedBand.get().getStatistics().getImage());
		};
		
		him.addListener(updateListener);
		
		selectedBand.addListener(updateListener);
		
		this.selectedLow.addListener(updateListener);
		this.selectedHigh.addListener(updateListener);
		
	}
	
	
	public ReadOnlyObjectProperty<HyperspectralBandModel> selectedBandProperty() {
		return this.selectedBand;
	}
	
	public ReadOnlyObjectProperty<Image> selectedImageProperty() {
		return this.selectedImage;
	}
	
	public DoubleProperty selectedLowProperty() {
		return this.selectedLow;
	}
	
	public DoubleProperty selectedHighProperty() {
		return this.selectedHigh;
	}

}
