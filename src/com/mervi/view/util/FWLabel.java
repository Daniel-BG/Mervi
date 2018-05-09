package com.mervi.view.util;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class FWLabel extends Label {

	public FWLabel(String text) {
		super(text);
		
		final Text renderedText = new Text(text);
	    new Scene(new Group(renderedText));
	    renderedText.applyCss(); 
	    final double width = renderedText.getLayoutBounds().getWidth();

		this.setMinWidth(width);
	}
	
}
