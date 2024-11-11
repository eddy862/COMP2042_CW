package com.example.demo.ui;

import javafx.scene.control.Label;

public class NumberOfKillsLabel extends Label {
    private final int killsToAdvance;

    public NumberOfKillsLabel(double xPosition, double yPosition, int killsToAdvance) {
        this.killsToAdvance = killsToAdvance;
        this.setText("Kills: " + 0 + "/" + killsToAdvance);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateKills(int numberOfKills) {
        this.setText("Kills: " + numberOfKills + "/" + killsToAdvance);
    }
}
