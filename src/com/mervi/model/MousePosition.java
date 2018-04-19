package com.mervi.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MousePosition {
	
	private final IntegerProperty col = new SimpleIntegerProperty();
	private final IntegerProperty row = new SimpleIntegerProperty();
	private final DoubleProperty xPos = new SimpleDoubleProperty();
	private final DoubleProperty yPos = new SimpleDoubleProperty();
	
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

}
