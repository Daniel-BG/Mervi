package com.mervi.model;

public class HyperspectralDiffModel extends AbstractHyperspectralImageModel {
	
	AbstractHyperspectralImageModel source1, source2;
	
	
	public void setSources(AbstractHyperspectralImageModel source1, AbstractHyperspectralImageModel source2) {
		//assign
		this.source1 = source1;
		this.source2 = source2;
		//tie to source1, source2 should follow
		this.bandsProperty().bind(source1.bandsProperty());
		this.rowsProperty().bind(source1.rowsProperty());
		this.colsProperty().bind(source1.colsProperty());
		//this updates when the base images update
		source1.modelChangedProperty().addListener(e -> this.modelChangedProperty().update());
		source2.modelChangedProperty().addListener(e -> this.modelChangedProperty().update());
		this.modelChangedProperty().update();
	}

	@Override
	public float getValue(int band, int row, int col) {
		return source1.getValue(band, row, col) - source2.getValue(band, row, col);
	}

	@Override
	public ReadOnlyMatrix getBand(int index) {
		//check dimensions
		if (source1.bandsProperty().getValue() != source2.bandsProperty().getValue() ||
			source1.rowsProperty().getValue() != source2.rowsProperty().getValue() ||
			source1.colsProperty().getValue() != source2.colsProperty().getValue())
			throw new IllegalStateException("Images cannot be of different sizes");
		
		return new ReadOnlyMatrix() {
			
			@Override
			public int getRows() {
				return source1.rowsProperty().getValue();
			}
			
			@Override
			public int getCols() {
				return source1.colsProperty().getValue();
			}
			
			@Override
			public float get(int row, int col) {
				float val1 = source1.getBand(index).get(row, col);
				float val2 = source2.getBand(index).get(row, col);
				return (val1 - val2 + 1) / 2; //back in 0-1 range
			}
		};
	}

	@Override
	public boolean available() {
		return source1 != null && source2 != null && source1.available() && source2.available();
	}

}
