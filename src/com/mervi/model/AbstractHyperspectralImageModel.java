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
			this.average = null;
			this.variance = null;
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
	
	
	public boolean sizeEquals(AbstractHyperspectralImageModel other) {
		return this.rowsProperty().intValue() == other.rowsProperty().intValue()
				&& this.colsProperty().intValue() == other.colsProperty().intValue()
				&& this.bandsProperty().intValue() == other.bandsProperty().intValue()
				&& this.getRange() == other.getRange();
	}
	
	/** precalculated values for metrics */
	private Double average;
	private Double variance;
	
	public double variance() {
		if (variance != null)
			return variance;
		
		double localavg = this.average();

		double acc = 0;
		for (int band = 0; band < bandsProperty().intValue(); band++) {
			for (int row = 0; row < rowsProperty().intValue(); row++) {
				for (int col = 0; col < colsProperty().intValue(); col++) {
					double val = (double) this.getValue(band, row, col) - localavg;
					acc += val * val;
				}
			}	
		}
		
		return variance = acc / ((double) bandsProperty().intValue() * rowsProperty().intValue() * colsProperty().intValue());
	}
	
	public double average() {
		if (average != null)
			return average;
		
		double acc = 0;
		
		for (int band = 0; band < bandsProperty().intValue(); band++) {
			for (int row = 0; row < rowsProperty().intValue(); row++) {
				for (int col = 0; col < colsProperty().intValue(); col++) {
					acc += (double) this.getValue(band, row, col);
				}
			}	
		}
		
		return average = acc / ((double) bandsProperty().intValue() * rowsProperty().intValue() * colsProperty().intValue());
	}
	
}
