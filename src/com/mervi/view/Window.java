package com.mervi.view;


import com.mervi.control.MatrixViewPaneController;
import com.mervi.model.HyperspectralDiffModel;
import com.mervi.model.HyperspectralImageModel;
import com.mervi.model.MousePosition;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Window extends Application {
	
	Stage window;
	Scene scene;
	
	private static final int SQUARE_SIZE = 2; //in pixels
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		window.setResizable(true);
		
		//create model to be filled up
		int bands = 40, rows = 300, cols = 600;
		MousePosition mp = new MousePosition();
		HyperspectralImageModel himOrig = new HyperspectralImageModel();
		HyperspectralImageModel himComp = new HyperspectralImageModel();
		HyperspectralDiffModel hdm = new HyperspectralDiffModel();
		
		
		
		//set canvas up 
		//https://stackoverflow.com/questions/23449932/javafx-resize-canvas-when-screen-is-resized
		
		/** Original image selector */
		final HBox originalHBox = new HBox();
		final TextField originalTextBox = new TextField("Original");
		originalTextBox.setPrefWidth(4000);
		final Button originalButton = new Button("Original");
		originalButton.setMinWidth(90);
		originalHBox.getChildren().addAll(originalButton, originalTextBox);
		
		/** Compressed image selector */
		final HBox compressedHBox = new HBox();
		final TextField compressedTextBox = new TextField("Compressed");
		compressedTextBox.setPrefWidth(4000);
		final Button compressedButton = new Button("Compressed");
		compressedButton.setMinWidth(90);
		compressedHBox.getChildren().addAll(compressedButton, compressedTextBox);
		
		/** Band selector */
		final HBox bandSelector = new HBox();
		
		final Label labelRed = new Label("red");
		labelRed.setMinWidth(30);
		final Spinner<Integer> spinnerRed = new Spinner<Integer>();
        
        final Label labelBlue = new Label("blue");
        labelBlue.setMinWidth(35);
		final Spinner<Integer> spinnerBlue = new Spinner<Integer>();
		
        final Label labelGreen = new Label("green");
        labelGreen.setMinWidth(40);
		final Spinner<Integer> spinnerGreen = new Spinner<Integer>();
        
		himOrig.bandsProperty().addListener((observable, oldVal, newVal) -> {
			SpinnerValueFactory<Integer> valueFactoryRed = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
			SpinnerValueFactory<Integer> valueFactoryGreen = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
			SpinnerValueFactory<Integer> valueFactoryBlue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newVal.intValue() - 1, 0);
	        spinnerRed.setValueFactory(valueFactoryRed);
	        spinnerBlue.setValueFactory(valueFactoryBlue);
	        spinnerGreen.setValueFactory(valueFactoryGreen);
	        
			spinnerRed.setOnScroll(e -> {
				if (e.getDeltaY() > 0) {
					valueFactoryRed.increment(1);
				} else {
					valueFactoryRed.decrement(1);
				}
			});
			
			spinnerGreen.setOnScroll(e -> {
				if (e.getDeltaY() > 0) {
					valueFactoryGreen.increment(1);
				} else {
					valueFactoryGreen.decrement(1);
				}
			});
			
			spinnerBlue.setOnScroll(e -> {
				if (e.getDeltaY() > 0) {
					valueFactoryBlue.increment(1);
				} else {
					valueFactoryBlue.decrement(1);
				}
			});
		});
        
        bandSelector.getChildren().addAll(labelRed, spinnerRed, labelGreen, spinnerGreen, labelBlue, spinnerBlue);
        
        
        /** Bit viewers */
        HBox bvboxOrig = new HBox();
        Label origBits = new Label("(Orig)");
        IntegerBinaryViewer ibvOrigRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvOrigBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvOrigGreen = new IntegerBinaryViewer(16);
        bvboxOrig.setSpacing(10);
        bvboxOrig.getChildren().addAll(ibvOrigRed, ibvOrigGreen, ibvOrigBlue, origBits);
        
        HBox bvboxComp = new HBox();
        Label compBits = new Label("(Comp)");
        IntegerBinaryViewer ibvCompRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvCompBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvCompGreen = new IntegerBinaryViewer(16);
        bvboxComp.setSpacing(10);
        bvboxComp.getChildren().addAll(ibvCompRed, ibvCompGreen, ibvCompBlue, compBits);
        
        HBox bvboxDiff = new HBox();
        Label diffBits = new Label("(Diff)");
        IntegerBinaryViewer ibvDiffRed = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvDiffBlue = new IntegerBinaryViewer(16);
        IntegerBinaryViewer ibvDiffGreen = new IntegerBinaryViewer(16);
        bvboxDiff.setSpacing(10);
        bvboxDiff.getChildren().addAll(ibvDiffRed, ibvDiffGreen, ibvDiffBlue, diffBits);
        /****************/
        
		
		/** Putting all things together */
		VBox vbox = new VBox();
		vbox.getChildren().addAll(originalHBox, compressedHBox, bandSelector, bvboxOrig, bvboxComp, bvboxDiff);
		
		
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
			mvsComp.setWidth(newcols*SQUARE_SIZE);
			mvsComp.setHeight(newrows*SQUARE_SIZE);
			mvsOrig.setWidth(newcols*SQUARE_SIZE);
			mvsOrig.setHeight(newrows*SQUARE_SIZE);
			mvsDiff.setWidth(newcols*SQUARE_SIZE);
			mvsDiff.setHeight(newrows*SQUARE_SIZE);
		};
		
		himOrig.colsProperty().addListener(resizeAfterModelChange);
		himOrig.rowsProperty().addListener(resizeAfterModelChange);
		/*************/
		
		/** Finally initialize model */
		himOrig.setSize(bands, rows, cols);
		himOrig.randomize(0);
		
		himComp.setSize(bands, rows, cols);
		himComp.randomize(1);
		
		hdm.setSources(himOrig, himComp);
		/*****************************/

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