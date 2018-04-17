package com.mervi.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MousePosition {
	
	private final IntegerProperty col = new SimpleIntegerProperty();
	private final IntegerProperty row = new SimpleIntegerProperty();
	
	public IntegerProperty colProperty() {
		return this.col;
	}
	
	public IntegerProperty rowProperty() {
		return this.row;
	}

}
