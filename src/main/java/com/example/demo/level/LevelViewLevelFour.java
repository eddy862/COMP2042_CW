package com.example.demo.level;

import com.example.demo.actor.plane.Boss;
import com.example.demo.actor.plane.MultiStageBoss;
import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelFour extends LevelViewLevelTwo {
    private final int bossStageTwoHealth;
    private boolean isHealthReset;

    public LevelViewLevelFour(Group root, int heartsToDisplay, int bossStageOneHealth, int totalEnemiesPerTime, int bossStageTwoHealth) {
        super(root, heartsToDisplay, bossStageOneHealth, totalEnemiesPerTime);
        this.bossStageTwoHealth = bossStageTwoHealth;
        this.isHealthReset = false;
    }

    public void showStageTwoHealth() {
        if (!isHealthReset) {
            getBossHealthDisplay().resetHealth(bossStageTwoHealth);
            isHealthReset = true;
        }
    }

    @Override
    public void updateBossHealthPosition(Boss boss) {
        MultiStageBoss multiStageBoss = (MultiStageBoss) boss;
        if (multiStageBoss.getStage() == 1) {
            super.updateBossHealthPosition(boss);
        } else {
            double xPos = boss.getLayoutX() + boss.getTranslateX() + 50;
            double yPos = boss.getLayoutY() + boss.getTranslateY();
            getBossHealthDisplay().setLayout(xPos, yPos);
        }
    }

    @Override
    public void updateShieldPosition(Boss boss) {
        MultiStageBoss multiStageBoss = (MultiStageBoss) boss;
        if (multiStageBoss.getStage() == 1) {
            super.updateShieldPosition(boss);
        } else {
            double bossPositionX = boss.getLayoutX() + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 2;
            double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 4;
            getShieldImage().setLayoutX(bossPositionX);
            getShieldImage().setLayoutY(bossPositionY);
        }
    }
}
