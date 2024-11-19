package com.example.demo.level;

import com.example.demo.ui.NumberOfKillsLabel;
import javafx.scene.Group;

public class LevelViewLevelOne extends LevelView{
    private final Group root;
    private final NumberOfKillsLabel numberOfKillsLabel;

    public LevelViewLevelOne(Group root, int heartsToDisplay, int killsToAdvance, int numberOfEnemies) {
        super(root, heartsToDisplay);
        this.root = root;
        this.numberOfKillsLabel = new NumberOfKillsLabel(killsToAdvance);
    }

    public void showNumberOfKills() {
        root.getChildren().add(numberOfKillsLabel);
    }

    public void updateKills(int numberOfKills) {
        numberOfKillsLabel.updateKills(numberOfKills);
    }
}
