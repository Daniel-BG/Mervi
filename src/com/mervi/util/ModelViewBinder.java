package com.mervi.util;

import com.mervi.model.HyperspectralBandStatistics;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.properties.BandViewProperties;
import com.mervi.model.properties.PixelViewProperties;
import com.mervi.model.properties.ProgramProperties;
import com.mervi.view.data.BitViewer;
import com.mervi.view.data.CoordinateLabel;
import com.mervi.view.data.HistogramView;
import com.mervi.view.images.RGBImageStage;
import com.mervi.view.statistics.BandMetricsLabel;
import com.mervi.view.statistics.ImageMetricsLabel;
import com.mervi.view.statistics.PixelMetricsLabel;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;

public class ModelViewBinder {
	
	public static void bindImageModelToView(ProgramProperties properties, BandViewProperties bvpR, BandViewProperties bvpG, BandViewProperties bvpB, RGBImageStage stage) {
		stage.redImageProperty().bind(bvpR.selectedImageProperty());
		stage.greenImageProperty().bind(bvpG.selectedImageProperty());
		stage.blueImageProperty().bind(bvpB.selectedImageProperty());
		
		stage.selectedColProperty().bind(properties.colProperty());
		stage.selectedRowProperty().bind(properties.rowProperty());
	}
	
	
	public static void bindSelectedPixelToView(PixelViewProperties pvp, BitViewer bv) {
		bv.redValueProperty().bind(pvp.redPixelProperty());
		bv.greenValueProperty().bind(pvp.greenPixelProperty());
		bv.blueValueProperty().bind(pvp.bluePixelProperty());
		
		bv.bitDepthProperty().bind(pvp.bitDepthProperty());
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

	public static void bindBandToHistogram(ObjectProperty<HyperspectralImageModel> him,
			ObjectProperty<Integer> band, HistogramView hv, int index) {
		InvalidationListener il = e -> { 
			if (him.getValue() != null) {
				HyperspectralBandStatistics hbs = him.getValue().getBand(band.getValue()).getStatistics();
				hv.setSeries(index, hbs.getHistogram(), hbs.getMinX(), hbs.getMaxX(), hbs.getMinY(), hbs.getMaxY());
			}
		};
		
		him.addListener(il);
		band.addListener(il);
		
	}
}
