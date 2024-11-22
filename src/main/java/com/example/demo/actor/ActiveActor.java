package com.example.demo.actor;

import javafx.scene.image.*;

/**
 * Represents actors that can move including planes and projectiles.
 */
public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos); // set initial x position
		this.setLayoutY(initialYPos); // set initial y position
		this.setFitHeight(imageHeight); // set image height
		this.setPreserveRatio(true);
	}

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	protected void changeImage(String imageName) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
	}
}
