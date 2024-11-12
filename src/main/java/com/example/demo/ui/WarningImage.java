package com.example.demo.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class WarningImage extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/warning.png";
    public static final int WARNING_SIZE = 100;
//    private final Timeline flashTimeline;
    private static final int X_POSITION = 50;

    public WarningImage(double yPosition) {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setFitHeight(WARNING_SIZE);
        this.setPreserveRatio(true);
        this.setFitHeight(X_POSITION);
        this.setLayoutY(yPosition);
        this.setVisible(false);
    }

    public void showWarning() {
        this.setVisible(true);
    }

    public void hideWarning() {
        this.setVisible(false);
    }
}