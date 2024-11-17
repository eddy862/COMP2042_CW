package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.scene.control.Label;

public class BossHealthLabel extends Label {
    private static final int X_POSITION = Main.SCREEN_WIDTH - 450;
    private static final int Y_POSITION = 25;

    public BossHealthLabel(int bossHealth) {
        this.setText("Boss Health: " + bossHealth);
        this.setLayoutX(X_POSITION);
        this.setLayoutY(Y_POSITION);
        this.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateBossHealth(int bossHealth) {
        this.setText("Boss Health: " + bossHealth);
    }
}
