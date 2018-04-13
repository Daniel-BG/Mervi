package com.mervi.control;

import com.mervi.model.MatrixModel;
import com.mervi.view.MatrixView;
import com.mervi.view.events.SelfResizingCanvasEvent;

import javafx.scene.input.MouseEvent;

/**
 * @author Daniel
 *
 */
public class MatrixViewPaneController {

	private MatrixModel mm;

	public void setModel(MatrixModel mm) {
		this.mm = mm;
	}
	
	/**
	 * Set up listeners for view events
	 * @param mv
	 */
	public void setView(MatrixView mv) {
		if (mm == null)
			throw new IllegalStateException("Model must be set up prior to view");
		
		mv.getSelector().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
			double relxpos = e.getSceneX() / mv.getSelector().getWidth();
			double relypos = e.getSceneY() / mv.getSelector().getHeight();
			
			int realxpos = (int) (((float) mm.getRows()) * relxpos);
			int realypos = (int) (((float) mm.getCols()) * relypos);
			
			
			mv.getSelector().overlayOn(realxpos, realypos);
		});
		
		mv.getCanvas().addEventHandler(SelfResizingCanvasEvent.RESIZE, e -> mv.getCanvas().paintMatrix(mm.getRed(), mm.getGreen(), mm.getBlue()));
		mv.getSelector().addEventHandler(SelfResizingCanvasEvent.RESIZE, e -> mv.getSelector().setMatrixSize(mm.getRows(), mm.getCols()));
	}

}
