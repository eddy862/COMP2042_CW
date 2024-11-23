package com.example.demo.level;

import com.example.demo.actor.plane.Boss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.inGameElement.LevelCompletedMenu;
import javafx.scene.Scene;

/**
 * Represents the second level of the game.
 * This class extends LevelParent and manages the specific behavior and elements of Level Two.
 */
public class LevelTwo extends LevelParent {

    /**
     * The name of the background image for Level Two.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    /**
     * The name of the next level class.
     */
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
    /**
     * The initial health of the player for Level Two.
     */
    private static final int PLAYER_INITIAL_HEALTH = 6;
    /**
     * The boss for Level Two.
     */
    private final Boss boss;
    /**
     * The health of the boss for Level Two.
     */
    private static final int BOSS_HEALTH = 15;
    /**
     * The view for Level Two.
     */
    private LevelViewLevelTwo levelView;
    /**
     * The sound effect manager passed from the main class.
     */
    private final SoundEffect soundEffect;
    /**
     * The boolean value that indicates if the boss is shielded.
     */
    private boolean isBossShielded;

    /**
     * Constructs a LevelTwo object with the specified screen dimensions, music, and sound effects.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param music the music manager
     * @param soundEffect the sound effect manager
     */
    public LevelTwo(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
        this.soundEffect = soundEffect;
        boss = new Boss(BOSS_HEALTH);
        this.isBossShielded = boss.isShielded();
    }

    /**
     * Checks if the game is over by evaluating the player's and boss's states.
     * If the player is destroyed, the game is lost. If the boss is destroyed, the game is won.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
            levelView.hideBossHealth();
        } else if (boss.isDestroyed()) {
            levelCompleted();
            levelView.hideBossHealth();
        }
    }

    /**
     * Spawns enemy units based on the current game state.
     * If there are no enemies, the boss is added as an enemy unit.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }
    }

    /**
     * Instantiates the level view for Level Two.
     *
     * @return the instantiated level view
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH, 1);
        return levelView;
    }

    /**
     * Initializes the scene for Level Two.
     *
     * @return the initialized scene
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.displayShield();
        levelView.showBossHealth();
        return scene;
    }

    /**
     * Updates the level view based on the current game state.
     * This includes updating the shield and boss health display.
     */
    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        // update shield position
        levelView.updateShieldPosition(boss);
        boolean bossWasShielded = isBossShielded;

        if (boss.isShielded()) {
            levelView.showShield();
            if (!bossWasShielded) {
                soundEffect.playActivateShield();
            }
        } else {
            levelView.hideShield();
            if (bossWasShielded) {
                soundEffect.playShieldDeactivate();
            }
        }

        isBossShielded = boss.isShielded();

        // update boss health
        levelView.updateBossHealth(boss.getHealth());
        levelView.updateBossHealthPosition(boss);
    }

    /**
     * Shows the level completed menu.
     *
     * @return the level completed menu
     */
    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return new LevelCompletedMenu("Level Two", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelTwo"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
    }
}