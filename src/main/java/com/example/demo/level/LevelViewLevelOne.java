package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.ui.NumberOfKillsLabel;
import com.example.demo.ui.WarningImage;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class LevelViewLevelOne extends LevelView{
    private static final int NUMBER_OF_KILLS_X_POSITION = Main.SCREEN_WIDTH - 250;
    private static final int NUMBER_OF_KILLS_Y_POSITION = 25;
    private final Group root;
    private final NumberOfKillsLabel numberOfKillsLabel;
    private final List<WarningImage> warningImageList = new ArrayList<>();

    public LevelViewLevelOne(Group root, int heartsToDisplay, int killsToAdvance, int numberOfEnemies) {
        super(root, heartsToDisplay);
        this.root = root;
        this.numberOfKillsLabel = new NumberOfKillsLabel(NUMBER_OF_KILLS_X_POSITION, NUMBER_OF_KILLS_Y_POSITION, killsToAdvance);


        for (int i = 0; i < numberOfEnemies; i++) {
            WarningImage warningImage = new WarningImage(0);
            warningImageList.add(warningImage);
        }
    }

    public void showWarningImages() {
        for (WarningImage warningImage : warningImageList) {
            root.getChildren().add(warningImage);
        }
    }
    public void showNumberOfKills() {
        root.getChildren().add(numberOfKillsLabel);
    }

    public void updateKills(int numberOfKills) {
        numberOfKillsLabel.updateKills(numberOfKills);
    }
}
