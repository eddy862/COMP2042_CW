package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.scene.control.Label;

public class NumberOfKillsLabel extends Label {
    private static final int X_POSITION = Main.SCREEN_WIDTH - 350;
    private static final int Y_POSITION = 25;
    private final int killsToAdvance;

    public NumberOfKillsLabel(int killsToAdvance) {
        this.killsToAdvance = killsToAdvance;
        this.setText("Kills: " + 0 + "/" + killsToAdvance);
        this.setLayoutX(X_POSITION);
        this.setLayoutY(Y_POSITION);
        this.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    public void updateKills(int numberOfKills) {
        this.setText("Kills: " + numberOfKills + "/" + killsToAdvance);
    }
}
