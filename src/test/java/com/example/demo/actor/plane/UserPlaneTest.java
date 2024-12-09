package com.example.demo.actor.plane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserPlaneTest {
    private UserPlane userPlane;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            userPlane = new UserPlane(10);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void userPlaneInitialHealth() {
        assertEquals(10, userPlane.getHealth());
    }

    @Test
    void userPlaneStopsMovingVertically() {
        userPlane.moveUp();
        userPlane.stopVertically();
        userPlane.updatePosition();
        assertEquals(0, userPlane.getTranslateY());
    }

    @Test
    void userPlaneStopsMovingHorizontally() {
        userPlane.moveRight();
        userPlane.stopHorizontally();
        userPlane.updatePosition();
        assertEquals(0, userPlane.getTranslateX());
    }

    @Test
    void userPlaneFiresProjectile() {
        assertNotNull(userPlane.fireProjectile());
    }

    @Test
    void userPlaneIncrementsKillCount() {
        int initialKills = userPlane.getNumberOfKills();
        userPlane.incrementKillCount();
        assertEquals(initialKills + 1, userPlane.getNumberOfKills());
    }
}
