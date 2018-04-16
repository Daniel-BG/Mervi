package com.mervi.view;

import javafx.scene.layout.Pane;

public class MatrixViewPane extends Pane  {

	ColorMatrixCanvas cmc;
	MatrixSelector ms;
	
	/**
	 * Create two layers (one canvas, one selector)
	 * ensuring the selector is placed in front of
	 * the canvas
	 */
	public MatrixViewPane () {
		cmc = new ColorMatrixCanvas();
		ms = new MatrixSelector();
		
		cmc.widthProperty().bind(this.widthProperty());
		cmc.heightProperty().bind(this.heightProperty());
		ms.widthProperty().bind(this.widthProperty());
		ms.heightProperty().bind(this.heightProperty());
		
		
		this.setPrefSize(4096, 4096);
	    this.getChildren().addAll(this.getCanvas(), this.getSelector());
	    this.getSelector().toFront();
	}
	
	
	public ColorMatrixCanvas getCanvas() {
		return cmc;
	}
	
	public MatrixSelector getSelector() {
		return ms;
	}
	
}
