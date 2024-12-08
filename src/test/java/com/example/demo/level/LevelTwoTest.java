package com.example.demo.level;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LevelTwoTest {
    private LevelTwo levelTwo;
    private Music music;
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        music = new Music();
        soundEffect = new SoundEffect();

        Platform.runLater(() -> {
            levelTwo = new LevelTwo(800, 600, music, soundEffect);
        });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initializeSceneReturnsNonNullScene() {
        Scene scene = levelTwo.initializeScene();
        assertNotNull(scene);
    }

    @Test
    void checkIfGameOverWhenUserIsDestroyed() {
        levelTwo.getUser().destroy();
        levelTwo.checkIfGameOver();
        assertTrue(levelTwo.isGameOver());
    }

    @Test
    void checkIfGameOverWhenBossIsDestroyed() {
        levelTwo.getBoss().destroy();
        levelTwo.checkIfGameOver();
        assertTrue(levelTwo.isLevelCompleted());
    }

    @Test
    void spawnEnemyUnitsAddsBossWhenNoEnemies() {
        levelTwo.spawnEnemyUnits();
        assertEquals(1, levelTwo.getCurrentNumberOfEnemies());
        assertTrue(levelTwo.getEnemyUnits().contains(levelTwo.getBoss()));
    }

    @Test
    void showLevelCompletedMenuReturnsNonNullMenu() {
        assertNotNull(levelTwo.showLevelCompletedMenu());
    }
}
