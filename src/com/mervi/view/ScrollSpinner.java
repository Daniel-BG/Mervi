package com.mervi.view;

import com.mervi.Config;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.SwipeEvent;

/**
 * @author Daniel
 * Fires Swipe up and down events when scrolling
 * @param <T>
 */
public class ScrollSpinner<T> extends Spinner<T> {
	
	public ScrollSpinner(int units) {
		this.setOnScroll(e -> {
			if (e.getDeltaY() > 0) {
				this.fireEvent(new SwipeEvent(SwipeEvent.SWIPE_UP, 0, 0, 0, 0, false, false, false, false, false, 0, null));
			}
			else {
				this.fireEvent(new SwipeEvent(SwipeEvent.SWIPE_DOWN, 0, 0, 0, 0, false, false, false, false, false, 0, null));
			}
		});
	}
	
	public ScrollSpinner(SpinnerValueFactory<T> factory) {
		this(Config.SCROLL_UNITS);
		this.setValueFactory(factory);
		
		this.onSwipeUpProperty().addListener(e -> factory.increment(1));
		this.onSwipeDownProperty().addListener(e -> factory.decrement(1));
	}
	
	public ScrollSpinner() {
		this(Config.SCROLL_UNITS);
	}


}


/**if (e.getDeltaY() > 0) t.increment(((int) e.getDeltaY()) / Config.SCROLL_UNITS); 
else t.decrement((-(int) e.getDeltaY()) / Config.SCROLL_UNITS);*/