package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WarningImage extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/warning.png";
    public static final int WARNING_HEIGHT = 100;
    private static final int X_POSITION = 50;

    public WarningImage(double yPosition) {
        this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setFitHeight(WARNING_HEIGHT);
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