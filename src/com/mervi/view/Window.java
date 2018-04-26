package com.mervi.view;


import java.io.File;
import com.jypec.img.HyperspectralImage;
import com.jypec.util.io.HyperspectralImageReader;
import com.mervi.Config;
import com.mervi.control.MatrixViewPaneController;
import com.mervi.control.ProgramController;
import com.mervi.model.HyperspectralDiffModel;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.ConcreteHyperspectralImageModel;
import com.mervi.model.ProgramProperties;
import com.mervi.model.HyperspectralBandModel;
import com.mervi.model.metrics.BandMetrics;
import com.mervi.model.metrics.ImageMetrics;
import com.mervi.model.metrics.PixelMetrics;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
		HyperspectralImageModel himOrig, himComp;
		
		//set canvas up 
		//https://stackoverflow.com/questions/23449932/javafx-resize-canvas-when-screen-is-resized
		
		/** Original image selector */
		final TextField originalTextBox = new TextField("Original");
		originalTextBox.setEditable(false);
		originalTextBox.setPrefWidth(4000);
		final Button originalButton = new Button("Original");
		originalButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File f = fileChooser.showOpenDialog(window);
			try {
				originalTextBox.setText(f.toString());
				HyperspectralImage hi = HyperspectralImageReader.read(f.toString(), true);
				//create image
				himOrig = new ConcreteHyperspectralImageModel(hi);
				//create view for image
				HyperspectralImageStage his = new HyperspectralImageStage(propertiesController, "Original");
				//bind properties
			} catch (Exception e1) {
				System.out.println("Did not load image");
			}
		});
		originalButton.setMinWidth(90);
		final HBox originalHBox = new HBox(originalButton, originalTextBox);
		
		
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
        Label pixelMetrics = new Label();
        InvalidationListener pixelMetricRefresher = e -> {
        	int row = properties.rowProperty().intValue();
        	int col = properties.colProperty().intValue();
        	int band = spinnerRed.valueProperty().getValue().intValue();
        	int originalPix, compressedPix;
        	try {
	        	originalPix = himOrig.getValue(band, row, col);
	        	compressedPix = himComp.getValue(band, row, col);
        	} catch (Exception ex) {
        		return; //if one image has updated and the other hasn't, avoid conflicts
        	}
        	
        	double percent = PixelMetrics.percentDifference(originalPix, compressedPix);
        	double psnr = PixelMetrics.PSNR(originalPix, compressedPix, himOrig.getRange());
        	int diff = PixelMetrics.difference(originalPix, compressedPix);
        	
        	
        	pixelMetrics.setText(
        			"PIXEL: %off: " + String.format(Config.DOUBLE_FORMAT,percent) + 
        			" PSNR: " + String.format(Config.DOUBLE_FORMAT,psnr) + 
        			" DIFF: " + diff);
        };
        
        properties.rowProperty().addListener(pixelMetricRefresher);
        properties.colProperty().addListener(pixelMetricRefresher);
        spinnerRed.valueProperty().addListener(pixelMetricRefresher);
        himOrig.modelChangedProperty().addListener(pixelMetricRefresher);
        himComp.modelChangedProperty().addListener(pixelMetricRefresher);
        //band metrics below
        Label bandMetrics = new Label();
        InvalidationListener bandMetricRefresher = e -> {
        	int band = spinnerRed.valueProperty().getValue().intValue();
        	HyperspectralBandModel romOrig, romComp;
        	try {
	        	romOrig = himOrig.getBand(band);
	        	romComp = himComp.getBand(band);
        	} catch (Exception ex) {
        		return; //if one image has updated and the other hasn't, avoid conflicts
        	}
        	if (!romOrig.sizeEquals(romComp))
        		return; //two different sizes loaded
        	
        	double maxse = BandMetrics.maxSE(romOrig, romComp);
        	double mse = BandMetrics.MSE(romOrig, romComp);
        	double snr = BandMetrics.SNR(romOrig, romComp);
        	double psnr = BandMetrics.PSNR(romOrig, romComp);
        	
        	bandMetrics.setText( 
        			"BAND: mse: " + (long) mse + 
        			" snr: " + String.format(Config.DOUBLE_FORMAT, snr) + 
        			" psnr: " + String.format(Config.DOUBLE_FORMAT, psnr) + 
        			" maxse: " + (long) maxse);
        };
        
        spinnerRed.valueProperty().addListener(bandMetricRefresher);
        himOrig.modelChangedProperty().addListener(bandMetricRefresher);
        himComp.modelChangedProperty().addListener(bandMetricRefresher);
        //image metrics below
        Label imageMetrics = new Label();
        InvalidationListener imageMetricRefresher = e -> {
        	if (!himOrig.sizeEquals(himComp))
        		return;
        	
        	double maxse = ImageMetrics.maxSE(himOrig, himComp);
        	double mse = ImageMetrics.MSE(himOrig, himComp);
        	double snr = ImageMetrics.SNR(himOrig, himComp);
        	double psnr = ImageMetrics.PSNR(himOrig, himComp);
        	
        	imageMetrics.setText( 
        			"IMAGE: mse: " + (long) mse + 
        			" snr: " + String.format(Config.DOUBLE_FORMAT, snr) + 
        			" psnr: " + String.format(Config.DOUBLE_FORMAT, psnr) + 
        			" maxse: " + (long) maxse);
        	
        };
        himOrig.modelChangedProperty().addListener(imageMetricRefresher);
        himComp.modelChangedProperty().addListener(imageMetricRefresher);
        /********************/
        

        
		
		/** Putting all things together */
		VBox vbox = new VBox(originalHBox, compressedHBox, bandSelector, bvboxOrig, bvboxComp, bvboxDiff, pixelMetrics, bandMetrics, imageMetrics);
		
		
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
		MatrixViewPane mvpOrig = new MatrixViewPane();
		MatrixViewPaneController mvpcOrig = new MatrixViewPaneController(himOrig, mvpOrig, properties);
		mvpcOrig.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcOrig.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcOrig.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		HyperspectralImageStage mvsOrig = new HyperspectralImageStage(mvpOrig, "Original");
		
		mvsOrig.show();
		/*************************/
		
		/** and another one */
		MatrixViewPane mvpComp = new MatrixViewPane();
		MatrixViewPaneController mvpcComp = new MatrixViewPaneController(himComp, mvpComp, properties);
		mvpcComp.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcComp.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcComp.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		
		HyperspectralImageStage mvsComp = new HyperspectralImageStage(mvpComp, "Compressed");

		mvsComp.show();
		/**************************/
		
		
		/** and another one */
		MatrixViewPane mvpDiff = new MatrixViewPane();
		MatrixViewPaneController mvpcDiff = new MatrixViewPaneController(hdm, mvpDiff, properties);
		mvpcDiff.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcDiff.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcDiff.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		
		HyperspectralImageStage mvsDiff = new HyperspectralImageStage(mvpDiff, "Difference");
		
		mvsDiff.show();
		/**************************/
		
		
		/** bind stuff */
		ibvOrigRed.integerValueProperty().bind(mvpcOrig.selectedRValueProperty());
		ibvOrigGreen.integerValueProperty().bind(mvpcOrig.selectedGValueProperty());
		ibvOrigBlue.integerValueProperty().bind(mvpcOrig.selectedBValueProperty());
		ibvCompRed.integerValueProperty().bind(mvpcComp.selectedRValueProperty());
		ibvCompGreen.integerValueProperty().bind(mvpcComp.selectedGValueProperty());
		ibvCompBlue.integerValueProperty().bind(mvpcComp.selectedBValueProperty());
		ibvDiffRed.integerValueProperty().bind(mvpcDiff.selectedRValueProperty());
		ibvDiffGreen.integerValueProperty().bind(mvpcDiff.selectedGValueProperty());
		ibvDiffBlue.integerValueProperty().bind(mvpcDiff.selectedBValueProperty());
		
		InvalidationListener resizeAfterModelChange = e -> {
			int newrows = himOrig.getRows();
			int newcols = himOrig.getCols();
			mvsComp.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsComp.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
			mvsOrig.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsOrig.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
			mvsDiff.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsDiff.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
		};
		
		himOrig.modelChangedProperty().addListener(resizeAfterModelChange);
		
		Stage histogramStage = new Stage();
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
		spinnerBlue.valueProperty().addListener(histogramRefresher);
		
		
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