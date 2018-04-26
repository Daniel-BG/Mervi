package com.mervi.view;

import com.mervi.control.ProgramController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MatrixSelector extends Canvas {
	
	private final IntegerProperty numRows;
	private final IntegerProperty numCols;
	private final IntegerProperty selectedRow;
	private final IntegerProperty selectedCol;
	
	public MatrixSelector(ProgramController sc) {
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
		
		this.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
			double relxpos = e.getSceneX() / getWidth();
			double relypos = e.getSceneY() / getHeight();
			
			sc.selectionOn(relxpos, relypos);
		});
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
		gc.setLineWidth(2.0);
		gc.strokeRect(this.selectedCol.intValue()*sqrw, this.selectedRow.intValue()*sqrh, sqrw, sqrh);
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(1.0);
		gc.strokeRect(this.selectedCol.intValue()*sqrw, this.selectedRow.intValue()*sqrh, sqrw, sqrh);
		
		/* COOL but mostly useless code
		int SELECT_SIZE = 3;
		int AUGMENT_VAL = 10;
	
		MatrixViewPane parent = (MatrixViewPane) this.getParent();
		double width = parent.widthProperty().doubleValue();
		double height = parent.heightProperty().doubleValue();
		double xpos = this.selectedXPos.doubleValue() * width;
		double ypos = this.selectedYPos.doubleValue() * height;
		
		double xposTL = Math.max(0, xpos - SELECT_SIZE);
		double yposTL = Math.max(0, ypos - SELECT_SIZE);
		double xposBR = Math.min(width, xpos + SELECT_SIZE);
		double yposBR = Math.min(height, ypos + SELECT_SIZE);
		double pixelsRight = width - xpos;
		double pixelsBottom = height - ypos;

		SnapshotParameters sp = new SnapshotParameters();
		sp.setViewport(new Rectangle2D(xposTL, yposTL, xposBR-xposTL, yposBR-yposTL)); //fill with proper coordinates
		WritableImage wi = new WritableImage((int) (xposBR - xposTL), (int) (yposBR - yposTL)); //fill with proper coordinates
		this.getParent().snapshot(sp, wi);	//get the snapshot
		
		//resize the image
		WritableImage wiScaled = new WritableImage((int) (wi.getWidth() * AUGMENT_VAL), (int) (wi.getHeight() * AUGMENT_VAL));
		double centerx = (xpos - xposTL) * AUGMENT_VAL, centery = (ypos - yposTL) * AUGMENT_VAL;
		for (int i = 0; i < wiScaled.getWidth(); i++) {
			for (int j = 0; j < wiScaled.getHeight(); j++) {
				if (Math.sqrt((Math.abs(i - centerx) * Math.abs(i - centerx) + Math.abs(j - centery) * Math.abs(j - centery))) < SELECT_SIZE * AUGMENT_VAL)
					wiScaled.getPixelWriter().setArgb(i, j, wi.getPixelReader().getArgb(i / AUGMENT_VAL, j / AUGMENT_VAL));
			}
		}

		gc.drawImage(wiScaled, xpos - (xpos - xposTL) * AUGMENT_VAL, ypos - (ypos - yposTL) * AUGMENT_VAL);
		gc.strokeRect(this.selectedCol.intValue()*sqrw, this.selectedRow.intValue()*sqrh, sqrw, sqrh);*/
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
