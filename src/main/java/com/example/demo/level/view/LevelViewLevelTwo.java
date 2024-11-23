package com.example.demo.level.view;

import com.example.demo.actor.plane.Boss;
import com.example.demo.level.view.LevelView;
import com.example.demo.ui.inGameElement.BossHealthDisplay;
import com.example.demo.ui.inGameElement.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the view for Level Two.
 * This class extends LevelView and manages the specific behavior and elements of Level Two.
 */
public class LevelViewLevelTwo extends LevelView {
	/**
	 * The root group for the level view.
	 */
    private final Group root;
	/**
	 * The shield image for the level.
	 */
    private final ShieldImage shieldImage;
	/**
	 * The boss health display for the level.
	 */
    private final BossHealthDisplay bossHealthDisplay;

    /**
     * Constructs a LevelViewLevelTwo object with the specified parameters.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param bossHealth the initial health of the boss
     * @param totalEnemiesPerTime the total number of enemies per time
     */
    public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth, int totalEnemiesPerTime) {
        super(root, heartsToDisplay, totalEnemiesPerTime);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthDisplay = new BossHealthDisplay(bossHealth);
    }

    /**
     * Displays the boss health on the root group.
     */
    public void showBossHealth() {
        root.getChildren().add(bossHealthDisplay);
    }

    /**
     * Hides the boss health display.
     */
    public void hideBossHealth() {
        bossHealthDisplay.hide();
    }

    /**
     * Updates the position of the boss health display based on the boss's current position.
     *
     * @param boss the boss whose health position is being updated
     */
    public void updateBossHealthPosition(Boss boss) {
        double xPos = boss.getLayoutX() + boss.getTranslateX() + 50;
        double yPos = boss.getLayoutY() + boss.getTranslateY() + boss.getFitHeight() / 4;
        bossHealthDisplay.setLayout(xPos, yPos);
    }

    /**
     * Updates the boss health display with the current boss health.
     *
     * @param bossHealth the current health of the boss
     */
    public void updateBossHealth(int bossHealth) {
        bossHealthDisplay.updateBossHealth(bossHealth);
    }

    /**
     * add the shield image on the root group.
     */
    public void displayShield() {
        root.getChildren().add(shieldImage);
    }

    /**
     * Shows the shield image.
     */
    public void showShield() {
        shieldImage.showShield();
    }

    /**
     * Hides the shield image.
     */
    public void hideShield() {
        shieldImage.hideShield();
    }

    /**
     * Updates the position of the shield image based on the boss's current position.
     *
     * @param boss the boss whose shield position is being updated
     */
    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 3;
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 4;
        shieldImage.setLayoutX(bossPositionX);
        shieldImage.setLayoutY(bossPositionY);
    }

    /**
     * Retrieves the boss health display.
     *
     * @return the boss health display
     */
    protected BossHealthDisplay getBossHealthDisplay() {
        return bossHealthDisplay;
    }

    /**
     * Retrieves the shield image.
     *
     * @return the shield image
     */
    protected ShieldImage getShieldImage() {
        return shieldImage;
    }
}