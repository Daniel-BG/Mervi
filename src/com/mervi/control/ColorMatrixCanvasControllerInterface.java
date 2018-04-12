package com.mervi.control;

/**
 * This class will receive the actions performed on a ColorMatrixCanvas
 * @author Daniel
 *
 */
public interface ColorMatrixCanvasControllerInterface {
	
	/**
	 * Called when a paint is requested by the Canvas (e.g: after a resize)
	 */
	public void requestRepaint();
	
	/**
	 * Called when the mouse triggered an event on the given position
	 * @param sceneX position within the scene
	 * @param sceneY position within the scene
	 * @param sceneWidth total width of the scene (<code> 0 <= sceneX < sceneWidth </code>)
	 * @param sceneHeight total height of the scene (<code> 0 <= sceneY < sceneHeight </code>)
	 */
	public void mouseOn(double sceneX, double sceneY, double sceneWidth, double sceneHeight);
	

}
