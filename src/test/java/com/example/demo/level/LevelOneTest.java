package com.example.demo.level;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelOneTest {
    private LevelOne levelOne;
    private Music music;
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        music = new Music();
        soundEffect = new SoundEffect();

        Platform.runLater(() -> {
            levelOne = new LevelOne(800, 600, music, soundEffect);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initializeSceneReturnsNonNullScene() {
        Scene scene = levelOne.initializeScene();
        assertNotNull(scene);
    }

    @Test
    void checkIfGameOverWhenUserIsDestroyed() {
        levelOne.getUser().destroy();
        levelOne.checkIfGameOver();
        assertTrue(levelOne.isGameOver());
    }

    @Test
    void checkIfGameOverWhenUserHasReachedKillTarget() {
        for (int i = 0; i < 10; i++) {
            levelOne.getUser().incrementKillCount();
        }
        levelOne.checkIfGameOver();
        assertTrue(levelOne.isLevelCompleted());
    }

    @Test
    void spawnEnemyUnitsSpawnsCorrectNumberOfEnemies() {
        levelOne.spawnEnemyUnits();
        assertTrue(levelOne.getCurrentNumberOfEnemies() <= 5);
    }


    @Test
    void showLevelCompletedMenuReturnsNonNullMenu() {
        assertNotNull(levelOne.showLevelCompletedMenu());
    }
}
