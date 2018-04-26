package com.mervi.model.observers;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;

public class ObservableObjectValueImpl<T> implements ObservableObjectValue<T> {
		
	protected ArrayList<ChangeListener<? super T>> changeListeners = new ArrayList<ChangeListener<? super T>>();
	protected ArrayList<InvalidationListener> invalidationListeners = new ArrayList<InvalidationListener>();
	
	private T elem;

	@Override
	public void addListener(ChangeListener<? super T> listener) {
		changeListeners.remove(listener);
		changeListeners.add(listener);
	}

	@Override
	public void removeListener(ChangeListener<? super T> listener) {
		changeListeners.remove(listener);
	}

	@Override
	public T getValue() {
		return this.elem;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
		invalidationListeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		invalidationListeners.remove(listener);
	}

	@Override
	public T get() {
		return getValue();
	}
	
	public void update(T elem) {
		//call before changing according to docs
		for (InvalidationListener il: invalidationListeners)
			il.invalidated(this);
		
		T oldval = this.elem;
		this.elem = elem;
		
		for (ChangeListener<? super T> cl: changeListeners)
			cl.changed(this, oldval, this.elem);
	}

}
