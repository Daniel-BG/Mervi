package com.mervi.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatrixViewStage extends Stage {
	
	public MatrixViewStage(MatrixViewPane mvp) {
		Scene scene = new Scene(mvp, 200, 200);	
		
		this.setScene(scene);
		this.setTitle("test");
		this.show();
	}

}
