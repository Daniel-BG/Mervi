package com.mervi.util;

import javafx.beans.property.SimpleBooleanProperty;

public class ObjectChangedProperty extends SimpleBooleanProperty {
	public ObjectChangedProperty() {
		this.set(true);
	}
	
	/**
	 * Update this property so that a notification is sent to all listeners
	 */
	public void update() {
		this.set(!this.get());
	}
}
