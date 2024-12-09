package com.example.demo.level.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelViewLevelThreeTest {
    private LevelViewLevelThree levelViewLevelThree;
    private Group root;;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            root = new Group();
            levelViewLevelThree = new LevelViewLevelThree(root, 6, 3, 8, 20);
        });

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void showTimerAddsTimerLabelToRoot() {
        levelViewLevelThree.showTimer();
        assertTrue(root.getChildren().contains(levelViewLevelThree.getTimerLabel()));
    }

    @Test
    void updateTimerUpdatesTimerLabelText() {
        levelViewLevelThree.updateTimer(45);
        assertEquals("Time: 45", levelViewLevelThree.getTimerLabel().getText());
    }

    @Test
    void showUserProjectilesAddsUserProjectileDisplayToRoot() {
        levelViewLevelThree.showUserProjectiles();
        assertTrue(root.getChildren().contains(levelViewLevelThree.getUserProjectileDisplay().getContainer()));
    }

    @Test
    void updateUserProjectilesUpdatesUserProjectileDisplay() {
        levelViewLevelThree.updateUserProjectiles(3);
        assertEquals(8, levelViewLevelThree.getUserProjectileDisplay().getNumberOfProjectilesToDisplay());
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        assertEquals(root, levelViewLevelThree.getRoot());
        assertEquals(8, levelViewLevelThree.getUserProjectileDisplay().getNumberOfProjectilesToDisplay());
        assertEquals("Time: 20", levelViewLevelThree.getTimerLabel().getText());
    }
}
