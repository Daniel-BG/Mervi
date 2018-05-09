package com.mervi.view;

import org.controlsfx.control.RangeSlider;

import com.mervi.control.ProgramController;
import com.mervi.model.HyperspectralBandModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HyperspectralImageStage extends Stage {

	ColorMatrixView cmv;
	MatrixSelector ms;
	
	private static final int SLIDER_SPACING = 10;
	
	public HyperspectralImageStage(ProgramController sc, String title) {
		Pane p = new Pane();
		cmv = new ColorMatrixView();
		ms = new MatrixSelector(sc);
		
		cmv.bindDimensionsTo(p.widthProperty(), p.heightProperty());
		ms.widthProperty().bind(p.widthProperty());
		ms.heightProperty().bind(p.heightProperty());
		
		p.setPrefSize(4096, 4096);
		p.getChildren().addAll(cmv, ms);
		ms.toFront();
		
		
		RangeSlider dynRangeSlider = new RangeSlider();
		dynRangeSlider.setPrefHeight(SLIDER_SPACING);
		dynRangeSlider.setShowTickLabels(false);
		dynRangeSlider.setShowTickMarks(false);
		VBox vbox = new VBox(p, dynRangeSlider);
		
		
		Scene scene = new Scene(vbox, 200, 200);	
		this.setScene(scene);
		this.setTitle(title);
		this.show();
		
		this.redBandProperty().addListener((o, oldVal, newVal) -> {
			this.setWidth(newVal.getCols());
			this.setHeight(newVal.getRows() + SLIDER_SPACING);
		});
	}
	
	
	public ObjectProperty<HyperspectralBandModel> redBandProperty() {
		return this.cmv.redBandProperty();
	}
	
	public ObjectProperty<HyperspectralBandModel> greenBandProperty() {
		return this.cmv.greenBandProperty();
	}
	
	public ObjectProperty<HyperspectralBandModel> blueBandProperty() {
		return this.cmv.blueBandProperty();
	}
	
	public IntegerProperty numRowsProperty() {
		return this.ms.numRowsProperty();
	}
	
	public IntegerProperty numColsProperty() {
		return this.ms.numColsProperty();
	}
	
	public IntegerProperty selectedRowProperty() {
		return this.ms.selectedRowProperty();
	}
	
	public IntegerProperty selectedColProperty() {
		return this.ms.selectedColProperty();
	}

}
