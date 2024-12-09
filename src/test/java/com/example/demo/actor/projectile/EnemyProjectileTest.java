package com.example.demo.actor.projectile;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnemyProjectileTest {
    private EnemyProjectile enemyProjectile;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            enemyProjectile = new EnemyProjectile(100, 100);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void enemyProjectileInitialYPosition() {
        assertEquals(100, enemyProjectile.getLayoutY());
    }

    @Test
    void enemyProjectileInitialXPosition() {
        assertEquals(100, enemyProjectile.getLayoutX());
    }

    @Test
    void enemyProjectileMovesHorizontally() {
        double initialX = enemyProjectile.getLayoutX();
        enemyProjectile.updatePosition();
        assertEquals(initialX - 10, enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX());
    }

    @Test
    void enemyProjectileDoesNotMoveVertically() {
        double initialY = enemyProjectile.getLayoutY();
        enemyProjectile.updatePosition();
        assertEquals(initialY, enemyProjectile.getLayoutY() + enemyProjectile.getTranslateY());
    }
}
