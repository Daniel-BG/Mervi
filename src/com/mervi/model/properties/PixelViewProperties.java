package com.mervi.model.properties;

import com.mervi.model.HyperspectralImageModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PixelViewProperties {

	private IntegerProperty redPixel = new SimpleIntegerProperty();
	private IntegerProperty greenPixel = new SimpleIntegerProperty();
	private IntegerProperty bluePixel = new SimpleIntegerProperty();
	
	private IntegerProperty bitDepth = new SimpleIntegerProperty();
	
	public PixelViewProperties(
			ObjectProperty<HyperspectralImageModel> imageProperty, 
			ProgramProperties properties) {
			/**ObservableValue<? extends Number> selectedRow,
			ObservableValue<? extends Number> selectedCol,
			ObservableValue<? extends Number> selectedR,
			ObservableValue<? extends Number> selectedG, 
			ObservableValue<? extends Number> selectedB) {*/
		
		InvalidationListener updateRed = e -> {
			if (imageProperty.getValue() != null)
				this.redPixel.setValue(imageProperty.getValue().getValue(
						properties.selectedRedProperty().intValue(), 
						properties.rowProperty().intValue(), 
						properties.colProperty().intValue()));
		};
		InvalidationListener updateGreen = e -> {
			if (imageProperty.getValue() != null)
				this.greenPixel.setValue(imageProperty.getValue().getValue(
						properties.selectedGreenProperty().intValue(), 
						properties.rowProperty().intValue(), 
						properties.colProperty().intValue()));
		};
		InvalidationListener updateBlue = e -> {
			if (imageProperty.getValue() != null)
				this.bluePixel.setValue(imageProperty.getValue().getValue(
						properties.selectedBlueProperty().intValue(), 
						properties.rowProperty().intValue(), 
						properties.colProperty().intValue()));
		};
		
		
		imageProperty.addListener(updateRed);
		imageProperty.addListener(updateGreen);
		imageProperty.addListener(updateBlue);
		imageProperty.addListener((o, oldVal, newVal) -> {
			this.bitDepth.set(newVal.getDepth());
		});
		
		properties.rowProperty().addListener(updateRed);
		properties.rowProperty().addListener(updateGreen);
		properties.rowProperty().addListener(updateBlue);
		
		properties.colProperty().addListener(updateRed);
		properties.colProperty().addListener(updateGreen);
		properties.colProperty().addListener(updateBlue);
		
		properties.selectedRedProperty().addListener(updateRed);
		properties.selectedGreenProperty().addListener(updateGreen);
		properties.selectedBlueProperty().addListener(updateBlue);
	}
	
	public IntegerProperty redPixelProperty() {
		return this.redPixel;
	}
	
	public IntegerProperty greenPixelProperty() {
		return this.greenPixel;
	}
	
	public IntegerProperty bluePixelProperty() {
		return this.bluePixel;
	}

	public IntegerProperty bitDepthProperty() {
		return this.bitDepth;
	}
	
}
