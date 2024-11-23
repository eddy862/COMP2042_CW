package com.example.demo.ui.inGameElement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an image of a shield in the game.
 * This class extends ImageView and provides methods to show and hide the shield image.
 */
public class ShieldImage extends ImageView {

	/**
	 * The path to the shield image.
	 */
    private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	/**
	 * The size of the shield image.
	 */
    public static final int SHIELD_SIZE = 200;

    /**
     * Constructs a ShieldImage object and initializes its properties.
     * Sets the image, size, and visibility of the shield image.
     */
    public ShieldImage() {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(SHIELD_SIZE);
        this.setFitWidth(SHIELD_SIZE);
    }

    /**
     * Shows the shield image.
     */
    public void showShield() {
        this.setVisible(true);
    }

    /**
     * Hides the shield image.
     */
    public void hideShield() {
        this.setVisible(false);
    }
}