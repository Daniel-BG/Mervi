package com.mervi.control;

import com.mervi.model.AbstractHyperspectralImageModel;
import com.mervi.model.MousePosition;
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

	private AbstractHyperspectralImageModel him;
	private MatrixViewPane mvp;
	private MousePosition mp;
	
	private IntegerProperty selectedR = new SimpleIntegerProperty();
	private IntegerProperty selectedG = new SimpleIntegerProperty();
	private IntegerProperty selectedB = new SimpleIntegerProperty();
	
	public MatrixViewPaneController(AbstractHyperspectralImageModel him, MatrixViewPane mvp, MousePosition mp) {
		this.him = him;
		this.mvp = mvp;
		this.mp = mp;
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
			
			mp.xposProperty().set(realxpos);
			mp.yposProperty().set(realypos);
		});
		
		mvp.getSelector().numRowsProperty().bind(him.rowsProperty());
		mvp.getSelector().numColsProperty().bind(him.colsProperty());
		mvp.getSelector().selectedColProperty().bind(mp.xposProperty());
		mvp.getSelector().selectedRowProperty().bind(mp.yposProperty());
		
		
		this.redrawOnChangeOf(this.selectedBProperty());
		this.redrawOnChangeOf(this.selectedGProperty());
		this.redrawOnChangeOf(this.selectedRProperty());
		this.redrawOnChangeOf(mvp.widthProperty());
		this.redrawOnChangeOf(mvp.heightProperty());
		this.redrawOnChangeOf(him.modelChangedProperty());
		
		
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
