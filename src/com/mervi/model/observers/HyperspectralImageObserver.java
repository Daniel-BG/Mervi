package com.mervi.model.observers;

import com.mervi.model.HyperspectralBandModel;
import com.mervi.model.HyperspectralImageStatistics;

public interface HyperspectralImageObserver {


	public void statisticsUpdate(double average, double variance);

	/**
	 * Called when the band is requested
	 * @param band
	 * @param index
	 */
	public void bandRequested(HyperspectralBandModel band, int index);

	/**
	 * Called when the statistics are updated (or requested)
	 * @param statistics
	 */
	public void statisticsUpdate(HyperspectralImageStatistics statistics);

}
