package com.example.demo.level.view;

import com.example.demo.level.view.LevelView;
import com.example.demo.ui.inGameElement.NumberOfKillsLabel;
import javafx.scene.Group;

/**
 * Represents the view for Level One.
 * This class extends LevelView and manages the specific behavior and elements of Level One.
 */
public class LevelViewLevelOne extends LevelView {
    /**
     * The root group for the level view.
     */
    private final Group root;
    /**
     * The label showing the number of enemy kills for the level.
     */
    private final NumberOfKillsLabel numberOfKillsLabel;

    /**
     * Constructs a LevelViewLevelOne object with the specified parameters.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param killsToAdvance the number of kills required to advance
     * @param totalEnemiesPerTime the total number of enemies per time
     */
    public LevelViewLevelOne(Group root, int heartsToDisplay, int killsToAdvance, int totalEnemiesPerTime) {
        super(root, heartsToDisplay, totalEnemiesPerTime);
        this.root = root;
        this.numberOfKillsLabel = new NumberOfKillsLabel(killsToAdvance);
    }

    /**
     * Displays the number of kills label on the root group.
     */
    public void showNumberOfKills() {
        root.getChildren().add(numberOfKillsLabel);
    }

    /**
     * Updates the number of kills displayed.
     *
     * @param numberOfKills the current number of kills
     */
    public void updateKills(int numberOfKills) {
        numberOfKillsLabel.updateKills(numberOfKills);
    }

    /**
     * Returns label showing the number of enemy kills for the level.
     *
     * @return the label showing the number of enemy kills for the level
     */
    protected NumberOfKillsLabel getNumberOfKillsLabel() {
        return numberOfKillsLabel;
    }
}