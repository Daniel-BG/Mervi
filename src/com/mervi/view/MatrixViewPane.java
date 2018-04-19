package com.mervi.view;

import javafx.scene.layout.Pane;

public class MatrixViewPane extends Pane  {

	ColorMatrixView cmc;
	MatrixSelector ms;
	
	/**
	 * Create two layers (one canvas, one selector)
	 * ensuring the selector is placed in front of
	 * the canvas
	 */
	public MatrixViewPane () {
		cmc = new ColorMatrixView();
		ms = new MatrixSelector();
		
		cmc.bindDimensionsTo(this.widthProperty(), this.heightProperty());
		
		ms.widthProperty().bind(this.widthProperty());
		ms.heightProperty().bind(this.heightProperty());
		
		this.setPrefSize(4096, 4096);
	    this.getChildren().addAll(this.getCanvas(), this.getSelector());
	    this.getSelector().toFront();
	}
	
	
	public ColorMatrixView getCanvas() {
		return cmc;
	}
	
	public MatrixSelector getSelector() {
		return ms;
	}
	
}
