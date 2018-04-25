package com.mervi.model;

public class HyperspectralDiffModel extends AbstractHyperspectralImageModel {
	
	AbstractHyperspectralImageModel source1, source2;

	
	
	public void setSources(AbstractHyperspectralImageModel source1, AbstractHyperspectralImageModel source2) {
		//assign
		this.source1 = source1;
		this.source2 = source2;
		//this updates when the base images update
		source1.modelChangedProperty().addListener(e -> {
			this.modelChangedProperty().update();
			this.setSize(source1.getBands(), source1.getRows(), source1.getCols(), true);
		});
		source2.modelChangedProperty().addListener(e -> this.modelChangedProperty().update());
		this.modelChangedProperty().update();
	}

	@Override
	public int getValue(int band, int row, int col) {
		return Math.abs(source1.getValue(band, row, col) - source2.getValue(band, row, col));
	}

	@Override
	public HyperspectralBandModel doGetBand(int index) {
		//check dimensions
		if (source1.getBands() != source2.getBands() ||
			source1.getRows() != source2.getRows() ||
			source1.getCols() != source2.getCols() ||
			(source1.getRange() != source2.getRange()))
			throw new IllegalStateException("Images cannot be of different sizes" 
					+ source1.getBands() + ","
					+ source1.getRows() + ","
					+ source1.getCols() + ","
					+ source1.getRange() + ","
					+ source2.getBands() + ","
					+ source2.getRows() + ","
					+ source2.getCols() + ","
					+ source2.getRange());
		
		return new HyperspectralBandModel() {
			
			@Override
			public int getRows() {
				return source1.getRows();
			}
			
			@Override
			public int getCols() {
				return source1.getCols();
			}
			
			@Override
			public int get(int row, int col) {
				int val1 = source1.getBand(index).get(row, col);
				int val2 = source2.getBand(index).get(row, col);
				return Math.abs(val1 - val2); //back in positive range
			}

			@Override
			public int range() {
				return source1.getRange();
			}
		};
	}

	@Override
	public boolean available() {
		return source1 != null && source2 != null && source1.available() && source2.available() && source1.sizeEquals(source2);
	}

	@Override
	public int getRange() {
		return source1.getRange();
	}

	@Override
	protected void doSetSize() {
		//nothing to do
	}

}
