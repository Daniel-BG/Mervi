package com.mervi.model.observers;

import java.util.ArrayList;

public class ParametrizedObservable<T> {
	/**
	 * Adds an observer to the observable object only if it wasn't already added
	 * @param observer
	 */
	public void addObserver(T observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}
	/**
	 * Removes the specified observer, if present, from the observable object
	 * @param observer
	 */
	public void removeObserver(T observer) {
		observers.remove(observer);
	}
	
	
	protected ArrayList<T> observers = new ArrayList<T>();
}
