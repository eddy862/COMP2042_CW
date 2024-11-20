package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;
	private static final int X_POSITION = 355;
	private static final int Y_POSITION = 125;
	
	public WinImage() {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(X_POSITION);
		this.setLayoutY(Y_POSITION);
	}
	
	public void showWinImage() {
		this.setVisible(true);
	}

}
