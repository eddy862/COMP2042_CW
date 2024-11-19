package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExplosionImage extends ImageView {
    private static final String EXPLOSION_IMAGE = "/com/example/demo/images/explosion.png";
    private static final int EXPLOSION_SIZE = 100;

    public ExplosionImage(double xPosition, double yPosition) {
        this.setImage(new Image(getClass().getResource(EXPLOSION_IMAGE).toExternalForm()));
        this.setFitHeight(EXPLOSION_SIZE);
        this.setPreserveRatio(true);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }
}
