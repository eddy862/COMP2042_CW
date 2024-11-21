package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LevelCompletedMenu;
import javafx.scene.Scene;

public class LevelThree extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelFour";
    private static final int TOTAL_ENEMIES = 3;
    private static final int USER_PROJECTILE_LIMIT = 8;
    private int userProjectileRemaining = USER_PROJECTILE_LIMIT;
    private static final int SURVIVAL_SEC = 20;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private static final int PLAYER_INITIAL_HEALTH = 6;
    private LevelViewLevelThree levelView;
    private int survivalTimeMillis = 0;
    public LevelThree(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
    }

    @Override
    protected void updateScene() {
        super.updateScene();
        // update survival time
        double delay = getTimeline().getCycleDuration().toMillis();
        survivalTimeMillis += delay;
        levelView.updateTimer(SURVIVAL_SEC - survivalTimeMillis / 1000);
        if (survivalTimeMillis == SURVIVAL_SEC * 1000) {
            levelCompleted();
        }

        // increase user projectile
        if (survivalTimeMillis % 2000 == 0) { // every 2 seconds
            increaseUserProjectileRemaining();
        }
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        this.levelView = new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, TOTAL_ENEMIES, USER_PROJECTILE_LIMIT, SURVIVAL_SEC);
        return levelView;
    }

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.showUserProjectiles();
        levelView.showTimer();
        return scene;
    }

    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        levelView.updateUserProjectiles(userProjectileRemaining);
    }

    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return new LevelCompletedMenu("Level Three", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelThree"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
    }

    private void decreaseUserProjectileRemaining() {
        if (userProjectileRemaining > 0) {
            userProjectileRemaining--;
        }
    }

    private void increaseUserProjectileRemaining() {
        if (userProjectileRemaining < USER_PROJECTILE_LIMIT) {
            userProjectileRemaining++;
        }
    }

    private boolean canUserFire() {
        return userProjectileRemaining > 0;
    }

    @Override
    protected void fireProjectile() {
        if (canUserFire()) {
            super.fireProjectile();
            decreaseUserProjectileRemaining();
        }
    }
}
