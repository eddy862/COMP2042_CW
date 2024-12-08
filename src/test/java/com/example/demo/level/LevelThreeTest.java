package com.example.demo.level;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelThreeTest {
    private LevelThree levelThree;
    private Music music;
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        music = new Music();
        soundEffect = new SoundEffect();

        Platform.runLater(() -> {
            levelThree = new LevelThree(800, 600, music, soundEffect);
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initializeSceneReturnsNonNullScene() {
        Scene scene = levelThree.initializeScene();
        assertNotNull(scene);
    }

    @Test
    void checkIfGameOverWhenUserIsDestroyed() {
        levelThree.getUser().destroy();
        levelThree.checkIfGameOver();
        assertTrue(levelThree.isGameOver());
    }

    @Test
    void checkIfGameOverWhenSurvivalTimeReached() {
        levelThree.setSurvivalTimeMillis(20000); // Simulate 20 seconds
        levelThree.updateScene();
        assertTrue(levelThree.isLevelCompleted());
    }

    @Test
    void spawnEnemyUnitsSpawnsCorrectNumberOfEnemies() {
        levelThree.spawnEnemyUnits();
        assertTrue(levelThree.getCurrentNumberOfEnemies() <= 3);
    }

    @Test
    void showLevelCompletedMenuReturnsNonNullMenu() {
        assertNotNull(levelThree.showLevelCompletedMenu());
    }
}
