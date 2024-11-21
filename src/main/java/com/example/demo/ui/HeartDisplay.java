package com.example.demo.ui;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class HeartDisplay {
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private HBox container;
	private final double containerXPosition = 10;
	private final double containerYPosition = 25;
	private final int numberOfHeartsToDisplay;
	
	public HeartDisplay(int heartsToDisplay) {
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}
	
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}
	
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}
	
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(0);
	}
	
	public HBox getContainer() {
		return container;
	}

	private ScaleTransition createZoomTransition(Node node) {
		ScaleTransition transition = new ScaleTransition(Duration.millis(200), node);
		transition.setFromX(1.0);
		transition.setToX(1.2);
		transition.setFromY(1.0);
		transition.setToY(1.2);
		transition.setCycleCount(ScaleTransition.INDEFINITE);
		transition.setAutoReverse(true);
		return transition;
	}

	public void startZooming() {
		for (Node heart : container.getChildren()) {
			ScaleTransition transition = createZoomTransition(heart);
			transition.play();
		}
	}

	public void stopZooming() {
		for (Node heart : container.getChildren()) {
			heart.setScaleX(1.0);
			heart.setScaleY(1.0);
		}
	}
}
