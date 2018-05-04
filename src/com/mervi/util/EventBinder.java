package com.mervi.util;

import org.controlsfx.control.RangeSlider;

import javafx.scene.Node;
import javafx.scene.input.SwipeEvent;

public class EventBinder {

	/**
	 * Will fire swipe up or down events when scrolling
	 * @param n
	 */
	public static void addSwipeWhenScrolling(Node n) {
		n.setOnScroll(e -> {
			if (e.getDeltaY() > 0) {
				n.fireEvent(new SwipeEvent(SwipeEvent.SWIPE_UP, 0, 0, 0, 0, false, false, false, false, false, 0, null));
			}
			else {
				n.fireEvent(new SwipeEvent(SwipeEvent.SWIPE_DOWN, 0, 0, 0, 0, false, false, false, false, false, 0, null));
			}
		});
	}
	
	
	
	public static void moveIntervalWhenSwipe(RangeSlider rs) {
		rs.setOnSwipeDown(e -> {
			if (rs.getBlockIncrement() > rs.getLowValue())
				return;
			
			rs.decrementLowValue();
			rs.decrementHighValue();
		});
		
		rs.setOnSwipeUp(e -> {
			if (rs.getBlockIncrement() > rs.getMax() - rs.getHighValue())
				return;
			
			rs.incrementHighValue();
			rs.incrementLowValue();
		});
	}
	
}
