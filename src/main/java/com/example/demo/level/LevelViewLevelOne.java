package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.ui.NumberOfKillsLabel;
import javafx.scene.Group;

public class LevelViewLevelOne extends LevelView{
    private static final int NUMBER_OF_KILLS_X_POSITION = Main.SCREEN_WIDTH - 250;
    private static final int NUMBER_OF_KILLS_Y_POSITION = 25;
    private final Group root;
    private final NumberOfKillsLabel numberOfKillsLabel;

    public LevelViewLevelOne(Group root, int heartsToDisplay, int killsToAdvance) {
        super(root, heartsToDisplay);
        this.root = root;
        this.numberOfKillsLabel = new NumberOfKillsLabel(NUMBER_OF_KILLS_X_POSITION, NUMBER_OF_KILLS_Y_POSITION, killsToAdvance);
    }

    public void showNumberOfKills() {
        root.getChildren().add(numberOfKillsLabel.getLabel());
    }

    public void updateKills(int numberOfKills) {
        numberOfKillsLabel.updateKills(numberOfKills);
    }
}
