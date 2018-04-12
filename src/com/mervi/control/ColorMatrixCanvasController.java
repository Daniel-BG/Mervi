package com.mervi.control;

import com.mervi.model.MatrixModel;
import com.mervi.view.ColorMatrixCanvas;

/**
 * @author Daniel
 *
 */
public class ColorMatrixCanvasController implements ColorMatrixCanvasControllerInterface {

	private MatrixModel mm;
	private ColorMatrixCanvas cmc;

	public void setModel(MatrixModel mm) {
		this.mm = mm;
	}
	
	public void setView(ColorMatrixCanvas cmc) {
		this.cmc = cmc;
	}
	
	@Override
	public void requestRepaint() {
		cmc.paintMatrix(mm.getRed(), mm.getGreen(), mm.getBlue());
	}

	@Override
	public void mouseOn(double sceneX, double sceneY, double sceneWidth, double sceneHeight) {
		double relxpos = sceneX / sceneWidth;
		double relypos = sceneY / sceneHeight;
		
		int realxpos = (int) (((float) mm.getRows()) * relxpos);
		int realypos = (int) (((float) mm.getCols()) * relypos);
		
		
		cmc.overlayOn(realxpos, realypos);
		
	}

}
