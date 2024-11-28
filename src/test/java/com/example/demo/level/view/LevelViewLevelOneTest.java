package com.example.demo.level.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevelViewLevelOneTest {
    private LevelViewLevelOne levelView;
    private Group root;;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            root = new Group();
            levelView = new LevelViewLevelOne(root, 3, 10, 5);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void showNumberOfKillsAddsLabelToRoot() {
        levelView.showNumberOfKills();
        assertTrue(root.getChildren().contains(levelView.getNumberOfKillsLabel()));
    }

    @Test
    void updateKillsUpdatesNumberOfKillsLabel() {
        levelView.updateKills(5);
        assertEquals(5, levelView.getNumberOfKillsLabel().getNumberOfKills());
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        assertEquals(root, levelView.getRoot());
        assertEquals(10, levelView.getNumberOfKillsLabel().getKillsToAdvance());
    }
}
