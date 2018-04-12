package com.mervi.view;

import com.mervi.control.ColorMatrixCanvasController;
import com.mervi.model.MatrixModel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Window extends Application {
	
	Stage window;
	Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		//set canvas up 
		//https://stackoverflow.com/questions/23449932/javafx-resize-canvas-when-screen-is-resized
		int rows = 40, cols = 40;
		
		ColorMatrixCanvas cmc = new ColorMatrixCanvas();
		ColorMatrixCanvasController cmcc = new ColorMatrixCanvasController();
		MatrixModel mm = new MatrixModel(rows, cols);
		
		cmc.setController(cmcc);
		cmcc.setModel(mm);
		cmcc.setView(cmc);
		
		/**
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.fillOval(0,  0,  200,  200);*/
		
		//layout
		StackPane layout = new StackPane();
		layout.getChildren().add(cmc);
		scene = new Scene(layout, 200, 200);	
		
		window.setScene(scene);
		window.setTitle("test");
		window.show();
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