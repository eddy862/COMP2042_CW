package com.example.demo.level;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelFourTest {
    private LevelFour levelFour;
    private Music music;
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        music = new Music();
        soundEffect = new SoundEffect();

        Platform.runLater(() -> {
            levelFour = new LevelFour(800, 600, music, soundEffect);
        });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initializeSceneReturnsNonNullScene() {
        Scene scene = levelFour.initializeScene();
        assertNotNull(scene);
    }

    @Test
    void checkIfGameOverWhenUserIsDestroyed() {
        levelFour.getUser().destroy();
        levelFour.checkIfGameOver();
        assertTrue(levelFour.isGameOver());
    }

    @Test
    void checkIfGameOverWhenBossIsDestroyed() {
        levelFour.getBoss().destroy();
        levelFour.checkIfGameOver();
        assertTrue(levelFour.isLevelCompleted());
    }

    @Test
    void spawnEnemyUnitsAddsBossWhenNoEnemies() {
        levelFour.spawnEnemyUnits();
        assertEquals(1, levelFour.getCurrentNumberOfEnemies());
        assertTrue(levelFour.getEnemyUnits().contains(levelFour.getBoss()));
    }

    @Test
    void spawnEnemyUnitsSpawnsCorrectNumberOfEnemiesInStageTwo() {
        levelFour.getBoss().advanceStage();
        levelFour.spawnEnemyUnits();
        assertTrue(levelFour.getCurrentNumberOfEnemies() <= 4); // Boss + up to 3 enemies
    }
}
