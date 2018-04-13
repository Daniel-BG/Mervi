package com.mervi.view;

public class MatrixView  {

	ColorMatrixCanvas cmc;
	MatrixSelector ms;
	
	/**
	 * Create two layers (one canvas, one selector)
	 * ensuring the selector is placed in front of
	 * the canvas
	 */
	public MatrixView () {
		cmc = new ColorMatrixCanvas();
		ms = new MatrixSelector();
	}
	
	
	public ColorMatrixCanvas getCanvas() {
		return cmc;
	}
	
	public MatrixSelector getSelector() {
		return ms;
	}
	
}
