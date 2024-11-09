package com.example.demo.ui;

import javafx.scene.control.Label;

public class NumberOfKillsLabel {
    private final Label numberOfKillsLabel;
    private final int killsToAdvance;

    public NumberOfKillsLabel(double xPosition, double yPosition, int killsToAdvance) {
        this.killsToAdvance = killsToAdvance;
        numberOfKillsLabel = new Label("Kills: " + 0 + "/" + killsToAdvance);
        numberOfKillsLabel.setLayoutX(xPosition);
        numberOfKillsLabel.setLayoutY(yPosition);
        numberOfKillsLabel.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateKills(int numberOfKills) {
        numberOfKillsLabel.setText("Kills: " + numberOfKills + "/" + killsToAdvance);
    }

    public Label getLabel() {
        return numberOfKillsLabel;
    }
}
