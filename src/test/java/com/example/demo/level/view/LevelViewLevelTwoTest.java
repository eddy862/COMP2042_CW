package com.example.demo.level.view;

import com.example.demo.actor.plane.Boss;
import com.example.demo.ui.inGameElement.ShieldImage;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelViewLevelTwoTest {
    private LevelViewLevelTwo levelViewLevelTwo;
    private Group root;;
    private Boss boss;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            root = new Group();
            boss = new Boss(10);
            levelViewLevelTwo = new LevelViewLevelTwo(root, 3, 10, 5);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void showBossHealthAddsBossHealthDisplayToRoot() {
        levelViewLevelTwo.showBossHealth();
        assertTrue(root.getChildren().contains(levelViewLevelTwo.getBossHealthDisplay()));
    }

    @Test
    void hideBossHealthHidesBossHealthDisplay() {
        levelViewLevelTwo.showBossHealth();
        levelViewLevelTwo.hideBossHealth();
        assertFalse(levelViewLevelTwo.getBossHealthDisplay().isVisible());
    }

    @Test
    void updateBossHealthPositionUpdatesPositionCorrectly() {
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelTwo.updateBossHealthPosition(boss);
        assertEquals(50 + boss.getTranslateX() + boss.getFitHeight() / 3, levelViewLevelTwo.getBossHealthDisplay().getLayoutX());
        assertEquals(50 + boss.getTranslateY() + boss.getFitHeight() / 5, levelViewLevelTwo.getBossHealthDisplay().getLayoutY());
    }

    @Test
    void updateBossHealthUpdatesHealthCorrectly() {
        levelViewLevelTwo.updateBossHealth(80);
        assertEquals(80, levelViewLevelTwo.getBossHealthDisplay().getCurrentHealth());
    }

    @Test
    void displayShieldAddsShieldImageToRoot() {
        levelViewLevelTwo.displayShield();
        assertTrue(root.getChildren().contains(levelViewLevelTwo.getShieldImage()));
    }

    @Test
    void showShieldMakesShieldVisible() {
        levelViewLevelTwo.showShield();
        assertTrue(levelViewLevelTwo.getShieldImage().isVisible());
    }

    @Test
    void hideShieldMakesShieldInvisible() {
        levelViewLevelTwo.showShield();
        levelViewLevelTwo.hideShield();
        assertFalse(levelViewLevelTwo.getShieldImage().isVisible());
    }

    @Test
    void updateShieldPositionUpdatesPositionCorrectly() {
        boss.setLayoutX(50);
        boss.setLayoutY(50);
        levelViewLevelTwo.updateShieldPosition(boss);
        assertEquals(50 + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 3, levelViewLevelTwo.getShieldImage().getLayoutX());
        assertEquals(50 + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 6, levelViewLevelTwo.getShieldImage().getLayoutY());
    }
}
