package com.mervi.util;

import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.ImageModelBandSelector;
import com.mervi.model.ImageModelPixelSelector;
import com.mervi.model.ProgramProperties;
import com.mervi.view.BandMetricsLabel;
import com.mervi.view.BitViewer;
import com.mervi.view.CoordinateLabel;
import com.mervi.view.HyperspectralImageStage;
import com.mervi.view.ImageMetricsLabel;
import com.mervi.view.PixelMetricsLabel;

import javafx.beans.property.ObjectProperty;

public class ModelViewBinder {
	
	
	public static void bindCompressedImageToView(ProgramProperties properties, HyperspectralImageStage stage) {
		bindImageModelToView(properties, properties.compressedImageProperty(), stage);
	}
	
	public static void bindDifferenceImageToView(ProgramProperties properties, HyperspectralImageStage stage) {
		bindImageModelToView(properties, properties.comparableImageProperty(), stage);
	}

	public static void bindOriginalImageToView(ProgramProperties properties, HyperspectralImageStage stage) {
		bindImageModelToView(properties, properties.originalImageProperty(), stage);
	}
	
	private static void bindImageModelToView(ProgramProperties properties, ObjectProperty<HyperspectralImageModel> image, HyperspectralImageStage stage) {
		new ImageModelBandSelector(
				image, 
				properties.valueFactoryRedProperty().valueProperty(),
				properties.valueFactoryGreenProperty().valueProperty(),
				properties.valueFactoryBlueProperty().valueProperty())
			.bindTo(stage);
		
		stage.selectedColProperty().bind(properties.colProperty());
		stage.selectedRowProperty().bind(properties.rowProperty());
	}
	
	
	public static void bindCompressedPixelToView(ProgramProperties properties, BitViewer viewer) {
		bindSelectedPixelToView(properties, properties.compressedImageProperty(), viewer);
	}
	
	public static void bindDifferencePixelToView(ProgramProperties properties, BitViewer viewer) {
		bindSelectedPixelToView(properties, properties.comparableImageProperty(), viewer);
	}

	public static void bindOriginalPixelToView(ProgramProperties properties, BitViewer viewer) {
		bindSelectedPixelToView(properties, properties.originalImageProperty(), viewer);
	}
	
	private static void bindSelectedPixelToView(ProgramProperties properties, ObjectProperty<HyperspectralImageModel> image, BitViewer viewer) {
        new ImageModelPixelSelector(
        		image,
        		properties.rowProperty(),
        		properties.colProperty(),
        		properties.valueFactoryRedProperty().valueProperty(),
        		properties.valueFactoryGreenProperty().valueProperty(),
        		properties.valueFactoryBlueProperty().valueProperty())
        	.bindTo(viewer);
	}
	
	
	public static void bindPixelMetricsView(ProgramProperties properties, PixelMetricsLabel pml) {
        pml.selectedBandProperty().bind(properties.valueFactoryRedProperty().valueProperty());
        pml.selectedColProperty().bind(properties.colProperty());
        pml.selectedRowProperty().bind(properties.rowProperty());
        pml.aImageProperty().bind(properties.originalImageProperty());
        pml.bImageProperty().bind(properties.compressedImageProperty());
	}
	
	public static void bindBandMetricsView(ProgramProperties properties, BandMetricsLabel bml) {
		bml.selectedBandProperty().bind(properties.valueFactoryRedProperty().valueProperty());
        bml.aImageProperty().bind(properties.originalImageProperty());
        bml.bImageProperty().bind(properties.compressedImageProperty());
	}
	
	public static void bindImageMetricsView(ProgramProperties properties, ImageMetricsLabel iml) {
        iml.aImageProperty().bind(properties.originalImageProperty());
        iml.bImageProperty().bind(properties.compressedImageProperty());
	}

	public static void bindSelectedCoordView(ProgramProperties properties, CoordinateLabel coordLabel) {
		coordLabel.rowProperty().bind(properties.rowProperty());
		coordLabel.colProperty().bind(properties.colProperty());
	}
}
