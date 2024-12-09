package com.example.demo.actor.plane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyPlanTest {
    private EnemyPlane enemyPlane;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            enemyPlane = new EnemyPlane(500, 500);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void enemyPlaneMovesHorizontally() {
        double initialX = enemyPlane.getLayoutX();
        enemyPlane.updatePosition();
        assertEquals(initialX - 6, enemyPlane.getLayoutX() + enemyPlane.getTranslateX());
    }

    @Test
    void enemyPlaneEntersWarningArea() {
        enemyPlane.setLayoutX(200);
        enemyPlane.updateActor();
        assertTrue(enemyPlane.getInWarningArea());
    }

    @Test
    void enemyPlaneDoesNotEnterWarningArea() {
        assertFalse(enemyPlane.getInWarningArea());
    }
}
