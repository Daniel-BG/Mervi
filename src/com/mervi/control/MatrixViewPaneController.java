package com.mervi.control;

import com.mervi.model.AbstractHyperspectralImageModel;
import com.mervi.model.MousePosition;
import com.mervi.view.MatrixViewPane;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;

/**
 * @author Daniel
 *
 */
public class MatrixViewPaneController {

	private AbstractHyperspectralImageModel him;
	private MatrixViewPane mvp;
	private MousePosition mp;
	
	private IntegerProperty selectedRIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedGIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedBIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedRValue = new SimpleIntegerProperty(0);
	private IntegerProperty selectedGValue = new SimpleIntegerProperty(0);
	private IntegerProperty selectedBValue = new SimpleIntegerProperty(0);
	
	public MatrixViewPaneController(AbstractHyperspectralImageModel him, MatrixViewPane mvp, MousePosition mp) {
		this.him = him;
		this.mvp = mvp;
		this.mp = mp;
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
			
			int realxpos = (int) (him.colsProperty().floatValue() * relxpos);
			int realypos = (int) (him.rowsProperty().floatValue() * relypos);
			
			mp.colProperty().set(realxpos);
			mp.rowProperty().set(realypos);
			
		});
		
		InvalidationListener il = e -> {
			if (!him.available())
				return;
			this.selectedRValue.set(him.getBand(this.selectedRIndexProperty().intValue()).get(mp.rowProperty().intValue(), mp.colProperty().intValue()));
			this.selectedGValue.set(him.getBand(this.selectedGIndexProperty().intValue()).get(mp.rowProperty().intValue(), mp.colProperty().intValue()));
			this.selectedBValue.set(him.getBand(this.selectedBIndexProperty().intValue()).get(mp.rowProperty().intValue(), mp.colProperty().intValue()));
		};
		
		mp.rowProperty().addListener(il);
		mp.colProperty().addListener(il);
		this.selectedRIndexProperty().addListener(il);
		this.selectedBIndexProperty().addListener(il);
		this.selectedGIndexProperty().addListener(il);
		
		mvp.getSelector().numRowsProperty().bind(him.rowsProperty());
		mvp.getSelector().numColsProperty().bind(him.colsProperty());
		mvp.getSelector().selectedColProperty().bind(mp.colProperty());
		mvp.getSelector().selectedRowProperty().bind(mp.rowProperty());
		
		this.selectedRIndexProperty().addListener( (obs, oldval, newVal) ->
			mvp.getCanvas().getRedProperty().set(him.getBand(newVal.intValue()))
		);
		this.selectedGIndexProperty().addListener( (obs, oldval, newVal) ->
			mvp.getCanvas().getGreenProperty().set(him.getBand(newVal.intValue()))
		);
		this.selectedBIndexProperty().addListener( (obs, oldval, newVal) ->
			mvp.getCanvas().getBlueProperty().set(him.getBand(newVal.intValue()))
		);
		
		him.modelChangedProperty().addListener(e -> {
			mvp.getCanvas().getRedProperty().set(him.getBand(this.selectedRIndexProperty().intValue()));
			mvp.getCanvas().getGreenProperty().set(him.getBand(this.selectedGIndexProperty().intValue()));
			mvp.getCanvas().getBlueProperty().set(him.getBand(this.selectedBIndexProperty().intValue()));
		});
		
	}
	
	public IntegerProperty selectedRIndexProperty() {
		return this.selectedRIndex;
	}
	
	public IntegerProperty selectedGIndexProperty() {
		return this.selectedGIndex;
	}
	
	public IntegerProperty selectedBIndexProperty() {
		return this.selectedBIndex;
	}
	
	public IntegerProperty selectedRValueProperty() {
		return this.selectedRValue;
	}
	
	public IntegerProperty selectedGValueProperty() {
		return this.selectedGValue;
	}
	
	public IntegerProperty selectedBValueProperty() {
		return this.selectedBValue;
	}

}
