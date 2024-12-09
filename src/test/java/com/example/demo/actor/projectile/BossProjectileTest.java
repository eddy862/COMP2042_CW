package com.example.demo.actor.projectile;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BossProjectileTest {
    private BossProjectile bossProjectile;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            bossProjectile = new BossProjectile(100);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bossProjectileInitialYPosition() {
        assertEquals(100, bossProjectile.getLayoutY());
    }

    @Test
    void bossProjectileMovesHorizontally() {
        double initialX = bossProjectile.getLayoutX();
        bossProjectile.updatePosition();
        assertEquals(initialX - 15, bossProjectile.getLayoutX() + bossProjectile.getTranslateX());
    }

    @Test
    void bossProjectileDoesNotMoveVertically() {
        double initialY = bossProjectile.getLayoutY();
        bossProjectile.updatePosition();
        assertEquals(initialY, bossProjectile.getLayoutY() + bossProjectile.getTranslateY());
    }
}
