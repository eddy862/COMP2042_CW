package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class BossHealthDisplay extends StackPane {
    private final int bossInitialHealth;
    private final ProgressBar healthBar;
    private final Label healthText;
    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 25;

    public BossHealthDisplay(int bossHealth) {
        this.bossInitialHealth = bossHealth;
        this.healthBar = new ProgressBar(1.0);
        this.healthText = new Label(bossHealth + "/" + bossHealth);
        this.healthText.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        this.healthBar.setPrefWidth(BAR_WIDTH);
        this.healthBar.setPrefHeight(BAR_HEIGHT);
        this.healthBar.setStyle("-fx-accent: darkred; -fx-control-inner-background: grayaaaaa;");
        this.getChildren().addAll(healthBar, healthText);
    }

    public void updateBossHealth(int bossHealth) {
        double progress = (double) bossHealth / bossInitialHealth;
        this.healthBar.setProgress(progress);
        this.healthText.setText(bossHealth + "/" + bossInitialHealth);
    }

    public void setLayout(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
