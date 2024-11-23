package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.inGameElement.LevelCompletedMenu;
import javafx.scene.Scene;

/**
 * Represents the first level of the game.
 * This class extends LevelParent and manages the specific behavior and elements of Level One.
 */
public class LevelOne extends LevelParent {

	/**
	 * The name of the background image for Level One.
	 */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	/**
	 * The name of the next level class.
	 */
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelTwo";
	/**
	 * The total number of enemies for Level One at a time
	 */
    private static final int TOTAL_ENEMIES = 5;
	/**
	 * The number of kills required to advance to the next level
	 */
    private static final int KILLS_TO_ADVANCE = 10;
	/**
	 * The probability of spawning an enemy unit
	 */
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
	/**
	 * The initial health of the player for Level One
	 */
    private static final int PLAYER_INITIAL_HEALTH = 6;
	/**
	 * The view for Level One
	 */
    private LevelViewLevelOne levelView;

    /**
     * Constructs a LevelOne object with the specified screen dimensions, music, and sound effects.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param music the music manager
     * @param soundEffect the sound effect manager
     */
    public LevelOne(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
    }

    /**
     * Checks if the game is over by evaluating the player's state.
     * If the player is destroyed, the game is lost. If the player has reached the kill target, the level is completed.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            levelCompleted();
        }
    }

    /**
     * Spawns enemy units based on the current game state.
     * Enemies are spawned until the total number of enemies is reached. Only if an existing enemy is destroyed, then a new enemy is spawned.
     */
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

    /**
     * Instantiates the level view for Level One.
     *
     * @return the instantiated level view
     */
    @Override
    protected LevelView instantiateLevelView() {
        this.levelView = new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE, TOTAL_ENEMIES);
        return levelView;
    }

    /**
     * Initializes the scene for Level One.
     *
     * @return the initialized scene
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.showNumberOfKills();
        return scene;
    }

    /**
     * Updates the level view based on the current game state.
     * This includes updating the number of kills.
     */
    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        levelView.updateKills(getUser().getNumberOfKills());
    }

    /**
     * Shows the level completed menu.
     *
     * @return the level completed menu
     */
    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return new LevelCompletedMenu("Level One", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelOne"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
    }

    /**
     * Checks if the user has reached the kill target.
     *
     * @return true if the user has reached the kill target, false otherwise
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}