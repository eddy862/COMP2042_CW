package com.example.demo.actor.plane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiStageBossTest {
    private MultiStageBoss multiStageBoss;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            multiStageBoss = new MultiStageBoss(10, 20);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void multiStageBossStartsAtStageOne() {
        assertEquals(1, multiStageBoss.getStage());
    }

    @Test
    void multiStageBossAdvancesToStageTwoWhenHealthBelowThreshold() {
        for (int i = 0; i < 11; i++) {
            multiStageBoss.takeDamage();
        }
        assertEquals(2, multiStageBoss.getStage());
    }

    @Test
    void multiStageBossDoesNotAdvanceToStageTwoWhenHealthAboveThreshold() {
        for (int i = 0; i < 9; i++) {
            multiStageBoss.takeDamage();
        }
        assertEquals(1, multiStageBoss.getStage());
    }

    @Test
    void multiStageBossHealthDecreasesCorrectly() {
        int initialHealth = multiStageBoss.getHealth();
        multiStageBoss.takeDamage();
        assertEquals(initialHealth - 1, multiStageBoss.getHealth());
    }

    @Test
    void multiStageBossDestroyedWhenHealthReachesZero() {
        for (int i = 0; i < 30; i++) {
            multiStageBoss.takeDamage();
        }
        assertEquals(0, multiStageBoss.getHealth());
    }
}
