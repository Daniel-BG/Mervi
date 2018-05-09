package com.mervi.view.images;

import javafx.beans.property.ObjectProperty;
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
public class RGBImageView extends Group {
	
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
	public void bindDimensionsTo(ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
		this.imageViewBlue.fitWidthProperty().bind(widthProperty);
		this.imageViewGreen.fitWidthProperty().bind(widthProperty);
		this.imageViewRed.fitWidthProperty().bind(widthProperty);
		this.imageViewBlue.fitHeightProperty().bind(heightProperty);
		this.imageViewGreen.fitHeightProperty().bind(heightProperty);
		this.imageViewRed.fitHeightProperty().bind(heightProperty);
	}
	
	
	public ObjectProperty<Image> redImageProperty() {
		return this.imageViewRed.imageProperty();
	}
	
	public ObjectProperty<Image> greenImageProperty() {
		return this.imageViewGreen.imageProperty();
	}
	
	public ObjectProperty<Image> blueImageProperty() {
		return this.imageViewBlue.imageProperty();
	}
	
	
}
