package com.mervi.view.data;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class CoordinateLabel extends Label {
	
	private IntegerProperty row = new SimpleIntegerProperty();
	private IntegerProperty col = new SimpleIntegerProperty();
	
	public CoordinateLabel() {
		super(" @(x, x)");
		
        InvalidationListener mpChangedCoord = e -> {
        	this.setText(" @: (" + rowProperty().intValue() + "," + colProperty().intValue() + ")");
        };
        colProperty().addListener(mpChangedCoord);
        rowProperty().addListener(mpChangedCoord);
        
        this.setMinWidth(80);
	}
	
	public IntegerProperty rowProperty() {
		return this.row;
	}
	
	public IntegerProperty colProperty() {
		return this.col;
	}

}
