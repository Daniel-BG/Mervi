package com.mervi.view;


import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import com.jypec.img.HyperspectralImage;
import com.jypec.util.io.HyperspectralImageReader;
import com.mervi.Config;
import com.mervi.control.MatrixViewPaneController;
import com.mervi.model.HyperspectralDiffModel;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.MousePosition;
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
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
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
		MousePosition mp = new MousePosition();
		HyperspectralImageModel himOrig = new HyperspectralImageModel();
		HyperspectralImageModel himComp = new HyperspectralImageModel();
		HyperspectralDiffModel hdm = new HyperspectralDiffModel();
		hdm.setSources(himOrig, himComp);
		
		
		
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
				himOrig.setFromImage(hi);
			} catch (IOException e1) {
				System.out.println("Did not load image");
			}
		});
		originalButton.setMinWidth(90);
		final HBox originalHBox = new HBox(originalButton, originalTextBox);
		
		/** Compressed image selector */
		final TextField compressedTextBox = new TextField("Compressed");
		compressedTextBox.setEditable(false);
		compressedTextBox.setPrefWidth(4000);
		final Button compressedButton = new Button("Compressed");
		compressedButton.setMinWidth(90);
		compressedButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File f = fileChooser.showOpenDialog(window);
			try {
				compressedTextBox.setText(f.toString());	
				HyperspectralImage hi = HyperspectralImageReader.read(f.toString(), true);
				himComp.setFromImage(hi);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
						
					
		});
		final HBox compressedHBox = new HBox(compressedButton, compressedTextBox);
		
		/** Band selector */
		final Spinner<Integer> spinnerRed = new Spinner<Integer>();
		final Spinner<Integer> spinnerBlue = new Spinner<Integer>();
		final Spinner<Integer> spinnerGreen = new Spinner<Integer>();
		final Spinner<Integer> spinnerAll = new Spinner<Integer>();
        
		himOrig.bandsProperty().addListener((observable, oldVal, newVal) -> {
			SpinnerValueFactory<Integer> valueFactoryRed = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
			SpinnerValueFactory<Integer> valueFactoryGreen = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
			SpinnerValueFactory<Integer> valueFactoryBlue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
			SpinnerValueFactory<Integer> valueFactoryAll = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
	        spinnerRed.setValueFactory(valueFactoryRed);
	        spinnerBlue.setValueFactory(valueFactoryBlue);
	        spinnerGreen.setValueFactory(valueFactoryGreen);
	        spinnerAll.setValueFactory(valueFactoryAll);
	        
	        Function<SpinnerValueFactory<Integer>, EventHandler<? super ScrollEvent>> createScrollEventHandler = 
	        		new Function<SpinnerValueFactory<Integer>, EventHandler<? super ScrollEvent>>() {
				@Override
				public EventHandler<? super ScrollEvent> apply(SpinnerValueFactory<Integer> t) {
					return e -> {
						if (e.getDeltaY() > 0) t.increment(((int) e.getDeltaY()) / Config.SCROLL_UNITS); 
						else t.decrement((-(int) e.getDeltaY()) / Config.SCROLL_UNITS);
					};
				}
	        };
	        
			spinnerRed.setOnScroll(createScrollEventHandler.apply(valueFactoryRed));
			spinnerGreen.setOnScroll(createScrollEventHandler.apply(valueFactoryGreen));
			spinnerBlue.setOnScroll(createScrollEventHandler.apply(valueFactoryBlue));
			
			spinnerAll.setOnScroll(e -> {
				if (e.getDeltaY() > 0) spinnerAll.increment(((int) e.getDeltaY()) / Config.SCROLL_UNITS); 
				else spinnerAll.decrement((-(int) e.getDeltaY()) / Config.SCROLL_UNITS);
				
				valueFactoryBlue.setValue(spinnerAll.getValue());
				valueFactoryRed.setValue(spinnerAll.getValue());
				valueFactoryGreen.setValue(spinnerAll.getValue());
			});
		});
		
        Label coordLabel = new Label(" @(x, x)");
        InvalidationListener mpChangedCoord = e -> {
        	coordLabel.setText(" @: (" + mp.rowProperty().intValue() + "," + mp.colProperty().intValue() + ")");
        };
        mp.colProperty().addListener(mpChangedCoord);
        mp.rowProperty().addListener(mpChangedCoord);
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
        	int row = mp.rowProperty().intValue();
        	int col = mp.colProperty().intValue();
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
        
        mp.rowProperty().addListener(pixelMetricRefresher);
        mp.colProperty().addListener(pixelMetricRefresher);
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
		MatrixViewPaneController mvpcOrig = new MatrixViewPaneController(himOrig, mvpOrig, mp);
		mvpcOrig.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcOrig.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcOrig.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		MatrixViewStage mvsOrig = new MatrixViewStage(mvpOrig, "Original");
		
		mvsOrig.show();
		/*************************/
		
		/** and another one */
		MatrixViewPane mvpComp = new MatrixViewPane();
		MatrixViewPaneController mvpcComp = new MatrixViewPaneController(himComp, mvpComp, mp);
		mvpcComp.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcComp.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcComp.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		
		MatrixViewStage mvsComp = new MatrixViewStage(mvpComp, "Compressed");

		mvsComp.show();
		/**************************/
		
		
		/** and another one */
		MatrixViewPane mvpDiff = new MatrixViewPane();
		MatrixViewPaneController mvpcDiff = new MatrixViewPaneController(hdm, mvpDiff, mp);
		mvpcDiff.selectedRIndexProperty().bind(spinnerRed.valueProperty());
		mvpcDiff.selectedGIndexProperty().bind(spinnerGreen.valueProperty());
		mvpcDiff.selectedBIndexProperty().bind(spinnerBlue.valueProperty());
		
		MatrixViewStage mvsDiff = new MatrixViewStage(mvpDiff, "Difference");
		
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
			int newrows = himOrig.rowsProperty().intValue();
			int newcols = himOrig.colsProperty().intValue();
			mvsComp.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsComp.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
			mvsOrig.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsOrig.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
			mvsDiff.setWidth(newcols*Config.DEFAULT_PIXEL_SIZE);
			mvsDiff.setHeight(newrows*Config.DEFAULT_PIXEL_SIZE);
		};
		
		himOrig.colsProperty().addListener(resizeAfterModelChange);
		himOrig.rowsProperty().addListener(resizeAfterModelChange);
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