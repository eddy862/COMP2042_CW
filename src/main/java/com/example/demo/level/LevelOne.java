package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LevelCompletedMenu;
import javafx.scene.Scene;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 5;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 6;
	private LevelViewLevelOne levelView;

	public LevelOne(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
			levelCompleted();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) { // spawn enemies until the total number of enemies is reached
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		this.levelView = new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE, TOTAL_ENEMIES);
		return levelView;
	}

	@Override
	public Scene initializeScene() {
		Scene scene = super.initializeScene();
		levelView.showNumberOfKills();
		return scene;
	}

	@Override
	protected void updateLevelView() {
		super.updateLevelView();
		levelView.updateKills(getUser().getNumberOfKills());
	}

	@Override
	protected LevelCompletedMenu showLevelCompletedMenu() {
        return new LevelCompletedMenu("Level One", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelOne"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
