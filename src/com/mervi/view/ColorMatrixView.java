package com.mervi.view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;

/**
 * @author Daniel
 * Implements a canvas that receives color matrices and
 * paints each pixel according to the corresponding color <br>
 * It is also able to resize dynamically
 */
public class ColorMatrixView extends Group {
	
	private final HyperspectralBandNotifier red = new HyperspectralBandNotifier();
	private final HyperspectralBandNotifier green = new HyperspectralBandNotifier();
	private final HyperspectralBandNotifier blue = new HyperspectralBandNotifier();
	
	private ImageView imageViewRed = new ImageView();
	private ImageView imageViewGreen = new ImageView();
	private ImageView imageViewBlue = new ImageView();
	
	{
		red.changedProperty().addListener(e -> imageViewRed.setImage(red.getImage()));
		green.changedProperty().addListener(e -> imageViewGreen.setImage(green.getImage()));
		blue.changedProperty().addListener(e -> imageViewBlue.setImage(blue.getImage()));
		imageViewRed.setBlendMode(BlendMode.RED);
		imageViewGreen.setBlendMode(BlendMode.GREEN);
		imageViewBlue.setBlendMode(BlendMode.BLUE);
		this.getChildren().addAll(imageViewRed, imageViewGreen, imageViewBlue);
		this.setCache(true);
	}
	
	/**************/
	/** Controls **/
	
	public HyperspectralBandNotifier getRedProperty() {
		return this.red;
	}
	
	public HyperspectralBandNotifier getGreenProperty() {
		return this.green;
	}

	public HyperspectralBandNotifier getBlueProperty() {
		return this.blue;
	}
	
	public void bindWidthTo(ReadOnlyDoubleProperty widthProperty) {
		this.imageViewBlue.fitWidthProperty().bind(widthProperty);
		this.imageViewGreen.fitWidthProperty().bind(widthProperty);
		this.imageViewRed.fitWidthProperty().bind(widthProperty);
	}
	
	public void bindHeightTo(ReadOnlyDoubleProperty heightProperty) {
		this.imageViewBlue.fitHeightProperty().bind(heightProperty);
		this.imageViewGreen.fitHeightProperty().bind(heightProperty);
		this.imageViewRed.fitHeightProperty().bind(heightProperty);
	}
}
