package com.mervi.view;

import com.mervi.model.ReadOnlyMatrix;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Daniel
 * Implements a canvas that receives color matrices and
 * paints each pixel according to the corresponding color <br>
 * It is also able to resize dynamically
 */
public class ColorMatrixCanvas extends Canvas {
	
	//number of rows and columns currently drawn:
	int rows, cols;
	
	/**************/
	/** Controls **/
	
	/**
	 * Paint the matrix in this canvas
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void paintMatrix(ReadOnlyMatrix red, ReadOnlyMatrix green, ReadOnlyMatrix blue) {
		//size check
		if (!red.sizeEquals(green) || !red.sizeEquals(blue))
			throw new IllegalArgumentException("Matrices must be of the same size");
		
		this.rows = red.getRows();
		this.cols = red.getCols();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		double width = this.getWidth();
		double height = this.getHeight();
		double sqrw = width / (double) cols;
		double sqrh = height / (double) rows;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.paintSquare(
						gc, 
						new Color(red.get(i, j), green.get(i, j), blue.get(i, j), 1), 
						i*sqrw, j*sqrh, sqrw, sqrh);
			}
		}
	}
	
	private void paintSquare(GraphicsContext gc, Color c, double x, double y, double w, double h) {
		gc.setFill(c);
		gc.fillRect(x, y, w, h);
	}
	/**************/

}
