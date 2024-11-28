package com.example.demo.level.view;

import com.example.demo.actor.plane.MultiStageBoss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.level.LevelFour;
import com.example.demo.ui.inGameElement.ShieldImage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelViewLevelFourTest {
    private LevelViewLevelFour levelViewLevelFour;
    private Group root;
    private MultiStageBoss boss;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            root = new Group();
            boss = new MultiStageBoss(10, 20);
            levelViewLevelFour = new LevelViewLevelFour(root, 6, boss.getStageOneHealth(), 4, boss.getStageTwoHealth());
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void showStageTwoHealthResetsHealthIfNotReset() {
        for (int i = 0; i < 10; i++) {
            boss.takeDamage();
        }

        levelViewLevelFour.getBossHealthDisplay().updateBossHealth(boss.getHealth());
        levelViewLevelFour.showStageTwoHealth();
        assertEquals(20, levelViewLevelFour.getBossHealthDisplay().getCurrentHealth());
        assertTrue(levelViewLevelFour.getIsHealthReset());
    }

    @Test
    void showStageTwoHealthDoesNotResetHealthIfAlreadyReset() {
        for (int i = 0; i < 10; i++) {
            boss.takeDamage();
        }
        levelViewLevelFour.getBossHealthDisplay().updateBossHealth(boss.getHealth());
        levelViewLevelFour.showStageTwoHealth();
        levelViewLevelFour.showStageTwoHealth();
        assertEquals(20, levelViewLevelFour.getBossHealthDisplay().getCurrentHealth());
    }

    @Test
    void updateBossHealthPositionUpdatesPositionCorrectlyForStageOne() {
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelFour.updateBossHealthPosition(boss);
        assertEquals(50 + boss.getTranslateX() + boss.getFitHeight() / 3, levelViewLevelFour.getBossHealthDisplay().getLayoutX());
        assertEquals(50 + boss.getTranslateY() + boss.getFitHeight() / 5, levelViewLevelFour.getBossHealthDisplay().getLayoutY());
    }

    @Test
    void updateBossHealthPositionUpdatesPositionCorrectlyForStageTwo() {
        for (int i = 0; i < 11; i++) {
            boss.takeDamage();
        }
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelFour.updateBossHealthPosition(boss);
        assertEquals(100, levelViewLevelFour.getBossHealthDisplay().getLayoutX());
        assertEquals(50, levelViewLevelFour.getBossHealthDisplay().getLayoutY());
    }

    @Test
    void updateShieldPositionUpdatesPositionCorrectlyForStageOne() {
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelFour.updateShieldPosition(boss);
        assertEquals(50 + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 3, levelViewLevelFour.getShieldImage().getLayoutX());
        assertEquals(50 + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 6, levelViewLevelFour.getShieldImage().getLayoutY());
    }

    @Test
    void updateShieldPositionUpdatesPositionCorrectlyForStageTwo() {
        for (int i = 0; i < 11; i++) {
            boss.takeDamage();
        }
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelFour.updateShieldPosition(boss);
        assertEquals(50 + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 2, levelViewLevelFour.getShieldImage().getLayoutX());
        assertEquals(50 + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 4, levelViewLevelFour.getShieldImage().getLayoutY());
    }
}
