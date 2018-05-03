package com.mervi.view;

import java.io.File;

import com.jypec.img.HyperspectralImage;
import com.jypec.util.io.HyperspectralImageReader;
import com.mervi.model.ConcreteHyperspectralImageModel;
import com.mervi.model.HyperspectralImageModel;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HyperspectralImageSelector extends HBox {

	public HyperspectralImageSelector(Stage parent, ObjectProperty<HyperspectralImageModel> target) {
		final TextField originalTextBox = new TextField("Original");
		originalTextBox.setEditable(false);
		originalTextBox.setPrefWidth(4000);
		final Button originalButton = new Button("Original");
		originalButton.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File f = fileChooser.showOpenDialog(parent);
			try {
				originalTextBox.setText(f.toString());
				HyperspectralImage hi = HyperspectralImageReader.read(f.toString(), true);
				//create image
				target.set(new ConcreteHyperspectralImageModel(hi));
			} catch (Exception e1) {
				System.out.println("Did not load image");
			}
		});
		originalButton.setMinWidth(90);
		
		this.getChildren().addAll(originalButton, originalTextBox);
	}
	
}
