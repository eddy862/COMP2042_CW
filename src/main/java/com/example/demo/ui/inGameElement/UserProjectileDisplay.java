package com.example.demo.ui.inGameElement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a display for user projectiles in the game.
 * This class creates a layout to display a specified number of remaining projectiles and provides methods to update the display.
 */
public class UserProjectileDisplay {
    /**
     * The container holding the projectile images.
     */
    private HBox container;
    /**
     * The x position of the container.
     */
    private static final double X_POSITION = 400;
    /**
     * The y position of the container.
     */
    private static final double Y_POSITION = 25;
    /**
     * The path to the projectile image.
     */
    private static final String PROJECTILE_IMAGE_NAME = "/com/example/demo/images/projectile.png";
    /**
     * The height of the projectile image.
     */
    private static final int PROJECTILE_HEIGHT = 50;
    /**
     * The number of projectiles to display.
     */
    private final int numberOfProjectilesToDisplay;

    /**
     * Constructs a UserProjectileDisplay object with the specified number of projectiles to display.
     *
     * @param projectilesToDisplay the number of projectiles to display
     */
    public UserProjectileDisplay(int projectilesToDisplay) {
        this.numberOfProjectilesToDisplay = projectilesToDisplay;
        initializeContainer();
        initializeProjectiles();
    }

    /**
     * Initializes the container for the projectiles.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(X_POSITION);
        container.setLayoutY(Y_POSITION);
    }

    /**
     * Initializes the projectiles and adds them to the container.
     */
    private void initializeProjectiles() {
        for (int i = 0; i < numberOfProjectilesToDisplay; i++) {
            ImageView projectile = new ImageView(new Image(getClass().getResource(PROJECTILE_IMAGE_NAME).toExternalForm()));
            projectile.setFitHeight(PROJECTILE_HEIGHT);
            projectile.setPreserveRatio(true);
            container.getChildren().add(projectile);
        }
    }

    /**
     * Updates the visibility of the projectiles based on the remaining number of projectiles.
     *
     * @param userProjectileRemaining the number of remaining projectiles
     */
    public void updateProjectiles(int userProjectileRemaining) {
        for (int i = 0; i < container.getChildren().size(); i++) {
            container.getChildren().get(i).setVisible(i < userProjectileRemaining);
        }
    }

    /**
     * Retrieves the container of the projectiles.
     *
     * @return the container of the projectiles
     */
    public HBox getContainer() {
        return container;
    }

    /**
     * Retrieves the number of projectiles to display.
     *
     * @return the number of projectiles to display
     */
    public int getNumberOfProjectilesToDisplay() {
        return numberOfProjectilesToDisplay;
    }
}