package com.example.demo.level.view;

import com.example.demo.actor.plane.Boss;
import com.example.demo.actor.plane.MultiStageBoss;
import com.example.demo.ui.inGameElement.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the view for Level Four.
 * This class extends LevelViewLevelTwo and manages the specific behavior and elements of Level Four.
 */
public class LevelViewLevelFour extends LevelViewLevelTwo {
    /**
     * The health of the boss in stage two for Level Four.
     */
    private final int bossStageTwoHealth;
    /**
     * The boolean value that indicates if the health of boss has been reset for stage two.
     */
    private boolean isHealthReset;

    /**
     * Constructs a LevelViewLevelFour object with the specified parameters.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param bossStageOneHealth the health of the boss in stage one
     * @param totalEnemiesPerTime the total number of enemies per time
     * @param bossStageTwoHealth the health of the boss in stage two
     */
    public LevelViewLevelFour(Group root, int heartsToDisplay, int bossStageOneHealth, int totalEnemiesPerTime, int bossStageTwoHealth) {
        super(root, heartsToDisplay, bossStageOneHealth, totalEnemiesPerTime);
        this.bossStageTwoHealth = bossStageTwoHealth;
        this.isHealthReset = false;
    }

    /**
     * Shows the health for stage two of the boss.
     * Resets the boss health display to the stage two health if it has not been reset yet.
     */
    public void showStageTwoHealth() {
        if (!isHealthReset) {
            getBossHealthDisplay().resetHealth(bossStageTwoHealth);
            isHealthReset = true;
        }
    }

    /**
     * Updates the position of the boss health display based on the boss's current position.
     *
     * @param boss the boss whose health position is being updated
     */
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

    /**
     * Updates the position of the shield image based on the boss's current position.
     *
     * @param boss the boss whose shield position is being updated
     */
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

    /**
     * Returns the boolean value that indicates if the health of the boss has been reset for stage two.
     *
     * @return the boolean value that indicates if the health of the boss has been reset for stage two
     */
    protected boolean getIsHealthReset() {
        return isHealthReset;
    }
}