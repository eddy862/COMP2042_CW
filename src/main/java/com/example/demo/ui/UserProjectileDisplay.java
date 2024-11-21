package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class UserProjectileDisplay {
    private HBox container;
    private static final double X_POSITION = 400;
    private static final double Y_POSITION = 25;
    private static final String PROJECTILE_IMAGE_NAME = "/com/example/demo/images/projectile.png";
    private static final int PROJECTILE_HEIGHT = 50;
    private final int numberOfProjectilesToDisplay;

    public UserProjectileDisplay(int projectilesToDisplay) {
        this.numberOfProjectilesToDisplay = projectilesToDisplay;
        initializeContainer();
        initializeProjectiles();
    }

    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(X_POSITION);
        container.setLayoutY(Y_POSITION);
    }

    private void initializeProjectiles() {
        for (int i = 0; i < numberOfProjectilesToDisplay; i++) {
            ImageView projectile = new ImageView(new Image(getClass().getResource(PROJECTILE_IMAGE_NAME).toExternalForm()));
            projectile.setFitHeight(PROJECTILE_HEIGHT);
            projectile.setPreserveRatio(true);
            container.getChildren().add(projectile);
        }
    }

    public void updateProjectiles(int userProjectileRemaining) {
        for (int i = 0; i < container.getChildren().size(); i++) {
            container.getChildren().get(i).setVisible(i < userProjectileRemaining);
        }
    }

    public HBox getContainer() {
        return container;
    }
}
