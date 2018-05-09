package com.mervi.view.images;

import org.controlsfx.control.RangeSlider;

import com.mervi.control.DynamicRangeController;
import com.mervi.control.SelectionController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RGBImageStage extends Stage {

	RGBImageView cmv;
	MatrixSelector ms;
	
	private static final int SLIDER_SPACING = 10;
	
	public RGBImageStage(SelectionController sc, DynamicRangeController drc, String title) {
		Pane p = new Pane();
		cmv = new RGBImageView();
		ms = new MatrixSelector(sc);
		
		cmv.bindDimensionsTo(p.widthProperty(), p.heightProperty());
		ms.widthProperty().bind(p.widthProperty());
		ms.heightProperty().bind(p.heightProperty());
		
		p.setPrefSize(4096, 4096);
		p.getChildren().addAll(cmv, ms);
		ms.toFront();
		
		
		RangeSlider dynRangeSlider = new RangeSlider(0.0,1.0,0.0,1.0);
		dynRangeSlider.setPrefHeight(SLIDER_SPACING);
		dynRangeSlider.setShowTickLabels(false);
		dynRangeSlider.setShowTickMarks(false);
		dynRangeSlider.lowValueProperty().addListener((o, old, newVal) -> drc.setLow(newVal.doubleValue()));
		dynRangeSlider.highValueProperty().addListener((o, old, newVal) -> drc.setHigh(newVal.doubleValue()));
		
		VBox vbox = new VBox(p, dynRangeSlider);
		
		
		Scene scene = new Scene(vbox, 200, 200);	
		this.setScene(scene);
		this.setTitle(title);
		this.show();
		
		this.redImageProperty().addListener((o, oldVal, newVal) -> {
			this.setWidth(newVal.getWidth());
			this.setHeight(newVal.getHeight() + SLIDER_SPACING);
		});
	}
	
	
	public ObjectProperty<Image> redImageProperty() {
		return this.cmv.redImageProperty();
	}
	
	public ObjectProperty<Image> greenImageProperty() {
		return this.cmv.greenImageProperty();
	}
	
	public ObjectProperty<Image> blueImageProperty() {
		return this.cmv.blueImageProperty();
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
