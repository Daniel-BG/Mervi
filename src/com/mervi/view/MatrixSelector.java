package com.mervi.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MatrixSelector extends SelfResizingCanvas {
	
	int rows = -1, cols = -1;
	
	
	public void setMatrixSize(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}
	
	public void overlayOn(int xpos, int ypos) {
		if (xpos < 0 || xpos > this.cols || ypos < 0 || ypos > this.rows)
			throw new IllegalArgumentException("x and/or y out of bounds or MatrixSelector not initialized");
		
		double width = this.getWidth();
		double height = this.getHeight();
		double sqrw = width / (double) cols;
		double sqrh = height / (double) rows;
		
		GraphicsContext gc = this.getGraphicsContext2D();
		
		gc.clearRect(0, 0, width, height);
		gc.setFill(Color.TRANSPARENT);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3.0);
		gc.strokeRect(xpos*sqrw, ypos*sqrh, sqrw, sqrh);
	}
}
