package com.example.demo.level;

import com.example.demo.actor.plane.Boss;
import com.example.demo.ui.BossHealthDisplay;
import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealthDisplay bossHealthDisplay;

	public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth, int totalEnemiesPerTime) {
		super(root, heartsToDisplay, totalEnemiesPerTime);
		this.root = root;
		this.shieldImage = new ShieldImage();
		this.bossHealthDisplay = new BossHealthDisplay(bossHealth);
	}

	public void showBossHealth() {
		root.getChildren().add(bossHealthDisplay);
	}

	public void hideBossHealth() {
		bossHealthDisplay.hide();
	}

	public void updateBossHealthPosition(Boss boss) {
		double xPos = boss.getLayoutX() + boss.getTranslateX() + 50;
		double yPos = boss.getLayoutY() + boss.getTranslateY() + boss.getFitHeight() / 4;
		bossHealthDisplay.setLayout(xPos, yPos);
	}

	public void updateBossHealth(int bossHealth) {
		bossHealthDisplay.updateBossHealth(bossHealth);
	}

	public void displayShield() {
		root.getChildren().add(shieldImage);
	}
	
	public void showShield() {
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateShieldPosition(Boss boss) {
		double bossPositionX = boss.getLayoutX() + boss.getTranslateX() - ShieldImage.SHIELD_SIZE / 3;
		double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + ShieldImage.SHIELD_SIZE / 4;

		shieldImage.setLayoutX(bossPositionX);
		shieldImage.setLayoutY(bossPositionY);
	}

	protected BossHealthDisplay getBossHealthDisplay() {
		return bossHealthDisplay;
	}

	protected ShieldImage getShieldImage() {
		return shieldImage;
	}
}
