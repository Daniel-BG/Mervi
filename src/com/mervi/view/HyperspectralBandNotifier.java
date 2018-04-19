package com.mervi.view;

import com.mervi.control.ObjectChangedProperty;
import com.mervi.model.HyperspectralBandModel;

import javafx.scene.image.Image;

public class HyperspectralBandNotifier  {
	
	HyperspectralBandModel matrix;
	
	private final ObjectChangedProperty change = new ObjectChangedProperty();
	
	public void set(HyperspectralBandModel m) {
		this.matrix = m;
		this.change.update();
	}

	public ObjectChangedProperty changedProperty() {
		return this.change;
	}
	
	/**
	 * @param other
	 * @return true if both matrices are of the same size
	 */
	public boolean sizeEquals(HyperspectralBandNotifier other) {		
		return this.matrix != null && other.matrix != null &&
				this.matrix.sizeEquals(other.matrix);
	}

	public Image getImage() {
		return this.matrix.getImage();
	}
	
}