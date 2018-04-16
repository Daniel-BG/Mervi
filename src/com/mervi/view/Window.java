package com.mervi.view;


import com.mervi.control.MatrixViewPaneController;
import com.mervi.model.HyperspectralImageModel;

import javafx.application.Application;
import javafx.event.Event;
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
		HyperspectralImageModel him = new HyperspectralImageModel();
		
		
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
        
		him.bandsProperty().addListener((observable, oldVal, newVal) -> {
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
        
        bandSelector.getChildren().addAll(labelRed, spinnerRed, labelBlue, spinnerBlue, labelGreen, spinnerGreen);
        
		
		/** Putting all things together */
		VBox vbox = new VBox();
		vbox.getChildren().addAll(originalHBox, compressedHBox, bandSelector);
		
		
		/** Create and show the scene */
		scene = new Scene(vbox, 400, 300);	
		window.setScene(scene);
		window.setTitle("test");
		window.show();
		
		
		/** this creates one view */
		int bands = 40, rows = 40, cols = 40;
		MatrixViewPane mvp = new MatrixViewPane();
		MatrixViewPaneController mvpc = new MatrixViewPaneController(him, mvp);
		mvpc.selectedRProperty().bind(spinnerRed.valueProperty());
		mvpc.selectedGProperty().bind(spinnerGreen.valueProperty());
		mvpc.selectedBProperty().bind(spinnerBlue.valueProperty());
		
		MatrixViewStage mvs = new MatrixViewStage(mvp);
		him.setSize(bands, rows, cols);
		him.randomize(0);
		
		mvs.show();
		/*************************/
		
		

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