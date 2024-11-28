package com.example.demo.actor.projectile;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserProjectileTest {
    private UserProjectile userProjectile;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            userProjectile = new UserProjectile(500, 500);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userProjectileInitialYPosition() {
        assertEquals(500, userProjectile.getLayoutY());
    }

    @Test
    void userProjectileInitialXPosition() {
        assertEquals(500, userProjectile.getLayoutX());
    }

    @Test
    void userProjectileMovesHorizontally() {
        double initialX = userProjectile.getLayoutX();
        userProjectile.updatePosition();
        assertEquals(initialX + 15, userProjectile.getLayoutX() + userProjectile.getTranslateX());
    }

    @Test
    void userProjectileDoesNotMoveVertically() {
        double initialY = userProjectile.getLayoutY();
        userProjectile.updatePosition();
        assertEquals(initialY, userProjectile.getLayoutY() + userProjectile.getTranslateY());
    }
}
