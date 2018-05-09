package com.mervi.view;


import com.mervi.control.DynamicRangeController;
import com.mervi.control.SelectionController;
import com.mervi.model.properties.BandViewProperties;
import com.mervi.model.properties.PixelViewProperties;
import com.mervi.model.properties.ProgramProperties;
import com.mervi.util.ModelViewBinder;
import com.mervi.view.data.BitViewer;
import com.mervi.view.data.CoordinateLabel;
import com.mervi.view.data.HistogramView;
import com.mervi.view.images.RGBImageStage;
import com.mervi.view.statistics.BandMetricsLabel;
import com.mervi.view.statistics.ImageMetricsLabel;
import com.mervi.view.statistics.PixelMetricsLabel;
import com.mervi.view.util.FWLabel;
import com.mervi.view.util.ScrollSpinner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Window extends Application {
	
	Stage window;
	Scene scene;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		window.setResizable(true);
		
		//create model to be filled up
		ProgramProperties properties = new ProgramProperties();
		SelectionController selectionController = new SelectionController(properties);
		BandViewProperties bvpROrig = new BandViewProperties(properties.originalImageProperty(), properties.selectedRedProperty());
		BandViewProperties bvpGOrig = new BandViewProperties(properties.originalImageProperty(), properties.selectedGreenProperty());
		BandViewProperties bvpBOrig = new BandViewProperties(properties.originalImageProperty(), properties.selectedBlueProperty());
		BandViewProperties bvpRComp = new BandViewProperties(properties.compressedImageProperty(), properties.selectedRedProperty());
		BandViewProperties bvpGComp = new BandViewProperties(properties.compressedImageProperty(), properties.selectedGreenProperty());
		BandViewProperties bvpBComp = new BandViewProperties(properties.compressedImageProperty(), properties.selectedBlueProperty());
		BandViewProperties bvpRDiff = new BandViewProperties(properties.comparableImageProperty(), properties.selectedRedProperty());
		BandViewProperties bvpGDiff = new BandViewProperties(properties.comparableImageProperty(), properties.selectedGreenProperty());
		BandViewProperties bvpBDiff = new BandViewProperties(properties.comparableImageProperty(), properties.selectedBlueProperty());
		PixelViewProperties pvpOrig = new PixelViewProperties(properties.originalImageProperty(), properties);
		PixelViewProperties pvpComp = new PixelViewProperties(properties.originalImageProperty(), properties);
		PixelViewProperties pvpDiff = new PixelViewProperties(properties.originalImageProperty(), properties);
		
		DynamicRangeController dynRangeControllerOrig = new DynamicRangeController(bvpROrig, bvpGOrig, bvpBOrig);
		DynamicRangeController dynRangeControllerComp = new DynamicRangeController(bvpRComp, bvpGComp, bvpBComp);
		DynamicRangeController dynRangeControllerDiff = new DynamicRangeController(bvpRDiff, bvpGDiff, bvpBDiff);
		
		
		
		/** Original image selector */
		HyperspectralImageSelector selectorOrig = new HyperspectralImageSelector(window, properties.originalImageProperty(), "Original");
		HyperspectralImageSelector selectorComp = new HyperspectralImageSelector(window, properties.compressedImageProperty(), "Compressed");

		/** Band selector */
		Spinner<Integer> spinnerRed = new ScrollSpinner<Integer>(properties.valueFactoryRedProperty());
		Spinner<Integer> spinnerBlue = new ScrollSpinner<Integer>(properties.valueFactoryBlueProperty());
		Spinner<Integer> spinnerGreen = new ScrollSpinner<Integer>(properties.valueFactoryGreenProperty());
		Spinner<Integer> spinnerAll = new ScrollSpinner<Integer>(properties.valueFactoryAllProperty());
		CoordinateLabel coordLabel = new CoordinateLabel();
        HBox bandSelector = new HBox(new FWLabel("red"), spinnerRed, new FWLabel("Green"), spinnerGreen, new FWLabel("Blue"), spinnerBlue, coordLabel, spinnerAll);
        /****************/
        
        /** Bit viewers */
        BitViewer bvOrig = new BitViewer("(Orig)");
        BitViewer bvComp = new BitViewer("(Comp)");
        BitViewer bvDiff = new BitViewer("(Diff)");
        /****************/
        
        /** METRICS display */
        PixelMetricsLabel pml = new PixelMetricsLabel();
        BandMetricsLabel bml = new BandMetricsLabel();
        ImageMetricsLabel iml = new ImageMetricsLabel();
        /********************/
		
		/** Create Image Views */
		RGBImageStage origStage = new RGBImageStage(selectionController, dynRangeControllerOrig, "Original");
		RGBImageStage compStage = new RGBImageStage(selectionController, dynRangeControllerComp, "Comp");
		RGBImageStage diffStage = new RGBImageStage(selectionController, dynRangeControllerDiff, "Diff");
		/**************************/
		
		/** Histograms */
		Stage histogramStage = new Stage();
		HistogramView hvOrig = new HistogramView(3);
		HBox hbHisto = new HBox(hvOrig);

		Scene histogramScene = new Scene(hbHisto, 1200, 300);
		histogramStage.setScene(histogramScene);
		histogramStage.setTitle("Histograms");
		/**************/
		
		/** bind stuff */
		ModelViewBinder.bindImageModelToView(properties, bvpROrig, bvpGOrig, bvpBOrig, origStage);
		ModelViewBinder.bindImageModelToView(properties, bvpRComp, bvpGComp, bvpBComp, compStage);
		ModelViewBinder.bindImageModelToView(properties, bvpRDiff, bvpGDiff, bvpBDiff, diffStage);
		ModelViewBinder.bindSelectedPixelToView(pvpOrig, bvOrig);
		ModelViewBinder.bindSelectedPixelToView(pvpDiff, bvDiff);
		ModelViewBinder.bindSelectedPixelToView(pvpComp, bvComp);
		ModelViewBinder.bindBandMetricsView(properties, bml);
		ModelViewBinder.bindImageMetricsView(properties, iml);
		ModelViewBinder.bindPixelMetricsView(properties, pml);
		ModelViewBinder.bindSelectedCoordView(properties, coordLabel);
		ModelViewBinder.bindBandToHistogram(properties.originalImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 0);
		ModelViewBinder.bindBandToHistogram(properties.compressedImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 1);
		//ModelViewBinder.bindBandToHistogram(properties.comparableImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 2);
		//ModelViewBinder.bindBandToHistogram(properties.originalImageProperty(), properties.valueFactoryGreenProperty().valueProperty(), hvOrig, 1);
		//ModelViewBinder.bindBandToHistogram(properties.originalImageProperty(), properties.valueFactoryBlueProperty().valueProperty(), hvOrig, 2);
		
		
		/** Putting all things together */
		VBox vbox = new VBox(selectorOrig, selectorComp, bandSelector, bvOrig, bvComp, bvDiff, pml, bml, iml);
		scene = new Scene(vbox, 400, 300);	
		window.setScene(scene);
		window.setTitle("test");
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        Platform.exit();
		    }
		});
		
		/** Show windows */
		window.show();
		origStage.show();
		compStage.show();
		diffStage.show();
		histogramStage.show();
		

		
		
		
		
		
	}
	
	
	



}



/**
@Override
public void start(Stage primaryStage) throws Exception {
	//main javafx entry point
	//
	// entire window is the "stage" (with close, maximize, and all)
	// the content inside the "stage" is the "scene" -> where you put buttons and all
	// 		thus primaryStage is the main window
	//
	primaryStage.setTitle("Window title");
	
	button = new Button();
	button.setText("click!");
	
	button.setOnAction(e -> System.out.println("hero"));
	
	StackPane layout = new StackPane();
	layout.getChildren().add(button);
	
	Scene scene = new Scene(layout, 300, 200);
	primaryStage.setScene(scene);
	primaryStage.show();
	
}
*/