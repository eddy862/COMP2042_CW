package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.ui.*;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Responsible for displaying the UI elements in the level.
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 10;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final List<ExplosionImage> explosionPool = new ArrayList<>();
	private final Map<ActiveActorDestructible, WarningImage> warningImageMap = new HashMap<>();
	private final List<WarningImage> warningImagePool = new ArrayList<>();
	private final int totalEnemiesPerTime;
	
	public LevelView(Group root, int heartsToDisplay, int totalEnemiesPerTime) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.totalEnemiesPerTime = totalEnemiesPerTime;
	}

	public void initialiseExplosionPool() {
		for (int i = 0; i < totalEnemiesPerTime; i++) {
			ExplosionImage explosion = new ExplosionImage(0, 0);
			explosionPool.add(explosion);
			root.getChildren().add(explosion);
		}
	}

	public void initialiseWarningPool() {
		for (int i = 0; i < totalEnemiesPerTime; i++) {
			WarningImage warning = new WarningImage(0);
			warningImagePool.add(warning);
			root.getChildren().add(warning);
		}
	}

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void showExplosion(ActiveActorDestructible enemy) {
		ExplosionImage explosion = getAvailableExplosion();
		if (explosion != null) {
			explosion.setLayoutX(enemy.getLayoutX() + enemy.getTranslateX());
			explosion.setLayoutY(enemy.getLayoutY() + enemy.getTranslateY());
			explosion.show();

			// Hide the explosion image after a short delay
			PauseTransition delay = new PauseTransition(Duration.millis(400));
			delay.setOnFinished(event -> explosion.hide());
			delay.play();
		}
	}

	public void showWarning(ActiveActorDestructible enemy) {
		WarningImage warning = getAvailableWarning();
		if (warning != null && !warningImageMap.containsKey(enemy)) {
			warning.setLayoutY(enemy.getLayoutY() + enemy.getFitHeight() / 3);
			warning.show();
			warningImageMap.put(enemy, warning);
		}
	}

	private WarningImage getAvailableWarning() {
		for (WarningImage warning : warningImagePool) {
			if (!warning.isVisible()) {
				return warning;
			}
		}
		return null; // No available warning, consider increasing the pool size
	}

	public void hideWarning(ActiveActorDestructible enemy) {
		WarningImage warning = warningImageMap.get(enemy);
		if (warning != null) {
			warning.hide();
			warningImageMap.remove(enemy);
		}
	}

	private ExplosionImage getAvailableExplosion() {
		for (ExplosionImage explosion : explosionPool) {
			if (!explosion.isVisible()) {
				return explosion;
			}
		}
		return null; // No available explosion, consider increasing the pool size
	}

	public void heartsStartZooming() {
		heartDisplay.startZooming();
	}

	public void heartsStopZooming() {
		heartDisplay.stopZooming();
	}
}
