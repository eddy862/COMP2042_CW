package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.ui.UserProjectileDisplay;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class LevelViewLevelThree extends LevelView {
    private final Group root;
    private final UserProjectileDisplay userProjectileDisplay;
    private final Label timerLabel;
    private static final double TIMER_X_POSITION = Main.SCREEN_WIDTH - 350;
    private static final double TIMER_Y_POSITION = 25;
    public LevelViewLevelThree(Group root, int heartsToDisplay, int totalEnemiesPerTime, int initialUserProjectile, int survivalTimeInSec) {
        super(root, heartsToDisplay, totalEnemiesPerTime);
        this.root = root;
        this.userProjectileDisplay = new UserProjectileDisplay(initialUserProjectile);
        this.timerLabel = new Label("Time:" + survivalTimeInSec);
    }

    public void showTimer() {
        timerLabel.setLayoutX(TIMER_X_POSITION);
        timerLabel.setLayoutY(TIMER_Y_POSITION);
        timerLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
        root.getChildren().add(timerLabel);
    }

    public void updateTimer(int remainingTimeInSec) {
        timerLabel.setText("Time: " + remainingTimeInSec);
    }

    public void showUserProjectiles() {
        root.getChildren().add(userProjectileDisplay.getContainer());
    }

    public void updateUserProjectiles(int userProjectileRemaining) {
        userProjectileDisplay.updateProjectiles(userProjectileRemaining);
    }
}
