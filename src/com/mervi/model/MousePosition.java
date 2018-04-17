package com.mervi.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class MousePosition {
	
	private final IntegerProperty xpos = new SimpleIntegerProperty();
	private final IntegerProperty ypos = new SimpleIntegerProperty();
	
	public IntegerProperty xposProperty() {
		return this.xpos;
	}
	
	public IntegerProperty yposProperty() {
		return this.ypos;
	}

}
