package com.mervi.model.binders;

import com.mervi.model.HyperspectralBandModel;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.view.HyperspectralImageStage;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class ImageModelBandSelector {
	
	private SimpleObjectProperty<HyperspectralBandModel> redBand = new SimpleObjectProperty<HyperspectralBandModel>();
	private SimpleObjectProperty<HyperspectralBandModel> greenBand = new SimpleObjectProperty<HyperspectralBandModel>();
	private SimpleObjectProperty<HyperspectralBandModel> blueBand = new SimpleObjectProperty<HyperspectralBandModel>();
	
	private IntegerProperty numberOfRows = new SimpleIntegerProperty();
	private IntegerProperty numberOfCols = new SimpleIntegerProperty();
	
	public ImageModelBandSelector(
			ObjectProperty<HyperspectralImageModel> imageProperty, 
			ObservableValue<? extends Number> selectedR,
			ObservableValue<? extends Number> selectedG, 
			ObservableValue<? extends Number> selectedB) {
		
		InvalidationListener updateRed = e -> {
			if (imageProperty.getValue() != null)
				this.redBand.setValue(imageProperty.getValue().getBand(selectedR.getValue().intValue()));
		};
		InvalidationListener updateGreen = e -> {
			if (imageProperty.getValue() != null)
				this.blueBand.setValue(imageProperty.getValue().getBand(selectedG.getValue().intValue()));
		};
		InvalidationListener updateBlue = e -> {
			if (imageProperty.getValue() != null)
				this.greenBand.setValue(imageProperty.getValue().getBand(selectedB.getValue().intValue()));
		};
		
		imageProperty.addListener(updateRed);
		imageProperty.addListener(updateGreen);
		imageProperty.addListener(updateBlue);
		
		selectedR.addListener(updateRed);
		selectedG.addListener(updateGreen);
		selectedB.addListener(updateBlue);
		
		imageProperty.addListener((o, oldVal, newVal) -> {
			numberOfRows.set(newVal.getRows());
			numberOfCols.set(newVal.getCols());
		});
	}
	
	public ObjectProperty<HyperspectralBandModel> redBandProperty() {
		return this.redBand;
	}
	
	public ObjectProperty<HyperspectralBandModel> greenBandProperty() {
		return this.greenBand;
	}
	
	public ObjectProperty<HyperspectralBandModel> blueBandProperty() {
		return this.blueBand;
	}
	
	public void bindTo(HyperspectralImageStage his) {
		his.redBandProperty().bind(redBand);
		his.blueBandProperty().bind(blueBand);
		his.greenBandProperty().bind(greenBand);
		
		his.numColsProperty().bind(numberOfCols);
		his.numRowsProperty().bind(numberOfRows);
	}
}