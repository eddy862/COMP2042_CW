package com.example.demo.ui;

import javafx.scene.control.Label;

public class BossHealthLabel extends Label {

    public BossHealthLabel(double xPosition, double yPosition, int bossHealth) {
        this.setText("Boss Health: " + bossHealth);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateBossHealth(int bossHealth) {
        this.setText("Boss Health: " + bossHealth);
    }
}
