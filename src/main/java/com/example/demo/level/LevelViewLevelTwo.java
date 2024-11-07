package com.example.demo.level;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

//	private static final int SHIELD_X_POSITION = 1150;
//	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
//		this.shieldImage = new ShieldImage();
//		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
//		root.getChildren().add(shieldImage);
	}
	
	public void showShield() {
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateShieldPosition(double bossPositionX, double bossPositionY) {
		// prevent render behind the background and also prevent multiple shield images
		if (!root.getChildren().contains(shieldImage)){
			this.shieldImage = new ShieldImage();
			System.out.println("New shield image created");
			root.getChildren().add(shieldImage);
		}

		shieldImage.setLayoutX(bossPositionX);
		shieldImage.setLayoutY(bossPositionY);
	}
}
