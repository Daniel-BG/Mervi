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
	
	private final NumberMatrix red = new NumberMatrix();
	private final NumberMatrix green = new NumberMatrix();
	private final NumberMatrix blue = new NumberMatrix();
	
	{
		red.changedProperty().addListener(e -> redraw());
		green.changedProperty().addListener(e -> redraw());
		blue.changedProperty().addListener(e -> redraw());
		this.widthProperty().addListener(e -> redraw());
		this.heightProperty().addListener(e -> redraw());
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
		
		int rows = red.rowsProperty().intValue();
		int cols = red.colsProperty().intValue();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		double width = this.getWidth();
		double height = this.getHeight();
		double sqrw = width / (double) cols;
		double sqrh = height / (double) rows;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.paintSquare(
						gc, 
						new Color(
								red.get(row, col), 
								green.get(row, col), 
								blue.get(row, col), 
								1), 
						col*sqrw, row*sqrh, sqrw, sqrh);
			}
		}
		
		System.out.println("Drawn: " + width + "," + height + "," + sqrw + "," + sqrh);
	}

	
	private void paintSquare(GraphicsContext gc, Color c, double x, double y, double w, double h) {
		gc.setFill(c);
		gc.fillRect(x, y, w, h);
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
