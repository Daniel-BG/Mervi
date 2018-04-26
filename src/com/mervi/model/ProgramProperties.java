package com.mervi.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class ProgramProperties {
	
	private final IntegerProperty col = new SimpleIntegerProperty();
	private final IntegerProperty row = new SimpleIntegerProperty();
	private final IntegerProperty maxCol = new SimpleIntegerProperty();
	private final IntegerProperty maxRow = new SimpleIntegerProperty();
	
	private final IntegerSpinnerValueFactory valueFactoryRed = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerSpinnerValueFactory valueFactoryGreen = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerSpinnerValueFactory valueFactoryBlue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
	private final IntegerSpinnerValueFactory valueFactoryAll = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);

	private final IntegerProperty minIndex = new SimpleIntegerProperty();
	private final IntegerProperty maxIndex = new SimpleIntegerProperty();
	
	
	public ProgramProperties() {
		valueFactoryRed.minProperty().bind(minIndexProperty());
		valueFactoryRed.maxProperty().bind(maxIndexProperty());
		valueFactoryGreen.minProperty().bind(minIndexProperty());
		valueFactoryGreen.maxProperty().bind(maxIndexProperty());
		valueFactoryBlue.minProperty().bind(minIndexProperty());
		valueFactoryBlue.maxProperty().bind(maxIndexProperty());
		valueFactoryAll.minProperty().bind(minIndexProperty());
		valueFactoryAll.maxProperty().bind(maxIndexProperty());
		
		//bind values to full controller
        valueFactoryAll.valueProperty().addListener((e, oldVal, newVal) -> {
        	valueFactoryBlue.setValue(newVal);
        	valueFactoryRed.setValue(newVal);
        	valueFactoryGreen.setValue(newVal);
        });
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
	
	public IntegerProperty minIndexProperty() {
		return this.minIndex;
	}
	
	public IntegerProperty maxIndexProperty() {
		return this.maxIndex;
	}

}
