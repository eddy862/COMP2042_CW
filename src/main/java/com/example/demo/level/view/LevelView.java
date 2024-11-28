package com.example.demo.level.view;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.ui.inGameElement.*;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for displaying the UI elements in the level.
 */
public class LevelView {
    /**
     * The root group for the level view.
     */
    private final Group root;
    /**
     * The win image for the level.
     */
    private final WinImage winImage;
    /**
     * The game over image for the level.
     */
    private final GameOverImage gameOverImage;
    /**
     * The heart display for the level.
     */
    private final HeartDisplay heartDisplay;
    /**
     * The list of explosion images in the pool. The pool is used to recycle explosion images.
     */
    private final List<ExplosionImage> explosionPool = new ArrayList<>();
    /**
     * The map of warning images for each enemy. The map is used to track which warning image is associated with each enemy.
     */
    private final Map<ActiveActorDestructible, WarningImage> warningImageMap = new HashMap<>();
    /**
     * The list of warning images in the pool. The pool is used to recycle warning images.
     */
    private final List<WarningImage> warningImagePool = new ArrayList<>();
    /**
     * The total number of enemies per time.
     */
    private final int totalEnemiesPerTime;

    /**
     * Constructs a LevelView object with the specified root group, hearts to display, and total enemies per time.
     *
     * @param root                the root group for the level view
     * @param heartsToDisplay     the number of hearts to display
     * @param totalEnemiesPerTime the total number of enemies per time
     */
    public LevelView(Group root, int heartsToDisplay, int totalEnemiesPerTime) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(heartsToDisplay);
        this.winImage = new WinImage();
        this.gameOverImage = new GameOverImage();
        this.totalEnemiesPerTime = totalEnemiesPerTime;
    }

    /**
     * Initializes the explosion pool by creating and adding explosion images to the root group.
     */
    public void initialiseExplosionPool() {
        for (int i = 0; i < totalEnemiesPerTime; i++) {
            ExplosionImage explosion = new ExplosionImage(0, 0);
            explosionPool.add(explosion);
            root.getChildren().add(explosion);
        }
    }

    /**
     * Initializes the warning pool by creating and adding warning images to the root group.
     */
    public void initialiseWarningPool() {
        for (int i = 0; i < totalEnemiesPerTime; i++) {
            WarningImage warning = new WarningImage(0);
            warningImagePool.add(warning);
            root.getChildren().add(warning);
        }
    }

    /**
     * Displays the heart display on the root group.
     */
    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    /**
     * Displays the win image on the root group.
     */
    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    /**
     * Displays the game over image on the root group.
     */
    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
    }

    /**
     * Removes hearts from the heart display based on the remaining hearts.
     *
     * @param heartsRemaining the number of hearts remaining
     */
    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }

    /**
     * Displays an explosion at the location of the specified enemy.
     *
     * @param enemy the enemy at whose location the explosion is displayed
     */
    public void showExplosion(ActiveActorDestructible enemy) {
        ExplosionImage explosion = getAvailableExplosion();
        if (explosion != null) {
            explosion.setLayoutX(enemy.getLayoutX() + enemy.getTranslateX());
            explosion.setLayoutY(enemy.getLayoutY() + enemy.getTranslateY());
            explosion.show();

            // Hide the explosion image after a short delay
            PauseTransition delay = new PauseTransition(Duration.millis(400));
            delay.setOnFinished(event -> explosion.hide());
            delay.play();
        }
    }

    /**
     * Displays a warning at the location of the specified enemy.
     *
     * @param enemy the enemy at whose location the warning is displayed
     */
    public void showWarning(ActiveActorDestructible enemy) {
        WarningImage warning = getAvailableWarning();
        if (warning != null && !warningImageMap.containsKey(enemy)) {
            warning.setLayoutY(enemy.getLayoutY() + enemy.getFitHeight() / 3);
            warning.show();
            warningImageMap.put(enemy, warning);
        }
    }

    /**
     * Retrieves an available warning image from the pool.
     *
     * @return an available warning image, or null if none are available
     */
    private WarningImage getAvailableWarning() {
        for (WarningImage warning : warningImagePool) {
            if (!warning.isVisible()) {
                return warning;
            }
        }
        return null; // No available warning, consider increasing the pool size
    }

    /**
     * Hides the warning for the specified enemy.
     *
     * @param enemy the enemy for which the warning is hidden
     */
    public void hideWarning(ActiveActorDestructible enemy) {
        WarningImage warning = warningImageMap.get(enemy);
        if (warning != null) {
            warning.hide();
            warningImageMap.remove(enemy);
        }
    }

    /**
     * Retrieves an available explosion image from the pool.
     *
     * @return an available explosion image, or null if none are available
     */
    private ExplosionImage getAvailableExplosion() {
        for (ExplosionImage explosion : explosionPool) {
            if (!explosion.isVisible()) {
                return explosion;
            }
        }
        return null; // No available explosion, consider increasing the pool size
    }

    /**
     * Starts the zooming animation for the hearts.
     */
    public void heartsStartZooming() {
        heartDisplay.startZooming();
    }

    /**
     * Stops the zooming animation for the hearts.
     */
    public void heartsStopZooming() {
        heartDisplay.stopZooming();
    }

    /**
     * Returns the root group for the level view.
     *
     * @return the root group for the level view
     */
    protected Group getRoot() {
        return root;
    }
}