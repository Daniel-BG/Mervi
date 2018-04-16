package com.mervi.view;

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
	public void paintMatrix(float[][] red, float[][] green, float[][] blue) {
		
		//assume red, green and blue are all rectangular and of the same size
		this.rows = red.length;
		this.cols = red[0].length;
		
		GraphicsContext gc = this.getGraphicsContext2D();
		double width = this.getWidth();
		double height = this.getHeight();
		double sqrw = width / (double) cols;
		double sqrh = height / (double) rows;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.paintSquare(
						gc, 
						new Color(red[i][j], green[i][j], blue[i][j], 1), 
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
