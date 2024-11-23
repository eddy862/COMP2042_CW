package com.example.demo.ui.inGameElement;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Represents the display of hearts in the game.
 * This class manages the creation, display, and animation of heart images.
 */
public class HeartDisplay {
	/**
	 * The path to the heart image.
	 */
    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	/**
	 * The height of the heart image.
	 */
    private static final int HEART_HEIGHT = 50;
	/**
	 * The container holding the heart images.
	 */
    private HBox container;
	/**
	 * The x position of the container.
	 */
    private static final double CONTAINER_X_POSITION = 10;
    private static final double CONTAINER_Y_POSITION = 25;
    private final int numberOfHeartsToDisplay;

    /**
     * Constructs a HeartDisplay object with the specified number of hearts to display.
     *
     * @param heartsToDisplay the number of hearts to display
     */
    public HeartDisplay(int heartsToDisplay) {
        this.numberOfHeartsToDisplay = heartsToDisplay;
        initializeContainer();
        initializeHearts();
    }

    /**
     * Initializes the container for the hearts.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(CONTAINER_X_POSITION);
        container.setLayoutY(CONTAINER_Y_POSITION);
    }

    /**
     * Initializes the heart images and adds them to the container.
     */
    private void initializeHearts() {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
            heart.setFitHeight(HEART_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);
        }
    }

    /**
     * Removes one heart from the display.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(0);
    }

    /**
     * Retrieves the container holding the heart images.
     *
     * @return the container holding the heart images
     */
    public HBox getContainer() {
        return container;
    }

    /**
     * Creates a zoom transition animation for a given node.
     *
     * @param node the node to apply the zoom transition to
     * @return the created ScaleTransition object
     */
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

    /**
     * Starts the zooming animation for all heart images.
     */
    public void startZooming() {
        for (Node heart : container.getChildren()) {
            ScaleTransition transition = createZoomTransition(heart);
            transition.play();
        }
    }

    /**
     * Stops the zooming animation for all heart images.
     */
    public void stopZooming() {
        for (Node heart : container.getChildren()) {
            heart.setScaleX(1.0);
            heart.setScaleY(1.0);
        }
    }
}