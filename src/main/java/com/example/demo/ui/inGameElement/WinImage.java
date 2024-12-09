package com.example.demo.ui.inGameElement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the "You Win" image in the game.
 * This class extends ImageView and provides methods to show the win image.
 */
public class WinImage extends ImageView {

	/**
	 * The path to the win image.
	 */
    private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	/**
	 * The size of the win image.
	 */
    private static final int HEIGHT = 500;
	/**
	 * The width of the win image.
	 */
    private static final int WIDTH = 600;
	/**
	 * The x position of the win image.
	 */
    private static final int X_POSITION = 355;
	/**
	 * The y position of the win image.
	 */
    private static final int Y_POSITION = 125;

    /**
     * Constructs a WinImage object and initializes its properties.
     * Sets the image, size, position, and visibility of the win image.
     */
    public WinImage() {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(X_POSITION);
        this.setLayoutY(Y_POSITION);
    }
}