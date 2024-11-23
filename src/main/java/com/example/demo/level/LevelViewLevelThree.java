package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.ui.inGameElement.UserProjectileDisplay;
import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Represents the view for Level Three.
 * This class extends LevelView and manages the specific behavior and elements of Level Three.
 */
public class LevelViewLevelThree extends LevelView {
    /**
     * The root group for the level view.
     */
    private final Group root;
    /**
     * The ui showing remaining projectiles of user for the level.
     */
    private final UserProjectileDisplay userProjectileDisplay;
    /**
     * The label showing the timer for the level.
     */
    private final Label timerLabel;
    /**
     * The x position of the timer label.
     */
    private static final double TIMER_X_POSITION = Main.SCREEN_WIDTH - 350;
    /**
     * The y position of the timer label.
     */
    private static final double TIMER_Y_POSITION = 25;

    /**
     * Constructs a LevelViewLevelThree object with the specified parameters.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param totalEnemiesPerTime the total number of enemies per time
     * @param initialUserProjectile the initial number of user projectiles
     * @param survivalTimeInSec the survival time in seconds
     */
    public LevelViewLevelThree(Group root, int heartsToDisplay, int totalEnemiesPerTime, int initialUserProjectile, int survivalTimeInSec) {
        super(root, heartsToDisplay, totalEnemiesPerTime);
        this.root = root;
        this.userProjectileDisplay = new UserProjectileDisplay(initialUserProjectile);
        this.timerLabel = new Label("Time:" + survivalTimeInSec);
    }

    /**
     * Displays the timer label on the root group.
     */
    public void showTimer() {
        timerLabel.setLayoutX(TIMER_X_POSITION);
        timerLabel.setLayoutY(TIMER_Y_POSITION);
        timerLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
        root.getChildren().add(timerLabel);
    }

    /**
     * Updates the timer label with the remaining time.
     *
     * @param remainingTimeInSec the remaining time in seconds
     */
    public void updateTimer(int remainingTimeInSec) {
        timerLabel.setText("Time: " + remainingTimeInSec);
    }

    /**
     * Displays the user projectile display on the root group.
     */
    public void showUserProjectiles() {
        root.getChildren().add(userProjectileDisplay.getContainer());
    }

    /**
     * Updates the user projectile display with the remaining projectiles.
     *
     * @param userProjectileRemaining the remaining number of user projectiles
     */
    public void updateUserProjectiles(int userProjectileRemaining) {
        userProjectileDisplay.updateProjectiles(userProjectileRemaining);
    }
}