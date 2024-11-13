package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PauseButton extends ImageView {
    private static final double PAUSE_BUTTON_SIZE = 60;
    private static final double PAUSE_BUTTON_X_POSITION = Main.SCREEN_WIDTH - 100;
    private static final double PAUSE_BUTTON_Y_POSITION = 25;
    private static final String PAUSE_BUTTON_IMAGE = "/com/example/demo/images/pauseButton.png";

    public PauseButton(Runnable onPause) {
        this.setImage(new Image(getClass().getResource(PAUSE_BUTTON_IMAGE).toExternalForm()));
        this.setFitHeight(PAUSE_BUTTON_SIZE);
        this.setPreserveRatio(true);
        this.setLayoutX(PAUSE_BUTTON_X_POSITION);
        this.setLayoutY(PAUSE_BUTTON_Y_POSITION);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onPause.run());
    }
}
