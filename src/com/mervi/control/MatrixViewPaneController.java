package com.mervi.control;

import com.mervi.model.HyperspectralImageModel;
import com.mervi.view.MatrixViewPane;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;

/**
 * @author Daniel
 *
 */
public class MatrixViewPaneController {

	private HyperspectralImageModel him;
	private MatrixViewPane mvp;
	
	private IntegerProperty selectedR = new SimpleIntegerProperty();
	private IntegerProperty selectedG = new SimpleIntegerProperty();
	private IntegerProperty selectedB = new SimpleIntegerProperty();
	
	public MatrixViewPaneController(HyperspectralImageModel him, MatrixViewPane mvp) {
		this.him = him;
		this.mvp = mvp;
		selectedR.set(0);
		selectedG.set(0);
		selectedB.set(0);
		this.setUp();
	}
	
	
	/**
	 * Set up listeners for view events
	 * @param mvp
	 */
	public void setUp() {
		mvp.getSelector().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
			double relxpos = e.getSceneX() / mvp.getSelector().getWidth();
			double relypos = e.getSceneY() / mvp.getSelector().getHeight();
			
			int realxpos = (int) (him.rowsProperty().floatValue() * relxpos);
			int realypos = (int) (him.colsProperty().floatValue() * relypos);
			
			mvp.getSelector().overlayOn(realxpos, realypos);
		});
		
		this.resizeSelectorOnChangeOf(him.colsProperty());
		this.resizeSelectorOnChangeOf(him.rowsProperty());
		
		this.redrawOnChangeOf(this.selectedBProperty());
		this.redrawOnChangeOf(this.selectedGProperty());
		this.redrawOnChangeOf(this.selectedRProperty());
		this.redrawOnChangeOf(mvp.widthProperty());
		this.redrawOnChangeOf(mvp.heightProperty());
		this.redrawOnChangeOf(him.modelChangedProperty());
		
		this.cleanSelectionOnChangeOf(mvp.widthProperty());
		this.cleanSelectionOnChangeOf(mvp.heightProperty());
		
	}
	
	public void resizeSelectorOnChangeOf(ObservableValue<?> p) {
		ChangeListener<Object> repaintListener = (observable, oldValue, newValue) -> {
			mvp.getSelector().setMatrixSize(him.rowsProperty().intValue(), him.colsProperty().intValue());
		};
		
		p.addListener(repaintListener);
	}
	
	public void cleanSelectionOnChangeOf(ObservableValue<?> p) {
		ChangeListener<Object> repaintListener = (observable, oldValue, newValue) -> {
			mvp.getSelector().removeOverlay();
		};
		
		p.addListener(repaintListener);
	}
	
	public void redrawOnChangeOf(ObservableValue<?> p) {
		ChangeListener<Object> repaintListener = (observable, oldValue, newValue) -> {
			if (him.available())
				mvp.getCanvas().paintMatrix(
					him.getBand(selectedR.intValue()), 
					him.getBand(selectedG.intValue()), 
					him.getBand(selectedB.intValue()));
		};
		
		p.addListener(repaintListener);
	}
	
	public IntegerProperty selectedRProperty() {
		return this.selectedR;
	}
	
	public IntegerProperty selectedGProperty() {
		return this.selectedG;
	}
	
	public IntegerProperty selectedBProperty() {
		return this.selectedB;
	}

}
