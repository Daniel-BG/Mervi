package com.mervi.view;


import com.mervi.control.ProgramController;
import com.mervi.model.ProgramProperties;
import com.mervi.util.ModelViewBinder;

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
		ProgramController propertiesController = new ProgramController(properties);
		
		/** Original image selector */
		HyperspectralImageSelector selectorOrig = new HyperspectralImageSelector(window, properties.originalImageProperty());
		HyperspectralImageSelector selectorComp = new HyperspectralImageSelector(window, properties.compressedImageProperty());

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
		HyperspectralImageStage origStage = new HyperspectralImageStage(propertiesController, "Original");
		HyperspectralImageStage compStage = new HyperspectralImageStage(propertiesController, "Comp");
		HyperspectralImageStage diffStage = new HyperspectralImageStage(propertiesController, "Diff");
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
		ModelViewBinder.bindOriginalImageToView(properties, origStage);
		ModelViewBinder.bindDifferenceImageToView(properties, diffStage);
		ModelViewBinder.bindCompressedImageToView(properties, compStage);
		ModelViewBinder.bindOriginalPixelToView(properties, bvOrig);
		ModelViewBinder.bindDifferencePixelToView(properties, bvDiff);
		ModelViewBinder.bindCompressedPixelToView(properties, bvComp);
		ModelViewBinder.bindBandMetricsView(properties, bml);
		ModelViewBinder.bindImageMetricsView(properties, iml);
		ModelViewBinder.bindPixelMetricsView(properties, pml);
		ModelViewBinder.bindSelectedCoordView(properties, coordLabel);
		ModelViewBinder.bindBandToHistogram(properties.originalImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 0);
		ModelViewBinder.bindBandToHistogram(properties.compressedImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 1);
		ModelViewBinder.bindBandToHistogram(properties.comparableImageProperty(), properties.valueFactoryRedProperty().valueProperty(), hvOrig, 2);
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