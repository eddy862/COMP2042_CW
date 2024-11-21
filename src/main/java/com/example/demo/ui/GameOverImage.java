package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	private static final int HEIGHT = 700;
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int X_POSITION = 275;
	private static final int Y_POSITION = -75;

	public GameOverImage() {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
		setLayoutX(X_POSITION);
		setLayoutY(Y_POSITION);
		setPreserveRatio(true);
		setFitHeight(HEIGHT);
	}
}
