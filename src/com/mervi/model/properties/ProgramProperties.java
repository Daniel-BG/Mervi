package com.mervi.model.properties;

import com.mervi.model.HyperspectralDiffModel;
import com.mervi.model.HyperspectralImageModel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class ProgramProperties {
	
	/** Currently selected column */
	private final IntegerProperty col = new SimpleIntegerProperty();
	/** Currently selected row */
	private final IntegerProperty row = new SimpleIntegerProperty();
	/** Maximum value for column */
	private final IntegerProperty maxCol = new SimpleIntegerProperty();
	/** Maximum value for row */
	private final IntegerProperty maxRow = new SimpleIntegerProperty();
	
	/** Value factory for the red band */
	private final IntegerSpinnerValueFactory valueFactoryRed = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerProperty selectedRed = new SimpleIntegerProperty();
	/** Value factory for the green band */
	private final IntegerSpinnerValueFactory valueFactoryGreen = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerProperty selectedGreen = new SimpleIntegerProperty();
	/** Value factory for the blue band */
	private final IntegerSpinnerValueFactory valueFactoryBlue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerProperty selectedBlue = new SimpleIntegerProperty();
	/** Value factory for the generic band (control all of the others) */
	private final IntegerSpinnerValueFactory valueFactoryAll = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerProperty selectedAll = new SimpleIntegerProperty();

	/** Maximum value for the band */
	private final IntegerProperty maxIndex = new SimpleIntegerProperty();
	
	/** Original image property */
	private final SimpleObjectProperty<HyperspectralImageModel> originalImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	/** Compressed image property */
	private final SimpleObjectProperty<HyperspectralImageModel> compressedImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	/** Comparable image property */
	private final SimpleObjectProperty<HyperspectralImageModel> comparableImageProperty = new SimpleObjectProperty<HyperspectralImageModel>();
	
	
	public ProgramProperties() {
		valueFactoryRed.minProperty().set(0);
		valueFactoryRed.maxProperty().bind(maxIndexProperty());
		valueFactoryGreen.minProperty().set(0);
		valueFactoryGreen.maxProperty().bind(maxIndexProperty());
		valueFactoryBlue.minProperty().set(0);
		valueFactoryBlue.maxProperty().bind(maxIndexProperty());
		valueFactoryAll.minProperty().set(0);
		valueFactoryAll.maxProperty().bind(maxIndexProperty());
		
		//bind values to full controller
        valueFactoryAll.valueProperty().addListener((e, oldVal, newVal) -> {
        	valueFactoryBlue.setValue(newVal);
        	valueFactoryRed.setValue(newVal);
        	valueFactoryGreen.setValue(newVal);
        });
        
        originalImageProperty.addListener((e, oldVal, newVal) -> {
        	maxIndex.set(newVal.getBands() - 1);
        	maxCol.set(newVal.getCols());
        	maxRow.set(newVal.getRows());
        });
        
        InvalidationListener createNewCompImage = e -> {
        	HyperspectralImageModel a = originalImageProperty.getValue();
        	HyperspectralImageModel b = compressedImageProperty.getValue();
        	
        	if (a == null || b == null || !a.sizeEquals(b)) {
        		return;
        	}
        	
        	HyperspectralDiffModel hdm = new HyperspectralDiffModel(originalImageProperty.getValue(), compressedImageProperty.getValue());
        	comparableImageProperty.set(hdm);
        };
        
        originalImageProperty.addListener(createNewCompImage);
        compressedImageProperty.addListener(createNewCompImage);
        
        
        selectedRed.bind(valueFactoryRed.valueProperty());
        selectedGreen.bind(valueFactoryGreen.valueProperty());
        selectedBlue.bind(valueFactoryBlue.valueProperty());
        selectedAll.bind(valueFactoryAll.valueProperty());
	}
	
	
	public IntegerProperty colProperty() {
		return this.col;
	}
	
	public IntegerProperty rowProperty() {
		return this.row;
	}
	
	public IntegerProperty maxColProperty() {
		return this.maxCol;
	}
	
	public IntegerProperty maxRowProperty() {
		return this.maxRow;
	}
	
	public IntegerProperty selectedRedProperty() {
		return this.selectedRed;
	}
	
	public IntegerProperty selectedGreenProperty() {
		return this.selectedGreen;
	}
	
	public IntegerProperty selectedBlueProperty() {
		return this.selectedBlue;
	}
	
	public IntegerProperty selectedAllProperty() {
		return this.selectedAll;
	}
	
	public IntegerSpinnerValueFactory valueFactoryRedProperty() {
		return this.valueFactoryRed;
	}
	
	public IntegerSpinnerValueFactory valueFactoryGreenProperty() {
		return this.valueFactoryGreen;
	}
	
	public IntegerSpinnerValueFactory valueFactoryBlueProperty() {
		return this.valueFactoryBlue;
	}
	
	public IntegerSpinnerValueFactory valueFactoryAllProperty() {
		return this.valueFactoryAll;
	}
	
	public IntegerProperty maxIndexProperty() {
		return this.maxIndex;
	}
	
	public ObjectProperty<HyperspectralImageModel> originalImageProperty() {
		return this.originalImageProperty;
	}
	
	public ObjectProperty<HyperspectralImageModel> compressedImageProperty() {
		return this.compressedImageProperty;
	}
	
	public ObjectProperty<HyperspectralImageModel> comparableImageProperty() {
		return this.comparableImageProperty;
	}

}
