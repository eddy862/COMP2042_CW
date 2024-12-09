package com.example.demo.ui.inGameElement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the game over image displayed in the game.
 * This class extends ImageView and sets the game over image with specific layout properties.
 */
public class GameOverImage extends ImageView {
	/**
	 * The width of the game over image.
	 */
    private static final int HEIGHT = 700;
	/**
	 * The path to the game over image.
	 */
    private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	/**
	 * The x position of the game over image.
	 */
    private static final int X_POSITION = 275;
	/**
	 * The y position of the game over image.
	 */
    private static final int Y_POSITION = -75;

    /**
     * Constructs a GameOverImage object and initializes its properties.
     * Sets the image, layout position, and size of the game over image.
     */
    public GameOverImage() {
        setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        setLayoutX(X_POSITION);
        setLayoutY(Y_POSITION);
        setPreserveRatio(true);
        setFitHeight(HEIGHT);
    }
}