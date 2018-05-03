package com.mervi.view;


import com.mervi.control.ProgramController;
import com.mervi.model.ImageModelBandSelector;
import com.mervi.model.ProgramProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		
		//set canvas up 
		//https://stackoverflow.com/questions/23449932/javafx-resize-canvas-when-screen-is-resized
		
		/** Original image selector */
		HyperspectralImageSelector selectorOrig = new HyperspectralImageSelector(window, properties.originalImageProperty());
		HyperspectralImageSelector selectorComp = new HyperspectralImageSelector(window, properties.compressedImageProperty());

		/** Band selector */
		final Spinner<Integer> spinnerRed = new ScrollSpinner<Integer>(properties.valueFactoryRedProperty());
		final Spinner<Integer> spinnerBlue = new ScrollSpinner<Integer>(properties.valueFactoryBlueProperty());
		final Spinner<Integer> spinnerGreen = new ScrollSpinner<Integer>(properties.valueFactoryGreenProperty());
		final Spinner<Integer> spinnerAll = new ScrollSpinner<Integer>(properties.valueFactoryAllProperty());


		
        Label coordLabel = new Label(" @(x, x)");
        InvalidationListener mpChangedCoord = e -> {
        	coordLabel.setText(" @: (" + properties.rowProperty().intValue() + "," + properties.colProperty().intValue() + ")");
        };
        properties.colProperty().addListener(mpChangedCoord);
        properties.rowProperty().addListener(mpChangedCoord);
        coordLabel.setMinWidth(80);
        
        HBox bandSelector = new HBox(new FWLabel("red"), spinnerRed, new FWLabel("Green"), spinnerGreen, new FWLabel("Blue"), spinnerBlue, coordLabel, spinnerAll);
        
        
        /** Bit viewers */
        Label origBits = new Label("(Orig)");
        IntegerBinaryViewer ibvOrigRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvOrigBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvOrigGreen = new IntegerBinaryViewer(16);
        HBox bvboxOrig = new HBox(10, ibvOrigRed, ibvOrigGreen, ibvOrigBlue, origBits);
       
        Label compBits = new Label("(Comp)");
        IntegerBinaryViewer ibvCompRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvCompBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvCompGreen = new IntegerBinaryViewer(16);
        HBox bvboxComp = new HBox(10, ibvCompRed, ibvCompGreen, ibvCompBlue, compBits);
        
        Label diffBits = new Label("(Diff)");
        IntegerBinaryViewer ibvDiffRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvDiffBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvDiffGreen = new IntegerBinaryViewer(16);
        HBox bvboxDiff = new HBox(10, ibvDiffRed, ibvDiffGreen, ibvDiffBlue, diffBits);
        /****************/
        
        
        /** METRICS display */
        //only for the red channel for now as to not crowd the screen too much
        //pixel:
        PixelMetricsLabel pml = new PixelMetricsLabel();
        BandMetricsLabel bml = new BandMetricsLabel();
        ImageMetricsLabel iml = new ImageMetricsLabel();
        //TODO bind with program properties
        pml.selectedBandProperty().bind(properties.valueFactoryRedProperty().valueProperty());
        pml.selectedColProperty().bind(properties.colProperty());
        pml.selectedRowProperty().bind(properties.rowProperty());
        pml.aImageProperty().bind(properties.originalImageProperty());
        pml.bImageProperty().bind(properties.compressedImageProperty());
        bml.selectedBandProperty().bind(properties.valueFactoryRedProperty().valueProperty());
        bml.aImageProperty().bind(properties.originalImageProperty());
        bml.bImageProperty().bind(properties.compressedImageProperty());
        iml.aImageProperty().bind(properties.originalImageProperty());
        iml.bImageProperty().bind(properties.compressedImageProperty());
        /********************/
        

        
		
		/** Putting all things together */
        //TODO
		VBox vbox = new VBox(selectorOrig, selectorComp, bandSelector, bvboxOrig, bvboxComp, bvboxDiff, pml, bml, iml);
		
		
		/** Create and show the scene */
		scene = new Scene(vbox, 400, 300);	
		window.setScene(scene);
		
		window.setTitle("test");
		window.show();
		
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        Platform.exit();
		    }
		});
		
		
		/** this creates one view */
		HyperspectralImageStage origStage = new HyperspectralImageStage(propertiesController, "Original");
		new ImageModelBandSelector(
				properties.originalImageProperty(), 
				properties.valueFactoryRedProperty().valueProperty(),
				properties.valueFactoryGreenProperty().valueProperty(),
				properties.valueFactoryBlueProperty().valueProperty())
			.bindTo(origStage);
		
		origStage.show();
		/*************************/
		/** and another one */
		HyperspectralImageStage compStage = new HyperspectralImageStage(propertiesController, "Comp");
		new ImageModelBandSelector(
				properties.compressedImageProperty(), 
				properties.valueFactoryRedProperty().valueProperty(),
				properties.valueFactoryGreenProperty().valueProperty(),
				properties.valueFactoryBlueProperty().valueProperty())
			.bindTo(compStage);
		compStage.show();
		/**************************/
		/** and another one */
		HyperspectralImageStage diffStage = new HyperspectralImageStage(propertiesController, "Diff");
		new ImageModelBandSelector(
				properties.comparableImageProperty(), 
				properties.valueFactoryRedProperty().valueProperty(),
				properties.valueFactoryGreenProperty().valueProperty(),
				properties.valueFactoryBlueProperty().valueProperty())
			.bindTo(diffStage);
		diffStage.show();
		/**************************/
		
		
		/** bind stuff */

		
		/**Stage histogramStage = new Stage();
		HistogramView hvOrig = new HistogramView(3);
		HistogramView hvComp = new HistogramView(3);
		HistogramView hvDiff = new HistogramView(3);
		HBox hbHisto = new HBox(hvOrig);

		Scene histogramScene = new Scene(hbHisto, 1200, 300);
		histogramStage.setScene(histogramScene);
		histogramStage.setTitle("Histograms");
		histogramStage.show();
		
		InvalidationListener histogramRefresher = e -> {
			int bandRIndex = spinnerRed.valueProperty().getValue().intValue();
			int bandGIndex = spinnerGreen.valueProperty().getValue().intValue();
			int bandBIndex = spinnerBlue.valueProperty().getValue().intValue();
        	HyperspectralBandModel bandR, bandG, bandB;
        	try {
        		bandR = himOrig.getBand(bandRIndex);
        		bandG = himOrig.getBand(bandGIndex);
        		bandB = himOrig.getBand(bandBIndex);
        	} catch (Exception ex) {
        		return; //if one image has updated and the other hasn't, avoid conflicts
        	}
        	
        	bandR.setHistogramIn(hvOrig.getSeries(0).getData());
        	bandG.setHistogramIn(hvOrig.getSeries(1).getData());
        	bandB.setHistogramIn(hvOrig.getSeries(2).getData());
        	//HistogramUtilities.getHistogramFor(bandR, hvOrig.getSeries(0).getData());
        	//HistogramUtilities.getHistogramFor(bandG, hvOrig.getSeries(1).getData());
        	//HistogramUtilities.getHistogramFor(bandB, hvOrig.getSeries(2).getData());
			
		};
		
		spinnerRed.valueProperty().addListener(histogramRefresher);
		spinnerGreen.valueProperty().addListener(histogramRefresher);
		spinnerBlue.valueProperty().addListener(histogramRefresher);*/
		
		
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