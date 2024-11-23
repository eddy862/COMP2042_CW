package com.example.demo.ui.inGameElement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an explosion image in the game.
 * This class extends ImageView and provides methods to show and hide the explosion image.
 */
public class ExplosionImage extends ImageView {
    /**
     * The path to the explosion image.
     */
    private static final String EXPLOSION_IMAGE = "/com/example/demo/images/explosion.png";
    /**
     * The size of the explosion image.
     */
    private static final int EXPLOSION_SIZE = 100;

    /**
     * Constructs an ExplosionImage object with the specified position.
     *
     * @param xPosition the x-position of the explosion image
     * @param yPosition the y-position of the explosion image
     */
    public ExplosionImage(double xPosition, double yPosition) {
        this.setImage(new Image(getClass().getResource(EXPLOSION_IMAGE).toExternalForm()));
        this.setFitHeight(EXPLOSION_SIZE);
        this.setPreserveRatio(true);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setVisible(false);
    }

    /**
     * Shows the explosion image.
     */
    public void show() {
        this.setVisible(true);
    }

    /**
     * Hides the explosion image.
     */
    public void hide() {
        this.setVisible(false);
    }
}