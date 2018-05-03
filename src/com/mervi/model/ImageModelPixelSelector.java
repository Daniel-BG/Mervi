package com.mervi.model;

import com.mervi.view.BitViewer;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public class ImageModelPixelSelector {

	private IntegerProperty redPixel = new SimpleIntegerProperty();
	private IntegerProperty greenPixel = new SimpleIntegerProperty();
	private IntegerProperty bluePixel = new SimpleIntegerProperty();
	
	private IntegerProperty bitDepth = new SimpleIntegerProperty();
	
	public ImageModelPixelSelector(
			ObjectProperty<HyperspectralImageModel> imageProperty, 
			ObservableValue<? extends Number> selectedRow,
			ObservableValue<? extends Number> selectedCol,
			ObservableValue<? extends Number> selectedR,
			ObservableValue<? extends Number> selectedG, 
			ObservableValue<? extends Number> selectedB) {
		
		InvalidationListener updateRed = e -> {
			if (imageProperty.getValue() != null)
				this.redPixel.setValue(imageProperty.getValue().getValue(
						selectedR.getValue().intValue(), 
						selectedRow.getValue().intValue(), 
						selectedCol.getValue().intValue()));
		};
		InvalidationListener updateGreen = e -> {
			if (imageProperty.getValue() != null)
				this.greenPixel.setValue(imageProperty.getValue().getValue(
						selectedG.getValue().intValue(), 
						selectedRow.getValue().intValue(), 
						selectedCol.getValue().intValue()));
		};
		InvalidationListener updateBlue = e -> {
			if (imageProperty.getValue() != null)
				this.bluePixel.setValue(imageProperty.getValue().getValue(
						selectedB.getValue().intValue(), 
						selectedRow.getValue().intValue(), 
						selectedCol.getValue().intValue()));
		};
		
		
		imageProperty.addListener(updateRed);
		imageProperty.addListener(updateGreen);
		imageProperty.addListener(updateBlue);
		imageProperty.addListener((o, oldVal, newVal) -> {
			this.bitDepth.set(newVal.getDepth());
		});
		
		selectedRow.addListener(updateRed);
		selectedRow.addListener(updateGreen);
		selectedRow.addListener(updateBlue);
		
		selectedCol.addListener(updateRed);
		selectedCol.addListener(updateGreen);
		selectedCol.addListener(updateBlue);
		
		selectedR.addListener(updateRed);
		selectedG.addListener(updateGreen);
		selectedB.addListener(updateBlue);
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
	
	public void bindTo(BitViewer bv) {
		bv.redValueProperty().bind(redPixel);
		bv.greenValueProperty().bind(greenPixel);
		bv.blueValueProperty().bind(bluePixel);
		
		bv.bitDepthProperty().bind(bitDepth);
	}
	
	
}
