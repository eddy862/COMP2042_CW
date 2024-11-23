package com.example.demo.ui.inGameElement;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Represents a pause button in the game.
 * This class extends ImageView and provides functionality to pause the game when clicked.
 */
public class PauseButton extends ImageView {
    /**
     * The size of the pause button.
     */
    private static final double PAUSE_BUTTON_SIZE = 60;
    /**
     * The x-position of the pause button.
     */
    private static final double PAUSE_BUTTON_X_POSITION = Main.SCREEN_WIDTH - 100;
    /**
     * The y-position of the pause button.
     */
    private static final double PAUSE_BUTTON_Y_POSITION = 25;
    /**
     * The path to the pause button image.
     */
    private static final String PAUSE_BUTTON_IMAGE = "/com/example/demo/images/pauseButton.png";

    /**
     * Constructs a PauseButton object and initializes its properties.
     * Sets the image, size, position, and click event handler for the pause button.
     *
     * @param onPause the action to perform when the pause button is clicked
     */
    public PauseButton(Runnable onPause) {
        this.setImage(new Image(getClass().getResource(PAUSE_BUTTON_IMAGE).toExternalForm()));
        this.setFitHeight(PAUSE_BUTTON_SIZE);
        this.setPreserveRatio(true);
        this.setLayoutX(PAUSE_BUTTON_X_POSITION);
        this.setLayoutY(PAUSE_BUTTON_Y_POSITION);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onPause.run());
    }
}