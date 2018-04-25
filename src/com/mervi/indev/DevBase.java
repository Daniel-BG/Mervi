package com.mervi.indev;

import java.util.Random;

import org.controlsfx.control.RangeSlider;

import com.mervi.view.HistogramView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DevBase extends Application {
	
	/*RangeSlider slider = new RangeSlider(0, 100, 20, 40);
	
	slider.setShowTickLabels(true);
	slider.setShowTickMarks(true);
	slider.setMajorTickUnit(50);
	slider.setMinorTickCount(5);
	slider.setBlockIncrement(10);
	
	
	scene = new Scene(new VBox(slider), 400, 300);	*/

	Scene scene;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		window.setResizable(true);
		
		HistogramView hv = new HistogramView();

		

		scene = new Scene(hv, 400, 300);	
		window.setScene(scene);
		
		window.setTitle("test");
		window.show();
		
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        Platform.exit();
		    }
		});
		

		Random r = new Random(1);
		int ybase = r.nextInt(100);
		int yvar = r.nextInt(20) + 1;
		int xlim = 200;
		for (int i = 0; i < xlim; i++) {
			hv.getSeries().getData().add(new XYChart.Data<Number, Number>(i, ybase));
			ybase += r.nextInt(yvar) - yvar/2;
		}
		


		

	
	}
	
	
	



}

