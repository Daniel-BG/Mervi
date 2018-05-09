package com.mervi.view;

import com.mervi.util.EventBinder;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.SwipeEvent;

/**
 * @author Daniel
 * Fires Swipe up and down events when scrolling
 * @param <T>
 */
public class ScrollSpinner<T> extends Spinner<T> {
	
	{
		EventBinder.changeValueWhenSwiping(this);
		EventBinder.addSwipeWhenScrolling(this);
	}
	
	public ScrollSpinner(SpinnerValueFactory<T> factory) {
		this.setValueFactory(factory);
	}

}


/**if (e.getDeltaY() > 0) t.increment(((int) e.getDeltaY()) / Config.SCROLL_UNITS); 
else t.decrement((-(int) e.getDeltaY()) / Config.SCROLL_UNITS);*/