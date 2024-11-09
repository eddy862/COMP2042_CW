package com.example.demo.ui;

import javafx.scene.control.Label;

public class BossHealthLabel {
    private final Label bossHealthLabel;

    public BossHealthLabel(double xPosition, double yPosition, int bossHealth) {
        bossHealthLabel = new Label("Boss Health: " + bossHealth);
        bossHealthLabel.setLayoutX(xPosition);
        bossHealthLabel.setLayoutY(yPosition);
        bossHealthLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateBossHealth(int bossHealth) {
        bossHealthLabel.setText("Boss Health: " + bossHealth);
    }

    public Label getLabel() {
        return bossHealthLabel;
    }
}
