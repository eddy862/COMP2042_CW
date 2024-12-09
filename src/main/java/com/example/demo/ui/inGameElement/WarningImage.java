package com.example.demo.ui.inGameElement;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Represents a warning image in the game.
 * This class extends ImageView and provides methods to show and hide the warning image.
 */
public class WarningImage extends ImageView {

    /**
     * The path to the warning image.
     */
    private static final String IMAGE_NAME = "/com/example/demo/images/warning.png";
    /**
     * The size of the warning image.
     */
    public static final int WARNING_SIZE = 100;
    /**
     * The x position of the warning image.
     */
    private static final int X_POSITION = 50;

    /**
     * Constructs a WarningImage object and initializes its properties.
     * Sets the image, size, position, and visibility of the warning image.
     *
     * @param yPosition the y-coordinate position of the warning image
     */
    public WarningImage(double yPosition) {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setFitHeight(WARNING_SIZE);
        this.setPreserveRatio(true);
        this.setFitHeight(X_POSITION);
        this.setLayoutY(yPosition);
        this.setVisible(false);
    }

    /**
     * Shows the warning image.
     */
    public void show() {
        this.setVisible(true);
    }

    /**
     * Hides the warning image.
     */
    public void hide() {
        this.setVisible(false);
    }
}