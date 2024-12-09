package com.example.demo.ui.inGameElement;

import com.example.demo.controller.Main;
import javafx.scene.control.Label;

/**
 * Represents a label that displays the number of kills in the game.
 * This class extends Label and provides methods to update the displayed number of kills.
 */
public class NumberOfKillsLabel extends Label {
    /**
     * The x position of the number of kills label.
     */
    private static final int X_POSITION = Main.SCREEN_WIDTH - 350;
    /**
     * The y position of the number of kills label.
     */
    private static final int Y_POSITION = 25;
    /**
     * The number of kills required to advance.
     */
    private final int killsToAdvance;

    /**
     * Constructs a NumberOfKillsLabel object with the specified number of kills required to advance.
     *
     * @param killsToAdvance the number of kills required to advance
     */
    public NumberOfKillsLabel(int killsToAdvance) {
        this.killsToAdvance = killsToAdvance;
        this.setText("Kills: " + 0 + "/" + killsToAdvance);
        this.setLayoutX(X_POSITION);
        this.setLayoutY(Y_POSITION);
        this.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
    }

    /**
     * Updates the displayed number of kills.
     *
     * @param numberOfKills the current number of kills
     */
    public void updateKills(int numberOfKills) {
        this.setText("Kills: " + numberOfKills + "/" + killsToAdvance);
    }

    /**
     * Returns the number of kills displayed.
     *
     * @return the number of kills displayed
     */
    public int getNumberOfKills() {
        return Integer.parseInt(this.getText().split(" ")[1].split("/")[0]);
    }

    /**
     * Returns the number of kills required to advance.
     *
     * @return the number of kills required to advance
     */
    public int getKillsToAdvance() {
        return killsToAdvance;
    }
}