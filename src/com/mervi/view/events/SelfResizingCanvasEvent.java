package com.mervi.view.events;

import javafx.event.Event;
import javafx.event.EventType;

public class SelfResizingCanvasEvent extends Event {

	/** */
	private static final long serialVersionUID = -4930341151042270822L;
	
	public final double width, height;
	
	public static final EventType<SelfResizingCanvasEvent> RESIZE = new EventType<>("RESIZE");
	
	public SelfResizingCanvasEvent(EventType<? extends Event> eventType, double width, double height) {
		super(eventType);
		this.width = width;
		this.height = height;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + width + "," + height;
	}

}
