package com.mervi.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class IntegerBinaryViewer extends Label {
	
	private final IntegerProperty value = new SimpleIntegerProperty(0);
	private final IntegerProperty bits = new SimpleIntegerProperty(0);
	
	public IntegerBinaryViewer(int bits) {
		this.bits.setValue(bits);
		
		this.value.addListener(e -> updateText());
		this.bits.addListener(e -> updateText());
	}
	
	private void updateText() {
		String text = "";
		for (int i = bits.intValue() - 1; i >= 0 ; i--) {
			text += ((value.intValue() >> i) & 0x1) == 0x1 ? "1" : "0";
		}
		this.setText(text);
	}
	
	
	public IntegerProperty integerValueProperty() {
		return this.value;
	}
	
	public IntegerProperty bitNumberProperty() {
		return this.bits;
	}

}
