package com.mervi.view;

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
	
	private ImageView imageViewRed = new ImageView();
	private ImageView imageViewGreen = new ImageView();
	private ImageView imageViewBlue = new ImageView();
	
	{
		imageViewRed.setBlendMode(BlendMode.RED);
		imageViewGreen.setBlendMode(BlendMode.GREEN);
		imageViewBlue.setBlendMode(BlendMode.BLUE);
		this.getChildren().addAll(imageViewRed, imageViewGreen, imageViewBlue);
		this.setCache(true);
	}
	
	/**************/
	/** Controls **/
	public void setRedComponent(Image red) {
		imageViewRed.setImage(red);
	}
	
	public void setGreenComponent(Image green) {
		imageViewGreen.setImage(green);
	}
	
	public void setBlueComponent(Image blue) {
		imageViewBlue.setImage(blue);
	}
	
	public void bindDimensionsTo(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
		this.imageViewBlue.fitWidthProperty().bind(widthProperty);
		this.imageViewGreen.fitWidthProperty().bind(widthProperty);
		this.imageViewRed.fitWidthProperty().bind(widthProperty);
		this.imageViewBlue.fitHeightProperty().bind(heightProperty);
		this.imageViewGreen.fitHeightProperty().bind(heightProperty);
		this.imageViewRed.fitHeightProperty().bind(heightProperty);
	}
}
