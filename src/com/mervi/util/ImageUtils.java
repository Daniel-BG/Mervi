package com.mervi.util;

import com.mervi.view.NumberMatrix;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageUtils {

	
	public static Image imageFromColorArrays(NumberMatrix red, NumberMatrix green, NumberMatrix blue) {
		//check size
		if (!red.sizeEquals(green) || !red.sizeEquals(blue))
			throw new IllegalArgumentException("Matrices must be of the same size");
		
		int rows = red.rowsProperty().intValue();
		int cols = red.colsProperty().intValue();
		
		WritableImage wi = new WritableImage(cols, rows);
		PixelWriter pw = wi.getPixelWriter();
				
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				pw.setColor(col, row, new Color(red.get(row, col), green.get(row, col), blue.get(row, col), 1));
			}
		}
		
		return wi;
	}
	
	
}
