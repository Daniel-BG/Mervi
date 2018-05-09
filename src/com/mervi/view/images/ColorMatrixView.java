package com.mervi.view.images;

import com.mervi.model.HyperspectralBandModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;

/**
 * @author Daniel
 * Implements a canvas that receives color matrices and
 * paints each pixel according to the corresponding color <br>
 * It is also able to resize dynamically
 */
public class ColorMatrixView extends Group {
	
	private BandView imageViewRed = new BandView();
	private BandView imageViewGreen = new BandView();
	private BandView imageViewBlue = new BandView();
	
	{
		imageViewRed.setBlendMode(BlendMode.RED);
		imageViewGreen.setBlendMode(BlendMode.GREEN);
		imageViewBlue.setBlendMode(BlendMode.BLUE);
		this.getChildren().addAll(imageViewRed, imageViewGreen, imageViewBlue);
		this.setCache(true);
	}
	
	/**************/
	/** Controls **/
	public void bindDimensionsTo(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
		this.imageViewBlue.fitWidthProperty().bind(widthProperty);
		this.imageViewGreen.fitWidthProperty().bind(widthProperty);
		this.imageViewRed.fitWidthProperty().bind(widthProperty);
		this.imageViewBlue.fitHeightProperty().bind(heightProperty);
		this.imageViewGreen.fitHeightProperty().bind(heightProperty);
		this.imageViewRed.fitHeightProperty().bind(heightProperty);
	}
	
	
	public ObjectProperty<HyperspectralBandModel> redBandProperty() {
		return this.imageViewRed.bandProperty();
	}
	
	public ObjectProperty<HyperspectralBandModel> greenBandProperty() {
		return this.imageViewGreen.bandProperty();
	}
	
	public ObjectProperty<HyperspectralBandModel> blueBandProperty() {
		return this.imageViewBlue.bandProperty();
	}
	
	
}
