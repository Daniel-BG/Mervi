package com.mervi.view;

import com.mervi.model.HyperspectralBandModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	
	private ImageView imageViewRed = new ImageView();
	private ImageView imageViewGreen = new ImageView();
	private ImageView imageViewBlue = new ImageView();
	
	private SimpleObjectProperty<HyperspectralBandModel> redBandProperty = new SimpleObjectProperty<HyperspectralBandModel>();
	private SimpleObjectProperty<HyperspectralBandModel> greenBandProperty = new SimpleObjectProperty<HyperspectralBandModel>();
	private SimpleObjectProperty<HyperspectralBandModel> blueBandProperty = new SimpleObjectProperty<HyperspectralBandModel>();
	
	{
		imageViewRed.setBlendMode(BlendMode.RED);
		imageViewGreen.setBlendMode(BlendMode.GREEN);
		imageViewBlue.setBlendMode(BlendMode.BLUE);
		this.getChildren().addAll(imageViewRed, imageViewGreen, imageViewBlue);
		this.setCache(true);
	}
	
	public ColorMatrixView() {
		this.redBandProperty().addListener((o, oldVal, newVal) -> imageViewRed.setImage(newVal.getStatistics().getImage()));
		this.greenBandProperty().addListener((o, oldVal, newVal) -> imageViewGreen.setImage(newVal.getStatistics().getImage()));
		this.blueBandProperty().addListener((o, oldVal, newVal) -> imageViewBlue.setImage(newVal.getStatistics().getImage()));
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
		return this.redBandProperty;
	}
	
	public ObjectProperty<HyperspectralBandModel> greenBandProperty() {
		return this.greenBandProperty;
	}
	
	public ObjectProperty<HyperspectralBandModel> blueBandProperty() {
		return this.blueBandProperty;
	}
	
	
}
