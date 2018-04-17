package com.mervi.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MatrixSelector extends Canvas {
	
	private final IntegerProperty numRows;
	private final IntegerProperty numCols;
	private final IntegerProperty selectedRow;
	private final IntegerProperty selectedCol;
	
	{
		this.numCols = new SimpleIntegerProperty(0);
		this.numRows = new SimpleIntegerProperty(0);
		this.selectedCol = new SimpleIntegerProperty(0);
		this.selectedRow = new SimpleIntegerProperty(0);
		
		this.numCols.addListener(e -> redraw());
		this.numRows.addListener(e -> redraw());
		this.selectedCol.addListener(e -> redraw());
		this.selectedRow.addListener(e -> redraw());	
		this.widthProperty().addListener(e -> redraw());
		this.heightProperty().addListener(e -> redraw());
	}
	
	private void redraw() {
		this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
		if (this.numCols.intValue() == 0 || this.numRows.intValue() == 0)
			return;
		
		GraphicsContext gc = this.getGraphicsContext2D();
		
		double sqrw = this.getWidth() / this.numCols.doubleValue();
		double sqrh = this.getHeight() / this.numRows.doubleValue();
		
		gc.setFill(Color.TRANSPARENT);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3.0);
		gc.strokeRect(this.selectedCol.intValue()*sqrw, this.selectedRow.intValue()*sqrh, sqrw, sqrh);
	}

	public IntegerProperty numRowsProperty() {
		return this.numRows;
	}
	
	public IntegerProperty numColsProperty() {
		return this.numCols;
	}
	
	public IntegerProperty selectedRowProperty() {
		return this.selectedRow;
	}
	
	public IntegerProperty selectedColProperty() {
		return this.selectedCol;
	}
}
