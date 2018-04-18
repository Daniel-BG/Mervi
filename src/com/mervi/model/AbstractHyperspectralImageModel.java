package com.mervi.model;

import com.mervi.control.ObjectChangedProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AbstractHyperspectralImageModel {

	private IntegerProperty bands, rows, cols;
	private ObjectChangedProperty modelChanged; //updated when the values change
	private ReadOnlyMatrix[] bandCache;
	
	{
		this.bands = new SimpleIntegerProperty();
		this.rows = new SimpleIntegerProperty();
		this.cols = new SimpleIntegerProperty();
	
		this.modelChanged = new ObjectChangedProperty();
		
		this.modelChanged.addListener(e -> {
			bandCache = new ReadOnlyMatrix[this.bandsProperty().intValue()];
		});
	}
	
	public IntegerProperty rowsProperty() {
		return this.rows;
	}
	
	public IntegerProperty colsProperty() {
		return this.cols;
	}
	
	public IntegerProperty bandsProperty() {
		return this.bands;
	}
	
	public ObjectChangedProperty modelChangedProperty() {
		return this.modelChanged;
	}
	
	public abstract int getValue(int band, int row, int col);
	
	public abstract int getRange();
	
	public final ReadOnlyMatrix getBand(int index) {
		if (this.bandCache[index] == null)
			this.bandCache[index] = doGetBand(index);
		
		return this.bandCache[index];
	}
	
	public abstract ReadOnlyMatrix doGetBand(int index);
	
	public abstract boolean available();
	
}
