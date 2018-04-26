package com.mervi.control;

import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.ProgramProperties;
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

	private HyperspectralImageModel him;
	private MatrixViewPane mvp;
	private ProgramProperties mp;
	
	private IntegerProperty selectedRIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedGIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedBIndex = new SimpleIntegerProperty(0);
	private IntegerProperty selectedRValue = new SimpleIntegerProperty(0);
	private IntegerProperty selectedGValue = new SimpleIntegerProperty(0);
	private IntegerProperty selectedBValue = new SimpleIntegerProperty(0);
	
	public MatrixViewPaneController(HyperspectralImageModel him, MatrixViewPane mvp, ProgramProperties mp) {
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
			
			int realxpos = (int) ((double) him.getCols() * relxpos);
			int realypos = (int) ((double) him.getRows() * relypos);
			
			mp.colProperty().set(realxpos);
			mp.rowProperty().set(realypos);
			mp.xPosProperty().set(relxpos);
			mp.yPosProperty().set(relypos);
			
		});
		
		InvalidationListener il = e -> {
			if (!him.available())
				return;
			//just in case a different selector is getting us out of bounds
			int rowIndex = Math.min(him.getRows() - 1, mp.rowProperty().intValue());
			int colIndex = Math.min(him.getCols() - 1, mp.colProperty().intValue());
			int maxBand = him.getBands() - 1;
			//now select the proper cell
			this.selectedRValue.set(him.getBand(Math.min(maxBand, this.selectedRIndexProperty().intValue())).get(rowIndex, colIndex));
			this.selectedGValue.set(him.getBand(Math.min(maxBand, this.selectedGIndexProperty().intValue())).get(rowIndex, colIndex));
			this.selectedBValue.set(him.getBand(Math.min(maxBand, this.selectedBIndexProperty().intValue())).get(rowIndex, colIndex));
		};
		
		mp.rowProperty().addListener(il);
		mp.colProperty().addListener(il);
		this.selectedRIndexProperty().addListener(il);
		this.selectedBIndexProperty().addListener(il);
		this.selectedGIndexProperty().addListener(il);
		
		him.modelChangedProperty().addListener(e -> {
			mvp.getSelector().numRowsProperty().setValue(him.getRows());
			mvp.getSelector().numColsProperty().setValue(him.getCols());
		});
		
		mvp.getSelector().selectedColProperty().bind(mp.colProperty());
		mvp.getSelector().selectedRowProperty().bind(mp.rowProperty());
		mvp.getSelector().selectedXPosProperty().bind(mp.xPosProperty());
		mvp.getSelector().selectedYPosProperty().bind(mp.yPosProperty());
		
		
		this.selectedRIndexProperty().addListener( (obs, oldval, newVal) -> {
			if (him.available())
				mvp.getCanvas().setRedComponent(him.getBand(newVal.intValue()).getImage());
		});
		this.selectedGIndexProperty().addListener( (obs, oldval, newVal) -> {
			if (him.available())
				mvp.getCanvas().setGreenComponent(him.getBand(newVal.intValue()).getImage());
		});
		this.selectedBIndexProperty().addListener( (obs, oldval, newVal) -> {
			if (him.available())
				mvp.getCanvas().setBlueComponent(him.getBand(newVal.intValue()).getImage());
		});
		
		him.modelChangedProperty().addListener(e -> {
			if (!him.available())
				return;
			mvp.getCanvas().setRedComponent(him.getBand(this.selectedRIndexProperty().intValue()).getImage());
			mvp.getCanvas().setGreenComponent(him.getBand(this.selectedGIndexProperty().intValue()).getImage());
			mvp.getCanvas().setBlueComponent(him.getBand(this.selectedBIndexProperty().intValue()).getImage());
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
