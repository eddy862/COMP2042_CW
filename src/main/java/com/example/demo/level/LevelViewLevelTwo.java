package com.example.demo.level;

import com.example.demo.controller.Main;
import com.example.demo.ui.BossHealthLabel;
import com.example.demo.ui.ShieldImage;
import com.example.demo.ui.WarningImage;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class LevelViewLevelTwo extends LevelView {
	private static final int BOSS_HEALTH_X_POSITION = Main.SCREEN_WIDTH - 350;
	private static final int BOSS_HEALTH_Y_POSITION = 25;
	private final Group root;
	private final ShieldImage shieldImage;
	private final BossHealthLabel bossHealthLabel;

	
	public LevelViewLevelTwo(Group root, int heartsToDisplay, int bossHealth) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage();
		this.bossHealthLabel = new BossHealthLabel(BOSS_HEALTH_X_POSITION, BOSS_HEALTH_Y_POSITION, bossHealth);

	}

	public void showBossHealth() {
		root.getChildren().add(bossHealthLabel);
	}

	public void updateBossHealth(int bossHealth) {
		bossHealthLabel.updateBossHealth(bossHealth);
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

	public void updateShieldPosition(double bossPositionX, double bossPositionY) {
		shieldImage.setLayoutX(bossPositionX - ShieldImage.SHIELD_SIZE / 3);
		shieldImage.setLayoutY(bossPositionY + ShieldImage.SHIELD_SIZE / 4);
	}
}
