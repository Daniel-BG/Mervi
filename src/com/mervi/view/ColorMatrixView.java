package com.mervi.view;

import com.mervi.util.ImageUtils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Daniel
 * Implements a canvas that receives color matrices and
 * paints each pixel according to the corresponding color <br>
 * It is also able to resize dynamically
 */
public class ColorMatrixView extends ImageView {
	
	private final NumberMatrix red = new NumberMatrix();
	private final NumberMatrix green = new NumberMatrix();
	private final NumberMatrix blue = new NumberMatrix();
	
	{
		red.changedProperty().addListener(e -> redraw());
		green.changedProperty().addListener(e -> redraw());
		blue.changedProperty().addListener(e -> redraw());
		this.setSmooth(false);
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
		
		Image im = ImageUtils.imageFromColorArrays(red, green, blue);
		
		this.setImage(im);
		
		System.out.println("Redrawn: " + im.getWidth() + "," + im.getHeight());
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
}
