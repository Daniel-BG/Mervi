package com.mervi.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MatrixSelector extends Canvas {
	
	int rows = -1, cols = -1;
	
	
	public void setMatrixSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}
	
	public void overlayOn(int xpos, int ypos) {
		if (xpos < 0 || xpos > this.cols || ypos < 0 || ypos > this.rows)
			throw new IllegalArgumentException("x and/or y out of bounds or MatrixSelector not initialized");
		
		this.removeOverlay();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		
		double sqrw = this.getWidth() / (double) cols;
		double sqrh = this.getHeight() / (double) rows;
		
		gc.setFill(Color.TRANSPARENT);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3.0);
		gc.strokeRect(xpos*sqrw, ypos*sqrh, sqrw, sqrh);
	}
	
	public void removeOverlay() {
		this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
	}
}
