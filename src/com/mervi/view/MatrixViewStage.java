package com.mervi.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatrixViewStage extends Stage {

	
	public MatrixViewStage(MatrixViewPane mvp, String title) {
		Scene scene = new Scene(mvp, 200, 200);	
		
		this.setScene(scene);
		this.setTitle(title);
		this.show();
	}

}
