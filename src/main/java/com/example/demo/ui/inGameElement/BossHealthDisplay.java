package com.example.demo.ui.inGameElement;

import com.example.demo.controller.Main;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

/**
 * Represents the health display for a Boss.
 * This class extends StackPane and provides methods to update and display the boss's health.
 */
public class BossHealthDisplay extends StackPane {
    /**
     * The initial health of the boss.
     */
    private int bossInitialHealth;
    /**
     * The progress bar representing the boss's health.
     */
    private final ProgressBar healthBar;
    /**
     * The text displaying the boss's health.
     */
    private final Label healthText;
    /**
     * The width of the health bar.
     */
    private static final int BAR_WIDTH = 200;
    /**
     * The height of the health bar.
     */
    private static final int BAR_HEIGHT = 25;

    /**
     * Constructs a BossHealthDisplay object with the specified initial boss health.
     *
     * @param bossHealth the initial health of the boss
     */
    public BossHealthDisplay(int bossHealth) {
        this.bossInitialHealth = bossHealth;
        this.healthBar = new ProgressBar(1.0);
        this.healthText = new Label(bossHealth + "/" + bossHealth);
        this.healthText.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        this.healthBar.setPrefWidth(BAR_WIDTH);
        this.healthBar.setPrefHeight(BAR_HEIGHT);
        this.healthBar.setStyle("-fx-accent: darkred; -fx-control-inner-background: gray;");
        this.getChildren().addAll(healthBar, healthText);
    }

    /**
     * Updates the boss health display with the current boss health.
     *
     * @param bossHealth the current health of the boss
     */
    public void updateBossHealth(int bossHealth) {
        double progress = (double) bossHealth / bossInitialHealth;
        this.healthBar.setProgress(progress);
        this.healthText.setText(bossHealth + "/" + bossInitialHealth);
    }

    /**
     * Sets the layout position of the boss health display.
     *
     * @param x the x-coordinate of the layout position
     * @param y the y-coordinate of the layout position
     */
    public void setLayout(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /**
     * Resets the boss health display to the specified boss health, usually used when the boss enters a new stage.
     *
     * @param bossHealth the new initial health of the boss
     */
    public void resetHealth(int bossHealth) {
        this.bossInitialHealth = bossHealth;
        this.healthBar.setStyle("-fx-accent: #00008B; -fx-control-inner-background: gray;");
    }

    /**
     * Hides the boss health display.
     */
    public void hide() {
        this.setVisible(false);
    }

    /**
     * Returns the current health of the boss.
     *
     * @return the current health of the boss
     */
    public int getCurrentHealth() {
        return Integer.parseInt(this.healthText.getText().split("/")[0]);
    }
}