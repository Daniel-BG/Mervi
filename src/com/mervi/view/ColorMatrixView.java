package com.mervi.view;

import com.mervi.util.ImageUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Daniel
 * Implements a canvas that receives color matrices and
 * paints each pixel according to the corresponding color <br>
 * It is also able to resize dynamically
 */
public class ColorMatrixView extends Group {
	
	private final NumberMatrix red = new NumberMatrix();
	private final NumberMatrix green = new NumberMatrix();
	private final NumberMatrix blue = new NumberMatrix();
	
	private ImageView imageViewRed = new ImageView();
	private ImageView imageViewGreen = new ImageView();
	private ImageView imageViewBlue = new ImageView();
	
	{
		red.changedProperty().addListener(e -> redraw());
		green.changedProperty().addListener(e -> redraw());
		blue.changedProperty().addListener(e -> redraw());
		imageViewRed.setBlendMode(BlendMode.RED);
		imageViewGreen.setBlendMode(BlendMode.GREEN);
		imageViewBlue.setBlendMode(BlendMode.BLUE);
		this.getChildren().addAll(imageViewRed, imageViewGreen, imageViewBlue);
		this.setCache(true);
	}
	
	/**************/
	/** Controls **/
	
	/**
	 * Paint the matrix in this canvas
	 * @param red
	 * @param green
	 * @param blue
	 */
	private void redraw() {
		//size check
		if (!red.sizeEquals(green) || !red.sizeEquals(blue))
			return;
		
		imageViewRed.setImage(red.getImage());
		imageViewGreen.setImage(green.getImage());
		imageViewBlue.setImage(blue.getImage());
	}
	/**************/
	
	public NumberMatrix getRedProperty() {
		return this.red;
	}
	
	public NumberMatrix getGreenProperty() {
		return this.green;
	}

	public NumberMatrix getBlueProperty() {
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
