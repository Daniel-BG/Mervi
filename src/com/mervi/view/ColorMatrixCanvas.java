package com.mervi.view;

import com.mervi.control.ColorMatrixCanvasControllerInterface;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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
	boolean initialized = false;
	
	ColorMatrixCanvasControllerInterface controller;
	
	
	/**
	 * Create this color matrix canvas and paint it
	 * @param red
	 * @param green
	 * @param blue
	 * @param rows
	 * @param cols
	 */
	public ColorMatrixCanvas() {
		super(); //resize will take care of this
		
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {				
				controller.mouseOn(event.getSceneX(), event.getSceneY(), getWidth(), getHeight());
			}
			
		});
	}
	
	
	/**
	 * @param controller the controller for this canvas
	 */
	public void setController(ColorMatrixCanvasControllerInterface controller) {
		this.controller = controller;
	}
	
	
	
	
	/**************/
	/** Controls **/
	
	/**
	 * Paint the matrix in this canvas
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void paintMatrix(double[][] red, double[][] green, double[][] blue) {
		if (!this.initialized)
			return; //just in case we don't want to paint on a 0x0 canvas
		
		//assume red, green and blue are all rectanguar and of the same size
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
	
	public void overlayOn(int xpos, int ypos) {
		if (xpos < 0 || xpos > this.cols || ypos < 0 || ypos > this.rows)
			throw new IllegalArgumentException("x and/or y out of bounds");
		
		double width = this.getWidth();
		double height = this.getHeight();
		double sqrw = width / (double) cols;
		double sqrh = height / (double) rows;
		
		this.paintSquare(this.getGraphicsContext2D(), Color.BLACK, xpos*sqrw, ypos*sqrh, sqrw, sqrh);
	}
	
	
	/**************/
	
	
	
	/*****************/
	/** Auto resize **/
	@Override
	public double minHeight(double width)
	{
	    return 64;
	}

	@Override
	public double maxHeight(double width)
	{
	    return 10000;
	}

	@Override
	public double prefHeight(double width)
	{
	    return minHeight(width);
	}

	@Override
	public double minWidth(double height)
	{
	    return 0;
	}

	@Override
	public double maxWidth(double height)
	{
	    return 10000;
	}

	@Override
	public boolean isResizable()
	{
	    return true;
	}

	@Override
	public void resize(double width, double height)
	{
	    super.setWidth(width);
	    super.setHeight(height);
	    this.initialized = true;
	    
	    if (controller != null)
	    	controller.requestRepaint();
	}
	/****************/

}
