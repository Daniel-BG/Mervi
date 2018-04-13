package com.mervi.view;

import com.mervi.view.events.SelfResizingCanvasEvent;

import javafx.event.Event;
import javafx.scene.canvas.Canvas;

public abstract class SelfResizingCanvas extends Canvas {

	private double minWidth, minHeight, maxWidth, maxHeight;
	
	public SelfResizingCanvas(double minWidth, double minHeight, double maxWidth, double maxHeight) {
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}
	
	public SelfResizingCanvas() {
		this(64, 64, 4096, 4096);
	}
	

	/*****************/
	/** Auto resize **/
	@Override
	public double minHeight(double width)
	{
	    return this.minHeight;
	}

	@Override
	public double maxHeight(double width)
	{
	    return this.maxHeight;
	}

	@Override
	public double prefHeight(double width)
	{
	    return minHeight(width);
	}

	@Override
	public double minWidth(double height)
	{
	    return this.minWidth;
	}

	@Override
	public double maxWidth(double height)
	{
	    return this.maxWidth;
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
	    
	    Event event = new SelfResizingCanvasEvent(SelfResizingCanvasEvent.RESIZE, width, height);
		this.fireEvent(event);
	}
	/****************/

}
