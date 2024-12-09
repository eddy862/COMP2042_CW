package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.level.view.LevelView;
import com.example.demo.level.view.LevelViewLevelThree;
import com.example.demo.ui.inGameElement.LevelCompletionMenu;
import javafx.scene.Scene;

/**
 * Represents the third level of the game.
 * This class extends LevelParent and manages the specific behavior and elements of Level Three.
 */
public class LevelThree extends LevelParent {
    /**
     * The name of the background image for Level Three.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    /**
     * The name of the next level class.
     */
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelFour";
    /**
     * The total number of enemies for Level Three at a time.
     */
    private static final int TOTAL_ENEMIES = 3;
    /**
     * The maximum number of user projectiles for Level Three, it is also the initial number of user projectiles.
     */
    private static final int USER_PROJECTILE_LIMIT = 8;
    /**
     * The number of user projectiles remaining.
     */
    private int userProjectileRemaining = USER_PROJECTILE_LIMIT;
    /**
     * The target survival time in seconds for Level Three.
     */
    private static final int SURVIVAL_SEC = 20;
    /**
     * The probability of spawning an enemy unit for Level Three.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    /**
     * The initial health of the player for Level Three.
     */
    private static final int PLAYER_INITIAL_HEALTH = 6;
    /**
     * The view for Level Three.
     */
    private LevelViewLevelThree levelView;
    /**
     * The survival time in milliseconds.
     */
    private int survivalTimeMillis = 0;
    /**
     * The interval in milliseconds to gain a user projectile.
     */
    private static final int GAIN_PROJECTILE_INTERVAL_MILLIS = 2000;

    /**
     * Constructs a LevelThree object with the specified screen dimensions, music, and sound effects.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param music the music manager
     * @param soundEffect the sound effect manager
     */
    public LevelThree(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
    }

    /**
     * Updates the scene, including the survival time and user projectiles.
     */
    @Override
    protected void updateScene() {
        super.updateScene();
        // update survival time
        double delay = getTimeline().getCycleDuration().toMillis();
        survivalTimeMillis += delay;
        levelView.updateTimer(SURVIVAL_SEC - survivalTimeMillis / 1000);
        if (survivalTimeMillis >= SURVIVAL_SEC * 1000) {
            levelCompleted();
        }

        // increase user projectile
        if (survivalTimeMillis % GAIN_PROJECTILE_INTERVAL_MILLIS == 0) { // every 2 seconds
            increaseUserProjectileRemaining();
        }
    }

    /**
     * Checks if the game is over by evaluating the player's state.
     * If the player is destroyed, the game is lost.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

    /**
     * Spawns enemy units based on the current game state.
     * Enemies are spawned until the total number of enemies is reached.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * Instantiates the level view for Level Three.
     *
     * @return the instantiated level view
     */
    @Override
    protected LevelView instantiateLevelView() {
        this.levelView = new LevelViewLevelThree(getLowerRoot(), PLAYER_INITIAL_HEALTH, TOTAL_ENEMIES, USER_PROJECTILE_LIMIT, SURVIVAL_SEC);
        return levelView;
    }

    /**
     * Initializes the scene for Level Three.
     *
     * @return the initialized scene
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.showUserProjectiles();
        levelView.showTimer();
        return scene;
    }

    /**
     * Updates the level view based on the current game state.
     * This includes updating the number of user projectiles.
     */
    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        levelView.updateUserProjectiles(userProjectileRemaining);
    }

    /**
     * Shows the level completed menu.
     *
     * @return the level completed menu
     */
    @Override
    protected LevelCompletionMenu showLevelCompletedMenu() {
        return new LevelCompletionMenu("Level Three", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelThree"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
    }

    /**
     * Decreases the number of user projectiles remaining.
     */
    private void decreaseUserProjectileRemaining() {
        if (userProjectileRemaining > 0) {
            userProjectileRemaining--;
        }
    }

    /**
     * Increases the number of user projectiles remaining.
     */
    private void increaseUserProjectileRemaining() {
        if (userProjectileRemaining < USER_PROJECTILE_LIMIT) {
            userProjectileRemaining++;
        }
    }

    /**
     * Checks if the user can fire a projectile.
     *
     * @return true if the user can fire, false otherwise
     */
    private boolean canUserFire() {
        return userProjectileRemaining > 0;
    }

    /**
     * Fires a projectile if the user can fire.
     */
    @Override
    protected void fireProjectile() {
        if (canUserFire()) {
            super.fireProjectile();
            decreaseUserProjectileRemaining();
        }
    }

    /**
     * sets the survival time in milliseconds for testing purposes.
     * @param survivalTimeMillis
     */
    public void setSurvivalTimeMillis(int survivalTimeMillis) {
        this.survivalTimeMillis = survivalTimeMillis;
    }
}