package com.example.demo.level;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {
	private final Group root;
	private ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage();
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
