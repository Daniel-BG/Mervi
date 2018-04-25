package com.mervi.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ProgramProperties {
	
	private final IntegerProperty col = new SimpleIntegerProperty();
	private final IntegerProperty row = new SimpleIntegerProperty();
	private final DoubleProperty xPos = new SimpleDoubleProperty();
	private final DoubleProperty yPos = new SimpleDoubleProperty();
	private final IntegerProperty redIndex = new SimpleIntegerProperty();
	private final IntegerProperty blueIndex = new SimpleIntegerProperty();
	private final IntegerProperty greenIndex = new SimpleIntegerProperty();
	private final IntegerProperty minIndex = new SimpleIntegerProperty();
	private final IntegerProperty maxIndex = new SimpleIntegerProperty();
	
	
	public IntegerProperty colProperty() {
		return this.col;
	}
	
	public IntegerProperty rowProperty() {
		return this.row;
	}
	
	public DoubleProperty xPosProperty() {
		return this.xPos;
	}
	
	public DoubleProperty yPosProperty() {
		return this.yPos;
	}
	
	public IntegerProperty redIndexProperty() {
		return this.redIndex;
	}
	
	public IntegerProperty greenIndexProperty() {
		return this.greenIndex;
	}
	
	public IntegerProperty blueIndexProperty() {
		return this.blueIndex;
	}
	
	public IntegerProperty minIndexProperty() {
		return this.minIndex;
	}
	
	public IntegerProperty maxIndexProperty() {
		return this.maxIndex;
	}

}
