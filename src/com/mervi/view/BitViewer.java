package com.mervi.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BitViewer extends HBox {
	
    IntegerBinaryViewer ibvOrigRed = new IntegerBinaryViewer(16);
    IntegerBinaryViewer ibvOrigBlue = new IntegerBinaryViewer(16);
    IntegerBinaryViewer ibvOrigGreen = new IntegerBinaryViewer(16);
    
    IntegerProperty bitDepthProperty = new SimpleIntegerProperty(16);
	
	public BitViewer(String text) {
		
		this.setSpacing(10);
		this.getChildren().addAll(ibvOrigRed, ibvOrigGreen, ibvOrigBlue, new Label(text));
		
		this.ibvOrigRed.bitNumberProperty().bind(bitDepthProperty);
		this.ibvOrigGreen.bitNumberProperty().bind(bitDepthProperty);
		this.ibvOrigBlue.bitNumberProperty().bind(bitDepthProperty);
	}
	
	
	public IntegerProperty redValueProperty() {
		return this.ibvOrigRed.integerValueProperty();
	}
	
	public IntegerProperty greenValueProperty() {
		return this.ibvOrigGreen.integerValueProperty();
	}
	
	public IntegerProperty blueValueProperty() {
		return this.ibvOrigBlue.integerValueProperty();
	}
	
	public IntegerProperty bitDepthProperty() {
		return this.bitDepthProperty;
	}
}
