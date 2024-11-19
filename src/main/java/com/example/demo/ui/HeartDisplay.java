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
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;
	
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
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
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
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
