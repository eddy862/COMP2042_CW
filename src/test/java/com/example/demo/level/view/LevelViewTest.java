package com.example.demo.level.view;

import com.example.demo.actor.plane.EnemyPlane;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelViewTest {
    private LevelView levelView;
    private Group root;

    @BeforeEach
    void setUp() {
        new JFXPanel();
        Platform.runLater(() -> {
            root = new Group();
            levelView = new LevelView(root, 3, 10);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void initialiseExplosionPoolAddsExplosionsToRoot() {
        levelView.initialiseExplosionPool();
        assertEquals(10, root.getChildren().size());
    }

    @Test
    void initialiseWarningPoolAddsWarningsToRoot() {
        levelView.initialiseWarningPool();
        assertEquals(10, root.getChildren().size());
    }

    @Test
    void showHeartDisplayAddsHeartDisplayToRoot() {
        levelView.showHeartDisplay();
        assertTrue(root.getChildren().contains(levelView.getHeartDisplay().getContainer()));
    }

    @Test
    void showWinImageAddsWinImageToRoot() {
        levelView.showWinImage();
        assertTrue(root.getChildren().contains(levelView.getWinImage()));
    }

    @Test
    void showGameOverImageAddsGameOverImageToRoot() {
        levelView.showGameOverImage();
        assertTrue(root.getChildren().contains(levelView.getGameOverImage()));
    }

    @Test
    void removeHeartsRemovesCorrectNumberOfHearts() {
        levelView.showHeartDisplay();
        levelView.removeHearts(1);
        assertEquals(1, levelView.getHeartDisplay().getContainer().getChildren().size());
    }

    @Test
    void showExplosionDisplaysExplosionAtEnemyLocation() {
        EnemyPlane enemy = new EnemyPlane(100, 100);
        enemy.setLayoutX(50);
        enemy.setLayoutY(50);
        levelView.initialiseExplosionPool();
        levelView.showExplosion(enemy);
        assertTrue(levelView.getExplosionPool().get(0).isVisible());
    }

    @Test
    void showWarningDisplaysWarningAtEnemyLocation() {
        EnemyPlane enemy = new EnemyPlane(100, 100);
        enemy.setLayoutY(50);
        levelView.initialiseWarningPool();
        levelView.showWarning(enemy);
        assertTrue(levelView.getWarningImageMap().containsKey(enemy));
    }

    @Test
    void hideWarningHidesWarningForEnemy() {
        EnemyPlane enemy = new EnemyPlane(100, 100);
        levelView.initialiseWarningPool();
        levelView.showWarning(enemy);
        levelView.hideWarning(enemy);
        assertFalse(levelView.getWarningImageMap().containsKey(enemy));
    }

    @Test
    void heartsStartZoomingStartsZoomingAnimation() {
        levelView.showHeartDisplay();
        levelView.heartsStartZooming();
        assertTrue(levelView.getHeartDisplay().isZooming());
    }

    @Test
    void heartsStopZoomingStopsZoomingAnimation() {
        levelView.showHeartDisplay();
        levelView.heartsStartZooming();
        levelView.heartsStopZooming();
        assertFalse(levelView.getHeartDisplay().isZooming());
    }
}
