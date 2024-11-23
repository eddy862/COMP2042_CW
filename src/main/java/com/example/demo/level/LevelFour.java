package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.MultiStageBoss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LevelCompletedMenu;
import javafx.scene.Scene;

public class LevelFour extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 6;
    private LevelViewLevelFour levelView;
    private final MultiStageBoss boss;
    private static final int BOSS_STAGE_ONE_HEALTH = 15;
    private static final int BOSS_STAGE_TWO_HEALTH = 25;
    private static final double SUMMON_ENEMY_PROBABILITY = .15;
    private static final int TOTAL_ENEMY_PLANES = 3;
    private boolean isBossShielded;
    private final SoundEffect soundEffect;

    public LevelFour(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
        boss = new MultiStageBoss(BOSS_STAGE_ONE_HEALTH, BOSS_STAGE_TWO_HEALTH);
        this.isBossShielded = boss.isShielded();
        this.soundEffect = soundEffect;
    }

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

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.displayShield();
        levelView.showBossHealth();
        return scene;
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelFour(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_STAGE_ONE_HEALTH, TOTAL_ENEMY_PLANES, BOSS_STAGE_TWO_HEALTH);
        return levelView;
    }

    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return null;
    }
}
