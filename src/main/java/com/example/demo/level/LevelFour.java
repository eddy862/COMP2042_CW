package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.MultiStageBoss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.inGameElement.LevelCompletedMenu;
import javafx.scene.Scene;

/**
 * Represents the fourth level of the game.
 * This class extends LevelParent and manages the specific behavior and elements of Level Four.
 */
public class LevelFour extends LevelParent {
    /**
     * The name of the background image for Level Four.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    /**
     * The initial health of the player for Level Four.
     */
    private static final int PLAYER_INITIAL_HEALTH = 6;
    /**
     * The view for Level Four.
     */
    private LevelViewLevelFour levelView;
    /**
     * The multi-stage boss for Level Four.
     */
    private final MultiStageBoss boss;
    /**
     * The health of the boss in stage one for Level Four.
     */
    private static final int BOSS_STAGE_ONE_HEALTH = 15;
    /**
     * The health of the boss in stage two for Level Four.
     */
    private static final int BOSS_STAGE_TWO_HEALTH = 25;
    /**
     * The probability of summoning an enemy plane for Level Four.
     */
    private static final double SUMMON_ENEMY_PROBABILITY = .15;
    /**
     * The total number of enemy planes for Level Four.
     */
    private static final int TOTAL_ENEMY_PLANES = 3;
    /**
     * The maximum y position of the enemy planes for Level Four.
     */
    private boolean isBossShielded;
    /**
     * The sound effect manager passed from the main class.
     */
    private final SoundEffect soundEffect;

    /**
     * Constructs a LevelFour object with the specified screen dimensions, music, and sound effects.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param music the music manager
     * @param soundEffect the sound effect manager
     */
    public LevelFour(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
        boss = new MultiStageBoss(BOSS_STAGE_ONE_HEALTH, BOSS_STAGE_TWO_HEALTH);
        this.isBossShielded = boss.isShielded();
        this.soundEffect = soundEffect;
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
            winGame();
            levelView.hideBossHealth();
        }
    }

    /**
     * Spawns enemy units based on the current game state.
     * Initially, the boss is spawned. If the boss is in stage two, additional enemy planes may be summoned.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }

        if (boss.getStage() == 2) {
            int currentNumberOfEnemyPlanes = getCurrentNumberOfEnemies() - 1; // exclude the boss
            for (int i = 0; i < TOTAL_ENEMY_PLANES - currentNumberOfEnemyPlanes; i++) {
                if (Math.random() < SUMMON_ENEMY_PROBABILITY) {
                    double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                    ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                    addEnemyUnit(newEnemy);
                }
            }
        }
    }

    /**
     * Initializes the scene for Level Four.
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
        levelView.updateBossHealth(boss.getStage() == 1 ? boss.getHealth() - BOSS_STAGE_TWO_HEALTH : boss.getHealth());
        levelView.updateBossHealthPosition(boss);
        if (boss.getStage() == 2) {
            levelView.showStageTwoHealth();
        }
    }

    /**
     * Instantiates the level view for Level Four.
     *
     * @return the instantiated level view
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_STAGE_ONE_HEALTH, TOTAL_ENEMY_PLANES, BOSS_STAGE_TWO_HEALTH);
        return levelView;
    }

    /**
     * Shows the level completed menu.
     *
     * @return the level completed menu
     */
    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return null;
    }
}